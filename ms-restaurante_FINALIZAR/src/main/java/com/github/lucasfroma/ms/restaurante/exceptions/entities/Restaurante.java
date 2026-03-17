package com.github.lucasfroma.ms.restaurante.exceptions.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_restaurante")
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private String endereco;
    private String cidade;
    private String uf;

    @OneToMany(mappedBy = "restaurante")
    private List<Reserva> reservas = new ArrayList<>();

}
