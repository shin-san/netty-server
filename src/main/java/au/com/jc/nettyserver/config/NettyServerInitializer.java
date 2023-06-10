package au.com.jc.nettyserver.config;


import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;
import org.apache.camel.component.netty4.NettyConsumer;
import org.apache.camel.component.netty4.ServerInitializerFactory;
import org.apache.camel.component.netty4.handlers.ServerChannelHandler;

import java.nio.ByteOrder;

public class NettyServerInitializer extends ServerInitializerFactory {

    NettyConsumer consumer;

    private int maxLineSize = 65536;

    public NettyServerInitializer(NettyConsumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public ServerInitializerFactory createPipelineFactory(NettyConsumer consumer) {
        return new NettyServerInitializer(consumer);
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline channelPipeline = channel.pipeline();
//        channelPipeline.addLast("framer", new LengthFieldBasedFrameDecoder(ByteOrder.LITTLE_ENDIAN, maxLineSize, 2, 2, 6, 0, false));
        channelPipeline.addLast("logger", new LoggingHandler(LogLevel.INFO));
        channelPipeline.addLast("encoder-SD", new StringEncoder(CharsetUtil.UTF_8));
//        channelPipeline.addLast("decoder-DELIM", new DelimiterBasedFrameDecoder(maxLineSize, true, Delimiters.lineDelimiter()));
        channelPipeline.addLast("decoder-SD", new StringDecoder(CharsetUtil.UTF_8));
        // here we add the default Camel ServerChannelHandler for the consumer, to allow Camel to route the message etc.
        channelPipeline.addLast("handler", new ServerChannelHandler(consumer));
    }
}
