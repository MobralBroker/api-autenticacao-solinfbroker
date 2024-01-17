package com.solinfbroker.apiautenticacao.dtos;

import com.solinfbroker.apiautenticacao.model.PessoaFisica;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Set;

public record ClienteModelDTO(
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id,
        @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "O campo 'email' deve ser um endereço de e-mail válido")
        String email,
        @NotNull
        String nomeUsuario,
        @NotNull
        double saldo,
        Set<PessoaFisica> pessoaFisica
) {
        @Override
        public Long id() {
                return id;
        }

        @Override
        public String email() {
                return email;
        }

        @Override
        public String nomeUsuario() {
                return nomeUsuario;
        }

        @Override
        public double saldo() {
                return saldo;
        }

        @Override
        public Set<PessoaFisica> pessoaFisica() {
                return pessoaFisica;
        }
}
