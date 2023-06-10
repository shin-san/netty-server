package au.com.jc.nettyserver.route;

import au.com.jc.nettyserver.processor.PayloadProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NettyServerRoute extends RouteBuilder {

    PayloadProcessor payloadProcessor;

    public NettyServerRoute(PayloadProcessor payloadProcessor) {
        this.payloadProcessor = payloadProcessor;
    }

    @Override
    public void configure() throws Exception {
        from("netty4:tcp://127.0.0.1:8992?sync=true&serverInitializerFactory=#serverInitializerFactory&reuseChannel=true")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        log.info("[Processor] - Incoming Message -> {}", exchange.getIn().getBody(String.class));
                    }
                })
                .log("Received Payload!: ${body}")
                .process(payloadProcessor);

    }
}
