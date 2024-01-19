package com.solinfbroker.apiautenticacao.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.solinfbroker.apiautenticacao.dtos.RegisterDTO;
import com.solinfbroker.apiautenticacao.exception.ApiRequestException;
import com.solinfbroker.apiautenticacao.model.*;
import com.solinfbroker.apiautenticacao.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class AuthenticationServiceTest {

    @Mock
    TokenService tokenService;

    @Mock
    private ValidacoesCliente validacoesCliente;
    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    @DisplayName("Deve retornar erro caso o E-mail já esteja cadastrado")
    void deveRetornarErroSeOEmailJaExistir() {
        RegisterDTO cliente = mock(RegisterDTO.class);
        when(clienteRepository.findByEmail(cliente.email())).thenReturn(new ClienteModel());

        assertThrows(ApiRequestException.class, () -> {
            authenticationService.registrarCliente(cliente);
        });
    }

    @Test
    @DisplayName("Deve retornar erro caso o CPF seja Inválido")
    void deveRetornarErroCasoCpfInvalido() {
        RegisterDTO cliente = mock(RegisterDTO.class);
        PessoaFisica pessoaFisica = mock(PessoaFisica.class);
        Set<PessoaFisica> pessoaFisicaSet = new HashSet<>();
        pessoaFisicaSet.add(pessoaFisica);

        when(clienteRepository.findByEmail(cliente.email())).thenReturn(null);
        when(cliente.pessoaFisica()).thenReturn(pessoaFisicaSet);
        when(pessoaFisica.getCpf()).thenReturn("1234567891");
        when(validacoesCliente.isValidCPF(anyString())).thenReturn(false);

        assertThrows(ApiRequestException.class, () -> {
            authenticationService.registrarCliente(cliente);
        });
    }

    @Test
    @DisplayName("Deve retornar erro caso o cliente não possui idade Minima")
    void deveRetornarErroQuandoOClienteNaoPossuiIdadeMinima() {
        RegisterDTO cliente = mock(RegisterDTO.class);
        PessoaFisica pessoaFisica = mock(PessoaFisica.class);
        Set<PessoaFisica> pessoaFisicaSet = new HashSet<>();
        pessoaFisicaSet.add(pessoaFisica);

        when(clienteRepository.findByEmail(cliente.email())).thenReturn(null);
        when(cliente.pessoaFisica()).thenReturn(pessoaFisicaSet);
        when(pessoaFisica.getCpf()).thenReturn("74258841005");
        when(validacoesCliente.isValidCPF(anyString())).thenReturn(true);
        when(validacoesCliente.validacaoIdade(any())).thenReturn(false);

        assertThrows(ApiRequestException.class, () -> {
            authenticationService.registrarCliente(cliente);
        });
    }

    @Test
    @DisplayName("Registrar novo cliente com sucesso")
    void deveRegistrarOClienteComSucesso() {
        PermissaoModel permissaoModel = new PermissaoModel(2L,"ROLE_USER");
        Set<PermissaoModel> permissaoModelSet = new HashSet<>();
        permissaoModelSet.add(permissaoModel);

        PessoaFisica pessoaFisica = new PessoaFisica();
        pessoaFisica.setId(1L);
        pessoaFisica.setCpf("74258841005");
        pessoaFisica.setNome("Pedro");
        pessoaFisica.setDataNascimento(LocalDate.now().minusYears(18));
        Set<PessoaFisica> pessoaFisicaSet = new HashSet<>();
        pessoaFisicaSet.add(pessoaFisica);
        RegisterDTO cliente =  new RegisterDTO(
                "pedro@teste.com",
                "123",
                permissaoModelSet,
                "Pedro",
                pessoaFisicaSet
                );

        ClienteModel newUsuarioModel = new ClienteModel(
                cliente.email(),
                "encryptPassword",
                permissaoModelSet,
                cliente.nomeUsuario(),
                cliente.pessoaFisica());
        newUsuarioModel.setId(1L);
        when(clienteRepository.findByEmail(cliente.email())).thenReturn(null);
        when(validacoesCliente.isValidCPF(anyString())).thenReturn(true);
        when(validacoesCliente.validacaoIdade(any())).thenReturn(true);
        when(clienteRepository.save(any())).thenReturn(newUsuarioModel);
        assertNotNull(authenticationService.registrarCliente(cliente));
    }

}