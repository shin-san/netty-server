package au.com.jc.nettyserver.config;

import org.apache.camel.component.netty4.ServerInitializerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NettyServerConfig {

    @Bean
    public ServerInitializerFactory serverInitializerFactory() {
        return new NettyServerInitializer(null);
    }
}
