package com.guiame.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  @Value("${rabbitmq.host}")
  private String host;

  @Value("${rabbitmq.port}")
  private int port;

  @Value("${rabbitmq.username}")
  private String username;

  @Value("${rabbitmq.password}")
  private String password;

  @Value("${rabbitmq.virtual-host}")
  private String virtualHost;

  @Value("${rabbitmq.exchange}")
  private String exchangeName;

  @Value("${rabbitmq.queue}")
  private String queueName;

  @Bean
  public ConnectionFactory connectionFactory() {
    return new CachingConnectionFactory(host, port);
  }

  @Bean
  public Exchange exchange() {
    return ExchangeBuilder.directExchange(exchangeName).build();
  }

  @Bean
  public Queue queue() {
    return QueueBuilder.durable(queueName).build();
  }

  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    return new RabbitTemplate(connectionFactory);
  }
}
