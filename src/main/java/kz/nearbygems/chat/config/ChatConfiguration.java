package kz.nearbygems.chat.config;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.ResourceLeakDetector;
import kz.nearbygems.chat.server.ChatChannelInitializer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ChatConfiguration {

    private final ChatProperties properties;

    @Bean
    public ServerBootstrap serverBootstrap(NioEventLoopGroup parentGroup,
                                           NioEventLoopGroup childGroup,
                                           ChatChannelInitializer initializer) {
        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.PARANOID);
        final var serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(parentGroup, childGroup)
                       .channel(NioServerSocketChannel.class)
                       .option(ChannelOption.SO_BACKLOG, properties.getBacklog())
                       .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, properties.getTimeout())
                       .childOption(ChannelOption.TCP_NODELAY, properties.getNodelay())
                       .childOption(ChannelOption.SO_KEEPALIVE, properties.getKeepAlive())
                       .childHandler(initializer);
        return serverBootstrap;
    }

    @Bean(destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup parentGroup() {
        return new NioEventLoopGroup(properties.getParents());
    }

    @Bean(destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup childGroup() {
        return new NioEventLoopGroup(properties.getChildren());
    }

    @Bean
    public DelimiterBasedFrameDecoder frameDecoder() {
        return new DelimiterBasedFrameDecoder(properties.getMaxFrameLength(),
                                              Delimiters.lineDelimiter());
    }

    @Bean
    public LoggingHandler loggingHandler() {
        return new LoggingHandler(LogLevel.INFO);
    }

    @Bean
    public StringDecoder stringDecoder() {
        return new StringDecoder();
    }

    @Bean
    public StringEncoder stringEncoder() {
        return new StringEncoder();
    }

}
