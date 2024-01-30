package com.solinfbroker.apiautenticacao.controller;

import com.solinfbroker.apiautenticacao.dtos.AuthResponseDTO;
import com.solinfbroker.apiautenticacao.dtos.AuthenticationDTO;
import com.solinfbroker.apiautenticacao.dtos.ClienteModelDTO;
import com.solinfbroker.apiautenticacao.dtos.RegisterDTO;
import com.solinfbroker.apiautenticacao.model.ClienteModel;
import com.solinfbroker.apiautenticacao.repository.ClienteRepository;
import com.solinfbroker.apiautenticacao.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    

    private final AuthenticationService authenticationService;


    private final ClienteRepository clienteRepository;


    @PostMapping("/login") // Método responsável por efetuar a autenticação e gerar o token de acesso ao cliente
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
        return ResponseEntity.ok(authenticationService.gerarAutenticacao(data));
    }

    @PostMapping("/register")
    public ResponseEntity<ClienteModelDTO> register(@RequestBody @Valid RegisterDTO data) {

        return ResponseEntity.ok().body(authenticationService.registrarCliente(data));
    }

    @GetMapping("/validar")
    public ResponseEntity<String> validarToken (@RequestParam("token") String token) {
        if(authenticationService.validarToken(token)){
            return new ResponseEntity<>("Token Não Autorizado",HttpStatus.UNAUTHORIZED); 
        }
        return new ResponseEntity<>("Token Autorizado",HttpStatus.ACCEPTED);
    }

    @GetMapping("/usuario/{login}")
    public ResponseEntity<ClienteModel> getUsuario(@PathVariable("login") String login) {
        return ResponseEntity.ok().body(clienteRepository.findByEmail(login));
    }
    
}
