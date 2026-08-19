package br.com.devart.estoqueservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstoqueResponseDTO {
    private Long id;
    private Long produtoId;
    private Integer quantidade;
    private LocalDateTime dataAtualizacao;
    private boolean ativo;
}
