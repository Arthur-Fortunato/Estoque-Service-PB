package br.com.devart.estoqueservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstoqueRequestDTO {
    private Long produtoId;
    private Integer quantidade;
}
