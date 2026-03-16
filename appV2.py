import streamlit as st
import plotly.graph_objects as go
from tradingview_ta import TA_Handler, Interval

# --- 1. 頁面基礎配置 ---
st.set_page_config(
    page_title="阿棠專業交易助手",
    page_icon="📈",
    layout="wide"
)

# 自定義 CSS 美化
st.markdown("""
    <style>
    .main {
        background-color: #0e1117;
    }
    stMetric {
        background-color: #1e2130;
        padding: 15px;
        border-radius: 10px;
    }
    </style>
    """, unsafe_allow_html=True)

st.title("📊 阿棠專業交易型態計算器 v3.0")
st.caption("支援 W/M 型態、頭肩型態、即時報價與視覺化計畫")

# --- 2. 側邊欄：行情與帳戶 ---
with st.sidebar:
    st.header("🔍 行情連線設置")
    market_type = st.selectbox("市場類型", ["crypto", "america", "forex", "TWSE"], help="加密貨幣、美股、外匯、台股")
    symbol = st.text_input("商品代碼", value="BTCUSDT").upper()
    exchange = st.text_input("交易所", value="BINANCE").upper()
    
    st.divider()
    st.header("💰 帳戶風險控管")
    balance = st.number_input("帳戶餘額 (USD)", value=10000.0, step=100.0)
    risk_p = st.slider("每筆交易風險 (%)", 0.1, 5.0, 1.0, 0.1)
    
    # 抓取價格按鈕
    if st.button("🔄 獲取即時價格", use_container_width=True):
        try:
            handler = TA_Handler(
                symbol=symbol,
                screener=market_type,
                exchange=exchange,
                interval=Interval.INTERVAL_1_HOUR
            )
            analysis = handler.get_analysis()
            st.session_state.current_price = analysis.indicators["open"]
            st.success(f"抓取成功！價格: {st.session_state.current_price}")
        except Exception as e:
            st.error("連線失敗，請檢查代碼或交易所是否正確。")

# --- 3. 主界面：輸入區域 ---
c1, c2, c3 = st.columns(3)

with c1:
    st.subheader("🛠 型態設定")
    pattern = st.selectbox("選擇型態", ["W / M 型態", "頭肩型態"])
    trade_side = st.radio("交易方向", ["多頭 (Long)", "空頭 (Short)"], horizontal=True)

with c2:
    st.subheader("📏 關鍵價位")
    b1 = st.number_input("底部 / 頂部 ", value=60000.0, format="%.2f")
    b2 = st.number_input("頸線 ", value=65000.0, format="%.2f")

with c3:
    st.subheader("🎯 進場與止損")
    # 自動帶入抓取的價格
    current_val = st.session_state.get('current_price', 65500.0)
    b3 = st.number_input("進場價 ", value=float(current_val), format="%.2f")
    
    auto_sl = (b1 + b2) / 2
    sl_input = st.number_input(f"自定止損 (建議: {auto_sl:.2f})", value=0.0, format="%.2f")
    sl = sl_input if sl_input != 0 else auto_sl

# --- 4. 核心計算邏輯 ---
diff = abs(b2 - b1)
is_long = "多頭" in trade_side

# 根據型態與方向計算目標 (R1, R2, R3)
if pattern == "頭肩型態":
    coef = [1.0, 1.272, 1.618]
    # 頭肩多頭目標往下算(反轉)，頭肩空頭目標往上算(反轉) -> 這裡依據一般技術分析邏輯調整
    targets = [b3 - (diff * c) if is_long else b3 + (diff * c) for c in coef]
else: # W/M 型態
    coef = [1.0, 1.272, 1.618]
    targets = [b3 + (diff * c) if is_long else b3 - (diff * c) for c in coef]

t1, t2, t3 = targets
risk_dist = abs(b3 - sl)
rr_ratio = abs(t1 - b3) / risk_dist if risk_dist != 0 else 0
risk_amt = balance * (risk_p / 100)
pos_size = risk_amt / risk_dist if risk_dist != 0 else 0

