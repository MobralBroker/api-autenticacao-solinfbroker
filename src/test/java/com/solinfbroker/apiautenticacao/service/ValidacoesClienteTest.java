package com.solinfbroker.apiautenticacao.service;

import com.solinfbroker.apiautenticacao.dtos.RegisterDTO;
import com.solinfbroker.apiautenticacao.exception.ApiRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@ActiveProfiles("test")
class ValidacoesClienteTest {

    private ValidacoesCliente validacoesCliente;

    @BeforeEach
    void setUp() {
        validacoesCliente = new ValidacoesCliente();
    }

    @Test
    void isValidCPF() {
        String cpfValido = "52998224725"; // um CPF válido
        String cpfInvalido = "123"; // um CPF inválido

        assertTrue(validacoesCliente.isValidCPF(cpfValido));
        assertFalse(validacoesCliente.isValidCPF(cpfInvalido));
    }

    @Test
    void validacaoIdade() {
        LocalDate dataNascimentoMaior = LocalDate.now().minusYears(20);
        LocalDate dataNascimentoMenor = LocalDate.now().minusYears(15);

        assertTrue(validacoesCliente.validacaoIdade(dataNascimentoMaior));
        assertFalse(validacoesCliente.validacaoIdade(dataNascimentoMenor));

    }
}