package com.github.lucasfroma.ms.restaurante.service;

import com.github.lucasfroma.ms.restaurante.exceptions.DatabaseException;
import com.github.lucasfroma.ms.restaurante.exceptions.ResourceNotFoundException;
import com.github.lucasfroma.ms.restaurante.exceptions.dto.RestauranteDTO;
import com.github.lucasfroma.ms.restaurante.exceptions.entities.Restaurante;
import com.github.lucasfroma.ms.restaurante.repositories.RestauranteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    // Mostra todos os Restaurantes salvos no BD
    @Transactional
    public List<RestauranteDTO> findAllRestaurantes() {

        List<Restaurante> restaurantes = restauranteRepository.findAll();

        return restaurantes.stream().map(RestauranteDTO::new).toList();

    }

    // ------- FIM -------- \\

    // FinByID
    @Transactional(readOnly = true)
    public RestauranteDTO finRestauranteById(Long id) {

        Restaurante restaurante = restauranteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado: " + id));

        return new RestauranteDTO(restaurante);
    }

    // ------- FIM -------- \\

    // Trabalha com o Post
    @Transactional
    public RestauranteDTO saveRestaurante(RestauranteDTO restauranteDTO) {
        try {
            Restaurante restaurante = new Restaurante();
            copyDtoToRestaurante(restauranteDTO, restaurante);
            restaurante = restauranteRepository.save(restaurante);
            return new RestauranteDTO(restaurante);

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Não foi possível salvar este restaurante");
        }

    }
    // ------- FIM -------- \\

    // Update dados de um Restaurante

    @Transactional
    public RestauranteDTO updateRestaurante(Long id, RestauranteDTO restauranteDTO) {

        try {
            Restaurante restaurante = restauranteRepository.getReferenceById(id);
            copyDtoToRestaurante(restauranteDTO, restaurante);
            restaurante = restauranteRepository.save(restaurante);
            return new RestauranteDTO(restaurante);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        }

    }

    // ------- FIM -------- \\


    // DeleteById
    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteRestauranteById(Long id) {

        if (!restauranteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Não foi possível excluir esse ID pois ele é inexistente: " + id);

        }

        restauranteRepository.deleteById(id);

    }

    // ------- FIM -------- \\

    private void copyDtoToRestaurante(RestauranteDTO restauranteDTO, Restaurante restaurante) {
        restaurante.setNome(restauranteDTO.getNome());
        restaurante.setEndereco(restauranteDTO.getEndereco());
        restaurante.setCidade(restauranteDTO.getCidade());
        restaurante.setUf(restauranteDTO.getUf());
    }

}