# --- 5. 結果顯示區域 ---
st.divider()
res_col, chart_col = st.columns([1, 2])

with res_col:
    st.subheader("📋 交易計畫摘要")
    
    st.metric("建議倉位大小", f"{pos_size:.4f} 單位")
    st.write(f"💵 每筆風險金額: `${risk_amt:.2f}`")
    st.write(f"⚖️ 風險報酬比: `1 : {rr_ratio:.2f}`")
    
    st.markdown("---")
    st.write(f"🎯 目標 1 (1.000): **{t1:.2f}**")
    st.write(f"🎯 目標 2 (1.272): **{t2:.2f}**")
    st.write(f"🎯 目標 3 (1.618): **{t3:.2f}**")
    st.markdown(f"🛑 止損價位: <span style='color:red; font-weight:bold;'>{sl:.2f}</span>", unsafe_allow_html=True)

    if rr_ratio >= 1.5:
        st.success("✅ 盈虧比良好，建議執行")
    else:
        st.warning("⚠️ 盈虧比較低，請斟酌風險")

with chart_col:
    # 繪製 Plotly 圖表
    fig = go.Figure()

    # 加入隱藏點以支撐座標軸顯示
    y_min = min(sl, t3, b3, b1) * 0.99
    y_max = max(sl, t3, b3, b1) * 1.01
    fig.add_trace(go.Scatter(x=[0, 10], y=[y_min, y_max], mode="markers", marker=dict(opacity=0), showlegend=False))

    # 繪製水平參考線
    fig.add_hline(y=b3, line_dash="dash", line_color="#3498db", annotation_text="進場 (B3)", annotation_position="top left")
    fig.add_hline(y=sl, line_dash="dot", line_color="#e74c3c", annotation_text="止損 (SL)", annotation_position="bottom left")
    fig.add_hline(y=t1, line_color="#2ecc71", annotation_text="目標 1", annotation_position="top right")
    fig.add_hline(y=t3, line_color="#1abc9c", annotation_text="目標 3 (最大獲利)", annotation_position="bottom right")

    # 填滿獲利區與止損區
    fig.add_hrect(y0=b3, y1=t3, fillcolor="green", opacity=0.1, line_width=0)
    fig.add_hrect(y0=b3, y1=sl, fillcolor="red", opacity=0.1, line_width=0)

    fig.update_layout(
        title=f"{symbol} 計畫視覺化預覽",
        height=500,
        template="plotly_dark",
        xaxis=dict(showgrid=False, zeroline=False, showticklabels=False),
        yaxis=dict(title="價格價格 (Price)", showgrid=True, gridcolor="#333"),
        margin=dict(l=20, r=20, t=50, b=20)
    )
    
    st.plotly_chart(fig, use_container_width=True)
    
    # --- 6. 新增：TradingView 實時 K 線圖外掛 ---
st.divider()
st.subheader(f"📺 {symbol} 實時盤面觀測")

# 建立 TradingView 外掛的 HTML 代碼
tv_widget_html = f"""
<div class="tradingview-widget-container" style="height:500px;">
  <div id="tradingview_chart"></div>
  <script type="text/javascript" src="https://s3.tradingview.com/tv.js"></script>
  <script type="text/javascript">
  new TradingView.widget({{
    "autosize": true,
    "symbol": "{exchange}:{symbol}",
    "interval": "60",
    "timezone": "Etc/UTC",
    "theme": "dark",
    "style": "1",
    "locale": "zh_TW",
    "toolbar_bg": "#f1f3f6",
    "enable_publishing": false,
    "allow_symbol_change": true,
    "container_id": "tradingview_chart"
  }});
  </script>
</div>
"""

# 使用 streamlit.components 顯示 HTML
import streamlit.components.v1 as components
components.html(tv_widget_html, height=520)
