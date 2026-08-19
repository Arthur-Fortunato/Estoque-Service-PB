package br.com.devart.estoqueservice.service;

import br.com.devart.estoqueservice.dto.request.EstoqueRequestDTO;
import br.com.devart.estoqueservice.dto.response.EstoqueResponseDTO;
import br.com.devart.estoqueservice.entity.Estoque;
import br.com.devart.estoqueservice.repository.IEstoqueRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EstoqueService {
    private final IEstoqueRepository estoqueRepository;

    public EstoqueService(IEstoqueRepository estoqueRepository) {
        this.estoqueRepository = estoqueRepository;
    }

    public EstoqueResponseDTO adicionarEstoque(EstoqueRequestDTO dto) {
        Optional<Estoque> estoqueExistente = estoqueRepository.findByProdutoId(dto.getProdutoId());

        if  (estoqueExistente.isPresent()) {
            throw new IllegalArgumentException("Produto já existe na tabela de estoque.");
        }

        Estoque estoque = new Estoque(null,
                dto.getProdutoId(),
                dto.getQuantidade(),
                LocalDateTime.now(),
                true);

        Estoque produtoSalvo = estoqueRepository.save(estoque);

        return new EstoqueResponseDTO(produtoSalvo.getId(),
                produtoSalvo.getProdutoId(),
                produtoSalvo.getQuantidade(),
                produtoSalvo.getDataAtualizacao(),
                produtoSalvo.isAtivo());
    }

    public EstoqueResponseDTO recuperarEstoque(Long produtoId) {
        Estoque estoque = estoqueRepository.findByProdutoId(produtoId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado no estoque."));

        return new EstoqueResponseDTO(estoque.getId(),
                estoque.getProdutoId(),
                estoque.getQuantidade(),
                estoque.getDataAtualizacao(),
                estoque.isAtivo());
    }

    public EstoqueResponseDTO atualizarEstoque(EstoqueRequestDTO dto) {
        Estoque estoque = estoqueRepository.findByProdutoId(dto.getProdutoId())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado no estoque."));

        estoque.setQuantidade(dto.getQuantidade());
        estoque.setDataAtualizacao(LocalDateTime.now());

        Estoque produtoSalvo = estoqueRepository.save(estoque);

        return new EstoqueResponseDTO(produtoSalvo.getId(),
                produtoSalvo.getProdutoId(),
                produtoSalvo.getQuantidade(),
                produtoSalvo.getDataAtualizacao(),
                produtoSalvo.isAtivo());
    }

    public void removerEstoque(Long produtoId) {
        Estoque estoque = estoqueRepository.findByProdutoId(produtoId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado no estoque."));

        estoque.setQuantidade(0);
        estoque.setDataAtualizacao(LocalDateTime.now());
        estoque.setAtivo(false);

        estoqueRepository.save(estoque);
    }
}
