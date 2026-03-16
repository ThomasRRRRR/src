import streamlit as st

# 頁面設定
st.set_page_config(page_title="阿棠交易計算器", page_icon="📈")

st.title("📊 阿棠交易型態計算器")
st.caption("支持 W/M 型態、頭肩型態與倉位風險控管")

# 側邊欄：基礎設置
with st.sidebar:
    st.header("⚙️ 帳戶設置")
    balance = st.number_input("帳戶餘額 (USD)", value=10000.0, step=100.0)
    risk_percent = st.slider("每筆風險 (%)", 0.5, 5.0, 1.0, 0.5)
    st.info(f"每筆交易預計虧損: ${balance * risk_percent / 100:.2f}")

# 主界面佈局
col1, col2 = st.columns(2)

with col1:
    pattern = st.selectbox("型態選擇", ["W / M 型態", "頭肩型態"])
    b1 = st.number_input("底部 / 頂部 ", value=100.0)
    b2 = st.number_input("頸線 ", value=110.0)
    b3 = st.number_input("進場價 ", value=110.0)

with col2:
    st.write("### 止損設置")
    auto_sl = (b1 + b2) / 2
    sl_input = st.number_input("自定義止損 (若為 0 則用建議值)", value=0.0)
    sl = sl_input if sl_input != 0 else auto_sl
    st.warning(f"當前止損位: {sl:.2f}")

# 計算邏輯
diff = abs(b2 - b1)
trade_type = st.radio("交易方向", ["多頭 (Bull)", "空頭 (Bear)"], horizontal=True)

if st.button("🚀 開始計算", use_container_width=True):
    is_bull = "多頭" in trade_type
    
    # 目標價計算
    if pattern == "頭肩型態":
        if is_bull:
            targets = [b3 - diff, b3 - (diff * 1.272), b3 - (diff * 1.618)]
        else:
            targets = [b3 + diff, b3 + (diff * 1.272), b3 + (diff * 1.618)]
    else: # W/M
        if is_bull:
            targets = [b3 + diff, b3 + (diff * 1.272), b3 + (diff * 1.618)]
        else:
            targets = [b3 - diff, b3 - (diff * 1.272), b3 - (diff * 1.618)]

    # 風報酬比與倉位
    reward = abs(targets[0] - b3)
    risk = abs(b3 - sl)
    rr_ratio = round(reward / risk, 2) if risk != 0 else 0
    
    risk_amount = balance * (risk_percent / 100)
    pos_size = risk_amount / risk if risk != 0 else 0

    # 顯示結果
    st.divider()
    res_col1, res_col2 = st.columns(2)
    
    with res_col1:
        st.success(f"🎯 目標 1: {targets[0]:.2f}")
        st.write(f"🎯 目標 2: {targets[1]:.2f}")
        st.write(f"🎯 目標 3: {targets[2]:.2f}")

    with res_col2:
        st.metric("風險報酬比", f"1 : {rr_ratio}")
        st.metric("建議倉位大小", f"{pos_size:.2f} 單位")
        
    if rr_ratio >= 1.5:
        st.balloons()
        st.info("✅ 風險回報比優異，適合進場！")
    else:
        st.error("⚠️ 風險報酬比較低，建議觀望。")