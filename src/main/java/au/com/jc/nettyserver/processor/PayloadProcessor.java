package au.com.jc.nettyserver.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PayloadProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String body = exchange.getIn().getBody(String.class);
        log.info("Finally receive payload from Netty Server: {}", body);
        exchange.getIn().setBody("Message Received!!!!");
    }
}
