package com.lineage.echo;

import java.io.IOException;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.core.service.IoHandler;
import com.lineage.server.mina.LineageProtocolHandler;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import com.lineage.server.mina.LineageCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import com.lineage.config.Config;
import com.lineage.server.LoginController;
import com.lineage.server.utils.PerformanceTimer;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class ServerExecutor extends Thread
{
    private static final Log _log;
    private static final String _t1 = "\n\r--------------------------------------------------";
    private static final String _t2 = "\n\r--------------------------------------------------";
    private int _port;
    
    static {
        _log = LogFactory.getLog((Class)ServerExecutor.class);
    }
    
    public ServerExecutor(final int port) {
        this._port = 0;
        try {
            this._port = port;
            this.startLoginServer();
        }
        catch (Exception ex) {
            return;
        }
        finally {
            ServerExecutor._log.info((Object)("[D] " + this.getClass().getSimpleName() + " 開始監聽服務端口:(" + this._port + ")"));
        }
    }
    
    private void startLoginServer() {
        try {
            PerformanceTimer timer = new PerformanceTimer();
            System.out.print("登錄控制器 加載中......");
            LoginController.getInstance().setMaxAllowedOnlinePlayers(Config.MAX_ONLINE_USERS);
            System.out.println("OK! " + timer.get() + " ms");
            final NioSocketAcceptor acceptor = new NioSocketAcceptor();
            final DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
            chain.addLast("codec", (IoFilter)new ProtocolCodecFilter((ProtocolCodecFactory)new LineageCodecFactory()));
            System.out.print("SocketAcceptor Binding...");
            acceptor.setReuseAddress(true);
            acceptor.setHandler((IoHandler)new LineageProtocolHandler());
            chain.addLast("線程池", (IoFilter)new ExecutorFilter());
            acceptor.bind((SocketAddress)new InetSocketAddress(this._port));
            System.out.println("OK! " + timer.get() + " ms");
            System.out.println("登錄服務器加載完成!");
            System.out.println("==================================================");
            System.out.println("監聽端口 [ " + Config.GAME_SERVER_PORT + " ] 開啟");
            System.out.println("==================================================");
            timer = null;
            final int num = Thread.activeCount();
            System.out.println("[存在線程量] : [ *" + num + "* ]");
//            System.out.println(":: Mina :: " + Config.SERVERNAME + " BY  Hero   QQ\uff1a313229085  ");
        }
        catch (Exception ex) {}
    }
    
    public void stsrtEcho() throws IOException {
        this.startLoginServer();
    }
    
    public void stopEcho() {
    }
}
