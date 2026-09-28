package br.com.devart.estoqueservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String PRODUTO_CRIADO_QUEUE = "estoque.produto-criado";
    public static final String PRODUTO_EXCLUIDO_QUEUE = "estoque.produto-excluido";

    @Bean
    public Queue produtoCriadoQueue() {
        return new Queue(PRODUTO_CRIADO_QUEUE, true);
    }

    @Bean
    public Queue produtoExcluidoQueue() {
        return new Queue(PRODUTO_EXCLUIDO_QUEUE, true);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }
}