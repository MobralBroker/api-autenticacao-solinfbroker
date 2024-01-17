package com.solinfbroker.apiautenticacao.model;

import java.util.*;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe ClienteModel é uma entidade representante da tabela "cliente" no banco de dados.
 * Essa classe implementa UserDetails para fornecer autenticação e autorização para os usuários.
 *
 * Atributos:
 * - id: Identificador único do cliente.
 * - tipo: Tipo de cliente.
 * - nomeUsuario: Nome do usuário do cliente.
 * - senha: Senha da conta do cliente.
 * - email: Endereço de email do cliente.
 * - saldo: Saldo atual da conta do cliente.
 * - pessoaFisica: Conjunto de pessoas físicas associadas à conta do cliente.
 * - pessoaJuridica: Conjunto de pessoas jurídicas associadas à conta do cliente.
 * - permissoes: Conjunto de permissões que o cliente possui.
 *
 * O construtor ClientModel é usado para criar uma instância da classe com email, senha, conjunto de permissões,
 * tipo, nome do usuário e conjuntos de pessoas físicas e jurídicas.
 *
 * Método getAuthorities() retorna uma coleção de autoridades concedidas ao cliente.
 * Método getPassword() retorna a senha do cliente.
 * Método getUsername() retorna o email do cliente.
 * Método isAccountNonExpired() sempre retorna verdadeiro, representando que a conta é não expirada.
 * Método isAccountNonLocked() sempre retorna verdadeiro, representando que a conta não está bloqueada.
 * Método isCredentialsNonExpired() sempre retorna verdadeiro, representando que as credenciais são não expiradas.
 * Método isEnabled() sempre retorna verdadeiro, representando que a conta está ativada.
 */
@Entity
@Data
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cliente")
public class ClienteModel implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeUsuario;

    private String senha;

    private String email;

    private double saldo;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_cliente")
    private Set<PessoaFisica> pessoaFisica = new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cliente_permissao", joinColumns =  @JoinColumn(name="id_cliente"), inverseJoinColumns = @JoinColumn(name="id_permissao"))
    private Set<PermissaoModel> permissoes;

    public ClienteModel(String email, String senha, Set<PermissaoModel> role, String nomeUsuario, Set<PessoaFisica> pessoaFisica){
        this.email = email;
        this.senha = senha;
        this.permissoes = role;
        this.nomeUsuario = nomeUsuario;

        for(PessoaFisica pf : pessoaFisica){

            PessoaFisica pessoaFisica1 = new PessoaFisica();
            pessoaFisica1.setCpf(pf.getCpf());
            pessoaFisica1.setNome(pf.getNome());
            pessoaFisica1.setDataNascimento(pf.getDataNascimento());
            this.pessoaFisica.add(pessoaFisica1);
        }
    }

    public ClienteModel() {
        
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList autorities = new ArrayList<>();
        for (PermissaoModel perm: permissoes){
            autorities.add(new SimpleGrantedAuthority(perm.getPermissao()));
        }
        return autorities;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Set<PessoaFisica> getPessoaFisica() {
        return pessoaFisica;
    }

    public void setPessoaFisica(Set<PessoaFisica> pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }

    public Set<PermissaoModel> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(Set<PermissaoModel> permissoes) {
        this.permissoes = permissoes;
    }
}
