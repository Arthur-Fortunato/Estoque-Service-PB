package br.com.devart.estoqueservice.service;

import br.com.devart.estoqueservice.dto.request.EstoqueRequestDTO;
import br.com.devart.estoqueservice.dto.response.EstoqueResponseDTO;
import br.com.devart.estoqueservice.entity.Estoque;
import br.com.devart.estoqueservice.repository.IEstoqueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EstoqueServiceTest {
    @Mock
    private IEstoqueRepository estoqueRepository;
    private EstoqueService estoqueService;

    @BeforeEach
    void setUp() {
        estoqueService = new EstoqueService(estoqueRepository);
    }

    @Test
    void deveAdicionarEstoque() {
        EstoqueRequestDTO dto = new EstoqueRequestDTO(1L, 10);

        when(estoqueRepository.findByProdutoId(1L)).thenReturn(Optional.empty());

        when(estoqueRepository.save(any(Estoque.class))).thenAnswer(inv -> {
                    Estoque estoque = inv.getArgument(0);
                    estoque.setId(1L);
                    return estoque;
                });

        EstoqueResponseDTO resultado = estoqueService.adicionarEstoque(dto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getProdutoId());
        assertEquals(10, resultado.getQuantidade());
        assertTrue(resultado.isAtivo());
    }

    @Test
    void deveLancarExcecaoQuandoProdutoJaPossuirEstoque() {
        EstoqueRequestDTO dto = new EstoqueRequestDTO(1L, 10);
        Estoque estoqueExistente = new Estoque(1L, 1L, 5, LocalDateTime.now(), true);
        when(estoqueRepository.findByProdutoId(1L)).thenReturn(Optional.of(estoqueExistente));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> estoqueService.adicionarEstoque(dto));

        assertEquals("Produto já existe na tabela de estoque.", exception.getMessage());
        verify(estoqueRepository, never()).save(any());
    }

    @Test
    void deveRecuperarEstoquePorProdutoId() {
        Estoque estoque = new Estoque(1L, 5L, 20, LocalDateTime.now(), true);
        when(estoqueRepository.findByProdutoId(5L)).thenReturn(Optional.of(estoque));

        EstoqueResponseDTO resultado = estoqueService.recuperarEstoque(5L);

        assertEquals(5L, resultado.getProdutoId());
        assertEquals(20, resultado.getQuantidade());
    }

    @Test
    void deveAtualizarQuantidadeDoEstoque() {
        Estoque estoque = new Estoque(1L, 5L, 10, LocalDateTime.now(), true);
        EstoqueRequestDTO dto = new EstoqueRequestDTO(5L, 30);

        when(estoqueRepository.findByProdutoId(5L)).thenReturn(Optional.of(estoque));
        when(estoqueRepository.save(any(Estoque.class))).thenAnswer(inv -> inv.getArgument(0));

        EstoqueResponseDTO resultado = estoqueService.atualizarEstoque(dto);

        assertEquals(30, resultado.getQuantidade());
        assertNotNull(resultado.getDataAtualizacao());
    }

    @Test
    void deveDesativarEstoque() {
        Estoque estoque = new Estoque(1L, 5L, 10, LocalDateTime.now(), true);
        when(estoqueRepository.findByProdutoId(5L)).thenReturn(Optional.of(estoque));
        estoqueService.removerEstoque(5L);

        assertFalse(estoque.isAtivo());
        assertEquals(0, estoque.getQuantidade());
        assertNotNull(estoque.getDataAtualizacao());
        verify(estoqueRepository).save(estoque);
    }
}