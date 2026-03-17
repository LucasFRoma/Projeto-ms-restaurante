package com.github.lucasfroma.ms.restaurante.exceptions.dto;

import com.github.lucasfroma.ms.restaurante.exceptions.entities.Reserva;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter

public class ReservaDTO {

    private Long id;

   @FutureOrPresent(message = "A data da reserva deve ser hoje ou futura")
    private LocalDate dataReserva;

    @NotBlank(message = "O campo nomeCliente é obrigatório")
    @Size(min = 3, max = 100, message = "O nome do cliente deve ter no mínimo 3 até 100 caracteres")
    private String nomeCliente;

    @NotNull(message = "O campo quantidade de pessoas é obrigatório")
    @Positive( message = "A quantidade de pessoas deve ser maior que 0")
    private int qtdePessoas;

    @NotNull(message = "O ID do restaurante é obrigatório")
    private Long restauranteId;

    public ReservaDTO(Reserva reserva) {
        id = reserva.getId();
        dataReserva = reserva.getDataReserva();
        nomeCliente = reserva.getNomeCliente();
        qtdePessoas = reserva.getQtdePessoas();
        restauranteId = reserva.getRestaurante().getId();
    }
}
