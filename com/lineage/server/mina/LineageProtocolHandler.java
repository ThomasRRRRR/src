package com.lineage.server.mina;

import com.lineage.server.datatables.lock.IpReading;
import com.lineage.list.OnlineUser;
import com.lineage.server.utils.SystemUtil;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.echo.ClientExecutor;

import org.apache.mina.core.buffer.IoBuffer;

import java.math.BigInteger;

import com.lineage.config.Config;
import com.lineage.server.serverpackets.S_Key;

import org.apache.mina.core.session.IdleStatus;

import com.lineage.server.timecontroller.server.ServerRestartTimer;

import java.util.StringTokenizer;

import org.apache.mina.core.session.IoSession;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.apache.mina.core.service.IoHandlerAdapter;

public class LineageProtocolHandler extends IoHandlerAdapter {

	private static final Log _log = LogFactory.getLog(LineageProtocolHandler.class);
	
    public void sessionCreated(final IoSession session) {
        try {
            this.CheckGamePort(session);
            final StringTokenizer st = new StringTokenizer(session.getRemoteAddress().toString().substring(1), ":");
            final String ip = st.nextToken();
            if (ServerRestartTimer.isRtartTime()) {
                session.close(true);
                LineageProtocolHandler._log.info((Object)("因為重啟拒絕IP:" + ip));
            }
            LineageProtocolHandler._log.info((Object)("【接受連線】 IP: (" + ip + ")"));
            if (st.nextToken().startsWith("0")) {
                session.close(true);
            }
            session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 300);
        }
        catch (Exception e) {
            session.close(true);
        }
    }
    
    public void sessionOpened(final IoSession session) {
        if (!session.isClosing()) {
            try {
                int xorByte = -16;
                long authdata = 0L;
                final ServerBasePacket packet = new S_Key();
                final byte[] FirstPacket = packet.getContent();
                int seed = 0;
                seed |= (FirstPacket[1] << 0 & 0xFF);
                seed |= (FirstPacket[2] << 8 & 0xFF00);
                seed |= (FirstPacket[3] << 16 & 0xFF0000);
                seed |= (FirstPacket[4] << 24 & 0xFF000000);
                if (Config.LOGINS_TO_AUTOENTICATION) {
                    final int randomNumber = (int)(Math.random() * 9.0E8) + 255;
                    xorByte = randomNumber % 255 + 1;
                    authdata = new BigInteger(Integer.toString(xorByte)).modPow(new BigInteger(Config.RSA_KEY_E), new BigInteger(Config.RSA_KEY_N)).longValue();
                    final byte[] SFLPack = { (byte)(authdata & 0xFFL), (byte)(authdata >> 8 & 0xFFL), (byte)(authdata >> 16 & 0xFFL), (byte)(authdata >> 24 & 0xFFL) };
                    final IoBuffer buffer = IoBuffer.allocate(4, false);
                    buffer.put(SFLPack);
                    buffer.flip();
                    session.write((Object)buffer);
                }
                final StringTokenizer st = new StringTokenizer(session.getRemoteAddress().toString().substring(1), ":");
                final String ip = st.nextToken();
                final ClientExecutor lc = new ClientExecutor(session, seed);
                lc._xorByte = xorByte;
                lc._authdata = authdata;
                lc.setIp(new StringBuilder(ip));
                session.setAttribute((Object)"CLIENT", (Object)lc);
                lc.Initialization();
                session.write((Object)packet);
            }
            catch (Exception e) {
                session.close(true);
            }
        }
    }
    
    public void messageSent(final IoSession session, final Object message) {
        if (message instanceof ServerBasePacket) {
            ((ServerBasePacket)message).close();
        }
    }
    
    public void messageReceived(final IoSession session, final Object message) {
        if (message instanceof byte[]) {
        	final ClientExecutor client = (ClientExecutor) session.getAttribute("CLIENT");
            try {
                client.PacketHandler((byte[])message);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void sessionClosed(final IoSession session) {
        final ClientExecutor lc = (ClientExecutor)session.getAttribute((Object)"CLIENT");
        lc.getsSession().close(true);
        lc.clientclose();
        LineageProtocolHandler._log.info((Object)("【記憶體】 使用: " + SystemUtil.getUsedMemoryMB() + "MB"));
        LineageProtocolHandler._log.info((Object)("【當前在線】 玩家" + OnlineUser.get().size() + "位，等待玩家連線中..."));
    }
    
    public void sessionIdle(final IoSession session, final IdleStatus status) throws Exception {
        final ClientExecutor lc = (ClientExecutor)session.getAttribute((Object)"CLIENT");
        final StringTokenizer st = new StringTokenizer(session.getRemoteAddress().toString().substring(1), ":");
        final String ip = st.nextToken();
        lc.getsSession().close(true);
        session.close(true);
        LineageProtocolHandler._log.info((Object)("ip:" + ip + "閒置時間太長切斷"));
    }
    
    public void exceptionCaught(final IoSession session, final Throwable cause) {
        try {
            final ClientExecutor lc = (ClientExecutor)session.getAttribute((Object)"CLIENT");
            final StringTokenizer st = new StringTokenizer(session.getRemoteAddress().toString().substring(1), ":");
            final String ip = st.nextToken();
            lc.getsSession().close(true);
            lc.clientclose();
            session.close(true);
            LineageProtocolHandler._log.info((Object)("【異常掉線】 ip:" + ip));
            LineageProtocolHandler._log.error((Object)cause.toString());
        }
        catch (Exception ex) {}
    }
    
    private void CheckGamePort(final IoSession session) {
        try {
            final StringTokenizer st1 = new StringTokenizer(session.getRemoteAddress().toString().substring(1), ":");
            final String ip1 = st1.nextToken();
            final IpReading iptable1 = IpReading.get();
            final StringTokenizer st2 = new StringTokenizer(session.getRemoteAddress().toString().substring(1), ":");
            final String ip2 = st2.nextToken();
            final IpReading iptable2 = IpReading.get();
            final StringTokenizer st3 = new StringTokenizer(session.getRemoteAddress().toString().substring(1), ":");
            final String ip3 = st3.nextToken();
            final IpReading iptable3 = IpReading.get();
            final StringTokenizer st4 = new StringTokenizer(session.getRemoteAddress().toString().substring(1), ":");
            final String ip4 = st4.nextToken();
            final IpReading iptable4 = IpReading.get();
            if (st1.nextToken().startsWith("0")) {
                iptable1.remove(ip1);
                System.out.println("端口不可以為0開頭: " + ip1);
                session.close(true);
            }
            if (st2.nextToken().startsWith("null")) {
                iptable2.remove(ip2);
                System.out.println("端口不能為null: " + ip2);
                session.close(true);
            }
            if (st3.nextToken().isEmpty()) {
                iptable3.remove(ip3);
                System.out.println("端口不能為空: " + ip3);
                session.close(true);
            }
            if (st4.nextToken().length() <= 0) {
                iptable4.remove(ip4);
                System.out.println("端口不能太短: " + ip4);
                session.close(true);
            }
        }
        catch (Exception ex) {}
    }
    
    public String printData(final byte[] data, final int len) {
        final StringBuffer result = new StringBuffer();
        int counter = 0;
        for (int i = 0; i < len; ++i) {
            if (counter % 16 == 0) {
                result.append(this.fillHex(i, 4)).append(": ");
            }
            result.append(this.fillHex(data[i] & 0xFF, 2)).append(" ");
            if (++counter == 16) {
                result.append("   ");
                int charpoint = i - 15;
                for (int a = 0; a < 16; ++a) {
                    final int t1 = data[charpoint++];
                    if (t1 > 31 && t1 < 128) {
                        result.append((char)t1);
                    }
                    else {
                        result.append('.');
                    }
                }
                result.append("\n");
                counter = 0;
            }
        }
        final int rest = data.length % 16;
        if (rest > 0) {
            for (int j = 0; j < 17 - rest; ++j) {
                result.append("   ");
            }
            int charpoint = data.length - rest;
            for (int a = 0; a < rest; ++a) {
                final int t1 = data[charpoint++];
                if (t1 > 31 && t1 < 128) {
                    result.append((char)t1);
                }
                else {
                    result.append('.');
                }
            }
            result.append("\n");
        }
        return result.toString();
    }
    
    private String fillHex(int data, int digits) {
        String number = Integer.toHexString(data);
        return String.format("%1$" + digits + "s", number).replace(' ', '0');
    }
    
    class ip
    {
        public String ip;
        public int count;
        public long time;
        public boolean block;
    }
}
