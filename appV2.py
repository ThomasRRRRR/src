import streamlit as st
import plotly.graph_objects as go
from tradingview_ta import TA_Handler, Interval

# --- 頁面配置 ---
st.set_page_config(page_title="阿棠交易計算器", page_icon="📈")

st.title("📊 阿棠交易型態計算器")
st.caption("支持 W/M 型態、頭肩型態與倉位風險控管")

# --- 側邊欄：商品與帳戶設定 ---
with st.sidebar:
    st.header("🔍 行情連線")
    market_type = st.selectbox("市場類型", ["crypto", "america", "forex"])
    symbol = st.text_input("商品代碼 (例如: BTCUSDT, AAPL, EURUSD)", value="BTCUSDT").upper()
    exchange = st.text_input("交易所 (例如: BINANCE, NASDAQ, FX_IDC)", value="BINANCE").upper()
    
    st.divider()
    st.header("💰 帳戶風險控管")
    balance = st.number_input("帳戶餘額 (USD)", value=10000.0)
    risk_p = st.slider("每筆風險 (%)", 0.5, 5.0, 1.0, 0.5)
    
    if st.button("🔄 抓取即時價格"):
        try:
            handler = TA_Handler(symbol=symbol, screener=market_type, exchange=exchange, interval=Interval.INTERVAL_1_HOUR)
            analysis = handler.get_analysis()
            st.session_state.current_price = analysis.indicators["open"]
            st.success(f"成功抓取！當前價格: {st.session_state.current_price}")
        except Exception as e:
            st.error(f"抓取失敗，請檢查代碼或交易所名稱")

# --- 主界面：輸入區 ---
c1, c2, c3 = st.columns(3)
with c1:
    pattern = st.selectbox("選擇型態", ["W / M 型態", "頭肩型態"])
    trade_side = st.radio("交易方向", ["多頭 (Long)", "空頭 (Short)"], horizontal=True)

with c2:
    b1 = st.number_input("底部 / 頂部 ", value=60000.0)
    b2 = st.number_input("頸線 ", value=65000.0)
    
with c3:
    # 如果有抓到即時價格，預設帶入 B3
    default_b3 = st.session_state.get('current_price', 65500.0)
    b3 = st.number_input("進場價 ", value=float(default_b3))
    sl_input = st.number_input("自定止損 (0 為自動)", value=0.0)

# --- 計算邏輯 ---
diff = abs(b2 - b1)
is_long = "多頭" in trade_side
sl = sl_input if sl_input != 0 else (b1 + b2) / 2

# 目標計算
if pattern == "頭肩型態":
    coef = [-1, -1.272, -1.618] if is_long else [1, 1.272, 1.618]
else: # W/M
    coef = [1, 1.272, 1.618] if is_long else [-1, -1.272, -1.618]

t1, t2, t3 = [b3 + (diff * c) for c in coef]
rr_ratio = abs(t1 - b3) / abs(b3 - sl) if b3 != sl else 0
pos_size = (balance * risk_p / 100) / abs(b3 - sl) if b3 != sl else 0

# --- 結果展示與圖表 ---
st.divider()
res_col, chart_col = st.columns([1, 2])

with res_col:
    st.subheader("📋 計算結果")
    st.metric("建議倉位", f"{pos_size:.4f} 單位")
    st.write(f"🎯 目標 1: **{t1:.2f}** (RR 1:{rr_ratio:.2f})")
    st.write(f"🎯 目標 2: **{t2:.2f}**")
    st.write(f"🎯 目標 3: **{t3:.2f}**")
    st.error(f"🛑 止損價: **{sl:.2f}**")
    
    if rr_ratio >= 1.5:
        st.success("✅ 盈虧比達標，可以考慮！")
    else:
        st.warning("⚠️ 盈虧比過低，請謹慎。")

with chart_col:
    # 建立視覺化圖表
    fig = go.Figure()
    # 繪製進場、止損、目標線
    fig.add_hline(y=b3, line_dash="dash", line_color="blue", annotation_text="進場 (B3)")
    fig.add_hline(y=sl, line_dash="dot", line_color="red", annotation_text="止損 (SL)")
    fig.add_hline(y=t1, line_color="green", annotation_text="目標 1")
    fig.add_hline(y=t3, line_color="darkgreen", annotation_text="目標 3")
    
    # 設定圖表樣式
    fig.update_layout(title=f"{symbol} 交易計畫視覺化", height=400, template="plotly_dark")
    st.plotly_chart(fig, use_container_width=True)
