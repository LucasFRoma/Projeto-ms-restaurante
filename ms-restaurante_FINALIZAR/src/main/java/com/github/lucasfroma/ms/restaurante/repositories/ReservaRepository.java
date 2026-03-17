package com.github.lucasfroma.ms.restaurante.repositories;

import com.github.lucasfroma.ms.restaurante.exceptions.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}
