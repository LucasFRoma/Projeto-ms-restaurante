package com.github.lucasfroma.ms.restaurante.controller;

import com.github.lucasfroma.ms.restaurante.exceptions.dto.ReservaDTO;
import com.github.lucasfroma.ms.restaurante.exceptions.dto.RestauranteDTO;
import com.github.lucasfroma.ms.restaurante.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;


    // Exibindo todas Reservas
    @GetMapping
    public ResponseEntity<List<ReservaDTO>> getAllReservas(){

        List<ReservaDTO> list = reservaService.findAllReservas();

        return ResponseEntity.ok(list);
    }

    // ------- FIM -------- \\


    //Exibir Reserva por Id
    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTO> getReservaById(@PathVariable Long id) {

        ReservaDTO reservaDTO = reservaService.findById(id);

        return ResponseEntity.ok(reservaDTO);
    }

    // ------- FIM -------- \\

    // Criar nova reserva
    // Criar novo Restaurante
    @PostMapping
    public ResponseEntity<ReservaDTO> createReserva(@RequestBody @Valid ReservaDTO reservaDTO) {

        reservaDTO = reservaService.saveReserva(reservaDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(reservaDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(reservaDTO);
    }

    // ------- FIM -------- \\

    // Substituir dados de um Restaurante
    @PutMapping("/{id}")
    public ResponseEntity<ReservaDTO> updateReserva(@PathVariable Long id, @RequestBody @Valid ReservaDTO reservaDTO){

        reservaDTO = reservaService.updateReserva(id, reservaDTO);

        return ResponseEntity.ok(reservaDTO);
    }

    // ------- FIM -------- \\

    // Excluir Restaurante por Id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReserva(@PathVariable Long id) {

        reservaService.deleteReservaById(id);

        return ResponseEntity.noContent().build();

    }

    // ------- FIM -------- \\

}
