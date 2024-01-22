## API Autenticação

A API de autenticação foi construida utilizando a linguagem de programação Java, utilizando ferramentas como o Spring Boot e o Spring Security. Essa API tem por responsabilidade autenticar o usuário que está entrando no **SolinfBroker**, utlizando técnicas de seguranças para verificar as informações repassadas pelo usuário.

---
## Start Aplicação

1º Criar o Cliente -> Post em /auth/register

2º Efetuar o Login -> Post em /auth/login

3º Salvar token na Variavel "token" dentro da colection SolinfBroker

---

## Stack

### Java (Versão 17)

O Java é uma linguagem de programação que é mantida atualmente pela Oracle Corporation. É uma linguagem de alto nível que tem por propósito geral a **orientação a objetos**. Projetada para ser independente de plataformas, o que permite que os programas em Java podem ser executados em diversas plataformas sem a necessidade de recompilação.

O **SolinfBroker** utiliza o Java 17 para construção da aplicação.

### Spring Boot (Versão 3.2.1)

Spring Boot é um projeto do ecossistema Spring Framework, que tem como objetivo simplificar e acelerar o desenvolvimento de aplicativos em Java.

O Spring Boot fornece um servidor web incorporado, eliminando a necessidade de configurar e implantar um servidor separado para executar seu aplicativo. O Spring Boot também é bastante utilizado para a construção de aplicações baseadas em microsserviços, pois fornece ferramentas que facilitam a incorporação de serviços independentes.

### Spring Security

O Spring Security é um projeto do ecossistema Spring que fornece recursos abrangentes para lidar com a segurança em aplicativos Java. Ele oferece uma estrutura poderosa e configurável para autenticação e autorização, ajudando os desenvolvedores e protegem suas aplicações contra ameaças de segurança.

O Spring Security facilita a implementação de autenticação em aplicativos, seja por meio de formulários de login, autenticação baseada em token, autenticação LDAP, etc. Oferece suporte a várias fontes de autenticação, como bancos de dados, serviços LDAP, provedores de autenticação externos (OAuth, OpenID Connect).

### JWT

O JWT (JSON Web Token) é um padrão aberto (RFC 7519) que define um formato compacto e autenticado para apresentação de informação em duas partes. Esse padrão é usado normalmente para o envio e o recebimento de informações de maneira segura entre cliente e servidor, e pode ser assinado digitalmente, o que garante a integridade dos dados.

O JWT é bastante utilizado para autenticação. Após uma autenticação bem-sucedida, um servidor pode gerar um JWT contendo reivindicações relevantes e enviá-lo para o cliente. O cliente então inclui o JWT em cabeçalhos de solicitações subsequentes para acessar recursos protegidos.

O JWT também é frequentemente utilizado em soluções SSO (Single Sign-On), permitindo que um usuário autenticado acesse vários serviços sem a necessidade de uma reautenticação.

### JpaRepository

O JpaRepository faz parte do Spring Data, que faz parte do ecossistema Spring destinado a simplificar o acesso a dados em aplicações Java, principalmente em aplicações baseadas em Spring. No contexto de autenticação em uma aplicação Spring, o JpaRepository é frequentemente utilizado em conjunto com o Spring Security para facilitar a manipulação de entidades de usuário e operações relacionadas ao banco de dados.

O JpaRepository também pode ser utilizado para armazenar senhas de forma segura, garantindo boas práticas de segurança, como o uso de hash e sal.

O JpaRepository possui métodos de crud para operações em banco de dados, como salvar(), delete(), findOne(), findAll() entre outros.

---

## Execução da API

Para executar a API Atenticação em sua máquina, é necessário ter o Docker instalado em seu PC para a execução do arquivo *setup.yml*. Caso o seu PC não possua o Docker instalado, sugerimos que siga o passo a passo de instalação da ferramenta na documentação oficial do Docker.

[Instalação do Docker](https://docs.docker.com/desktop/)

Após o sucesso da instalação do Docker em sua máquina, é necessário que, em seu terminal de preferência, se direcione para a pasta **Docker**, que está dentro da pasta raiz do projeto, onde se encontra o arquivo **setup.yml**, que contém as configurações necessárias para rodar um container com sucesso com todas as ferramentas essenciais para a execução da API.

Após encontrar o arquivo **setup.yml** no terminal, é necessário executar o seguinte comando:

> docker-compose -f setup.yml up -d

Esse comando é utilizado para iniciar o container Docker utilizando o Docker Compose, especificando que o arquivo setup.yml é o responsável de deter as configurações para a criação desse container, definindo os serviços, volumes, redes e outras configurações relacionadas.

Serão instalados e executados os seguintes serviços após a execução do arquivo **setup.yml**:

Serviço     |Descrição
------------|------------
PostgreSQL  | Container com o Banco de Dados Relacional PostgresSQL.
Zookeeper   | Container com o Zookeeper que é utilizado para gerenciar processos distribuidos.
Kafka       | Container que possui o Kafka, uma plataforma de streaming de eventos distribuída.
Kafdrop     | Container que possui o Kafdrop, uma interface gráfica (GUI) para interagir com clusters Kafka.
Kconnect    | Container que possui o Kafka Connect, que fornece uma maneira fácil e eficiente de integrar Kafka com outras fontes e destinos de dados.

Após o sucesso da execução do container, é necessário fazer a instalação de todas as dependências da nossa aplicação Spring de API de autenticação. As dependências podem ser visualizadas dentro do arquivo **pom.xml** que está dentro da pasta api-autenticacao.

Para baixar e instalar as dependências, é necessário, que com o terminal, dentro da pasta raíz do projeto, ou onde se encontra o arquivo **pom.xml**, e execute o seguinte comando:

> mvn spring-boot:run

As dependências serão baixadas e instaladas e prontas para o uso pela ferramenta **SolinfBroker**.

### Endpoints

A API de autenticação possui ao todo **4 endpoints**, que servem para autenticar e validar o usuário da plataforma **SolinfBroker**.

endpoint    | Método HTTP | Descrição
------------|-------------|------
/register   | POST        |Esse endipoint é o responsável por executar métodos necessários para registrar novos clientes.
/validar    | GET         | Esse endpoint é o responsável por executar métodos onde será possível os demais microsserviços validar o token dos cliente enquanto utilizamos o sistema.
/login     | POST         | Endpoint responsável por executar métodos de autenticação e gerar o token de acesso ao cliente.
/{usuario}/cliente/| GET  | *em construção* - Retorna o usuário logado
