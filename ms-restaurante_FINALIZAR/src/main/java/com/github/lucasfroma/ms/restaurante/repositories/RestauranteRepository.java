package com.github.lucasfroma.ms.restaurante.repositories;

import com.github.lucasfroma.ms.restaurante.exceptions.entities.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
}
