package com.iqmsoft.jms.config;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelConfig {

    @Bean
    ConnectionFactory jmsConnectionFactory() {
        // use a pool for ActiveMQ connections
        PooledConnectionFactory pool = new PooledConnectionFactory();
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        factory.setTrustAllPackages(true);
        pool.setConnectionFactory(factory);
        return pool;
    }

    @Bean
    RouteBuilder myRouter() {
        return new RouteBuilder() {

            @Override
            public void configure() throws Exception {
                from("activemq:sample.queue").to("bean:queueHandler");
            }
        };
    }
}