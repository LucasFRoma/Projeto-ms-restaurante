package com.github.lucasfroma.ms.restaurante.controller;

import com.github.lucasfroma.ms.restaurante.exceptions.dto.RestauranteDTO;
import com.github.lucasfroma.ms.restaurante.service.RestauranteService;
import jakarta.validation.Valid;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    // Exibir todos os Restaurantes
    @GetMapping
    public ResponseEntity<List<RestauranteDTO>> getAllRestaurantes() {

        List<RestauranteDTO> list = restauranteService.findAllRestaurantes();

        return ResponseEntity.ok(list);
    }

    // ------- FIM -------- \\


    // Get Mapping por ID

    @GetMapping("/{id}")
    public ResponseEntity<RestauranteDTO> getRestauranteById(@PathVariable Long id) {

        RestauranteDTO restauranteDTO = restauranteService.finRestauranteById(id);

        return ResponseEntity.ok(restauranteDTO);
    }


    // ------- FIM -------- \\

    // Criar novo Restaurante
    @PostMapping
    public ResponseEntity<RestauranteDTO> createRestaurante(@RequestBody @Valid RestauranteDTO restauranteDTO) {

        restauranteDTO = restauranteService.saveRestaurante(restauranteDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/id")
                .buildAndExpand(restauranteDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(restauranteDTO);
    }

    // ------- FIM -------- \\

    // Substituir dados de um Restaurante
    @PutMapping("/{id}")
    public ResponseEntity<RestauranteDTO> updateRestaurante(@PathVariable Long id, @RequestBody @Valid RestauranteDTO restauranteDTO){

        restauranteDTO = restauranteService.updateRestaurante(id, restauranteDTO);

        return ResponseEntity.ok(restauranteDTO);
    }

    // ------- FIM -------- \\



    // Excluir Restaurante por Id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurante(@PathVariable Long id) {

        restauranteService.deleteRestauranteById(id);

        return ResponseEntity.noContent().build();

    }

    // ------- FIM -------- \\


}
