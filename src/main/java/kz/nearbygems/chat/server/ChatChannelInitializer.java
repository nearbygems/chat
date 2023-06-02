package kz.nearbygems.chat.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final DelimiterBasedFrameDecoder frameDecoder;
    private final LoggingHandler             loggingHandler;
    private final StringDecoder              stringDecoder;
    private final StringEncoder              stringEncoder;
    private final ChatHandler                handler;

    @Override
    protected void initChannel(@NotNull SocketChannel ch) {
        ch.pipeline()
          .addLast("frameDecoder", frameDecoder)
          .addLast("loggingHandler", loggingHandler)
          .addLast("stringDecoder", stringDecoder)
          .addLast("stringEncoder", stringEncoder)
          .addLast(handler);
    }

}
