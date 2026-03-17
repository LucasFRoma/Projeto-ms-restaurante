package com.github.lucasfroma.ms.restaurante.service;

import com.github.lucasfroma.ms.restaurante.exceptions.DatabaseException;
import com.github.lucasfroma.ms.restaurante.exceptions.ResourceNotFoundException;
import com.github.lucasfroma.ms.restaurante.exceptions.dto.ReservaDTO;
import com.github.lucasfroma.ms.restaurante.exceptions.dto.RestauranteDTO;
import com.github.lucasfroma.ms.restaurante.exceptions.entities.Reserva;
import com.github.lucasfroma.ms.restaurante.exceptions.entities.Restaurante;
import com.github.lucasfroma.ms.restaurante.repositories.ReservaRepository;
import com.github.lucasfroma.ms.restaurante.repositories.RestauranteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private   RestauranteRepository restauranteRepository;


    //  Exibir todas Reservas
    @Transactional
    public List<ReservaDTO> findAllReservas() {

        List<Reserva> reservas = reservaRepository.findAll();

        return reservas.stream().map(ReservaDTO::new).toList();

    }

    // ------- FIM -------- \\

    // FindById
    @Transactional
    public ReservaDTO findById(long id) {

        Reserva reserva = reservaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reserva não encontrada"));

        return new ReservaDTO(reserva);
    }

    // ------- FIM -------- \\

    // Trabalha com o Post
    @Transactional
    public ReservaDTO saveReserva(ReservaDTO reservaDTO) {
        try {
            Reserva reserva = new Reserva();
            copyDtoToReserva(reservaDTO, reserva);
            reserva = reservaRepository.save(reserva);
            return new ReservaDTO(reserva);

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Não foi possível salvar esta reserva");
        }

    }
    // ------- FIM -------- \\

    // Update dados de um Restaurante
    @Transactional
    public ReservaDTO updateReserva(Long id, ReservaDTO reservaDTO) {

        try {
            Reserva reserva = reservaRepository.getReferenceById(id);
            copyDtoToReserva(reservaDTO, reserva);
            reserva = reservaRepository.save(reserva);
            return new ReservaDTO(reserva);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        }

    }

    // ------- FIM -------- \\


    // DeleteById
    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteReservaById(Long id) {

        if (!reservaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Não foi possível excluir esse ID pois ele é inexistente: " + id);

        }

        reservaRepository.deleteById(id);

    }

    // ------- FIM -------- \\

    private void copyDtoToReserva(ReservaDTO reservaDTO, Reserva reserva) {
        reserva.setDataReserva(reservaDTO.getDataReserva());
        reserva.setNomeCliente(reservaDTO.getNomeCliente());
        reserva.setQtdePessoas(reservaDTO.getQtdePessoas());

        // IMPORTANTE: Busca o restaurante e associa à entidade

        Restaurante restaurante = restauranteRepository.findById(reservaDTO.getRestauranteId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado"));
        reserva.setRestaurante(restaurante);
    }

}
