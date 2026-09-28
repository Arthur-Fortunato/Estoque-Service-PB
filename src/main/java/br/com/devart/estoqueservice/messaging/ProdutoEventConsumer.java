package br.com.devart.estoqueservice.messaging;

import br.com.devart.estoqueservice.dto.request.EstoqueRequestDTO;
import br.com.devart.estoqueservice.event.ProdutoCriadoEvent;
import br.com.devart.estoqueservice.event.ProdutoExcluidoEvent;
import br.com.devart.estoqueservice.service.EstoqueService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ProdutoEventConsumer {

    private final EstoqueService estoqueService;

    public ProdutoEventConsumer(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @RabbitListener(queues = "estoque.produto-criado")
    public void consumirProdutoCriado(ProdutoCriadoEvent evento) {
        EstoqueRequestDTO dto = new EstoqueRequestDTO(evento.produtoId(), 0);
        estoqueService.adicionarEstoque(dto);
    }

    @RabbitListener(queues = "estoque.produto-excluido")
    public void consumirProdutoExcluido(ProdutoExcluidoEvent evento) {
        estoqueService.removerEstoque(evento.produtoId());
    }
}