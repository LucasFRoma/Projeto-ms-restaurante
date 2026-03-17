package com.github.lucasfroma.ms.restaurante.exceptions.dto;

import com.github.lucasfroma.ms.restaurante.exceptions.entities.Restaurante;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RestauranteDTO {

    private Long id;

    @NotBlank(message = "O campo nome é obrigatório")
    @Size(min = 5, max = 120, message = "O nome deve ter 5 até 120 caracteres")
    private String nome;

    @NotBlank(message = "O campo endereço é obrigatório")
    @Size(min = 5, max = 120, message = "O endereço deve ter 5 até 120 caracteres")
    private String endereco;

    @NotBlank(message = "O campo cidade é obrigatório")
    @Size(min = 3, max = 100, message = "O nome da cidade deve ter 3 até 100 caracteres")
    private String cidade;

    @NotBlank(message = "O campo UF é obrigatório")
    @Size(min = 2, max = 2, message = "O UF deve ter apenas 2 caracteres")
    private String uf;


    private List<ReservaDTO> reserva;

    public RestauranteDTO(Restaurante restaurante) {


        id = restaurante.getId();
        nome = restaurante.getNome();
        endereco = restaurante.getEndereco();
        cidade = restaurante.getCidade();
        uf = restaurante.getUf();

       reserva = restaurante.getReservas().stream().map(ReservaDTO::new).toList();
    }
}
