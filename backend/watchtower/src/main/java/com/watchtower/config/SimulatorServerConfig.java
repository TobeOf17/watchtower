package com.watchtower.config;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimulatorServerConfig {

    @Value("${watchtower.simulator.port}")
    private int simulatorPort;

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> addSimulatorConnector() {
        return factory -> {
            Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
            connector.setPort(simulatorPort);
            factory.addAdditionalTomcatConnectors(connector);
        };
    }
}