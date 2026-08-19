package br.com.devart.estoqueservice.controller;

import br.com.devart.estoqueservice.dto.request.EstoqueRequestDTO;
import br.com.devart.estoqueservice.dto.response.EstoqueResponseDTO;
import br.com.devart.estoqueservice.service.EstoqueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/estoque")
public class EstoqueController {
    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @PostMapping
    public ResponseEntity<EstoqueResponseDTO> adicionarEstoqueDeProduto(@RequestBody EstoqueRequestDTO estoqueRequestDTO) {
        EstoqueResponseDTO estoque = estoqueService.adicionarEstoque(estoqueRequestDTO);
        return ResponseEntity.ok().body(estoque);
    }

    @GetMapping("/{produtoId}")
    public ResponseEntity<EstoqueResponseDTO> getEstoque(@PathVariable Long produtoId) {
        EstoqueResponseDTO estoque = estoqueService.recuperarEstoque(produtoId);
        return ResponseEntity.ok(estoque);
    }

    @PutMapping("/{produtoId}")
    public ResponseEntity<EstoqueResponseDTO> atualizarEstoque(@PathVariable Long produtoId, @RequestBody EstoqueRequestDTO dto) {
        dto.setProdutoId(produtoId);
        EstoqueResponseDTO estoque = estoqueService.atualizarEstoque(dto);
        return ResponseEntity.ok(estoque);
    }

    @DeleteMapping("/{produtoId}")
    public ResponseEntity<?> removerEstoque(@PathVariable Long produtoId) {
        estoqueService.removerEstoque(produtoId);
        return ResponseEntity.noContent().build();
    }
}
