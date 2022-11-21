package br.com.mo.financeiroapi.config;

import br.com.mo.financeiroapi.properties.QueueProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    
     @Value("${spring.rabbitmq.host:vrrabbitmq}")
    private String SPRING_RABBITMQ_HOST;

    @Value("${spring.rabbitmq.port:5672}")
    private int SPRING_RABBITMQ_PORT;

    @Value("${spring.rabbitmq.username:vr}")
    private String SPRING_RABBITMQ_USERNAME;

    @Value("${spring.rabbitmq.password:vr!RbtMQ}")
    private String SPRING_RABBITMQ_PASSWORD;

    private final QueueProperties queueProperties;

    @Autowired
    public RabbitMQConfig(QueueProperties queueProperties) {
        this.queueProperties = queueProperties;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(SPRING_RABBITMQ_HOST, SPRING_RABBITMQ_PORT);
        connectionFactory.setUsername(SPRING_RABBITMQ_USERNAME);
        connectionFactory.setPassword(SPRING_RABBITMQ_PASSWORD);

        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public Queue queueErro() {
        return new Queue(queueProperties.getErro(), false);
    }

    @Bean
    public Queue queueInfo() {
        return new Queue(queueProperties.getInfo(), false);
    }

    
}
