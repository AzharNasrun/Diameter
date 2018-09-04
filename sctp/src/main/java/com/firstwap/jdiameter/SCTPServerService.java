package com.firstwap.jdiameter;

import com.firstwap.jdiameter.Properties.JdiameterProperties;
import constant.Constants;
import com.firstwap.jdiameter.serverHandler.SCTPServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.SctpChannel;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;

import java.net.InetSocketAddress;

/**
 * Created by aji on 14/02/18.n
 */
@SpringBootApplication
@Profile("server")
public class SCTPServerService implements ApplicationRunner {

    private static final Logger logger = Logger.getLogger(Constants.SCTP_LOG);

    @Autowired
    SCTPServerHandler sctpServerHandler;
    @Override
    public void run(ApplicationArguments args) {
        logger.info("Waiting for client connection...");
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup, workerGroup);
            serverBootstrap.channel(NioSctpServerChannel.class);
            serverBootstrap.childHandler(new ChannelInitializer<SctpChannel>() {
                @Override
                public void initChannel(SctpChannel ch)
                    throws Exception {
                    ch.pipeline().addLast( sctpServerHandler);
                }
            });

            // Bind and start to accept incoming connections.
            serverBootstrap.bind(new InetSocketAddress(JdiameterProperties.getSctpServerIp(), Integer.parseInt(JdiameterProperties.getSctpServerPort()))).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
