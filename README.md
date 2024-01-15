# API de Autenticação
API responsável por criar e autenticar os usuários e garantir a segurança dos endpoints da sua aplicação.

### Pré-requisitos
1. Projeto atualizado
2. Banco de dados atualizado
3. Docker
4. Kubernetes

### Executando a Aplicação
1. Clone este repositório:

   ```bash
   git clone https://github.com/MobralBroker/api-autenticacao-solinfbroker
   ```

2. Instale mvn:

   ```bash
   mvn install
   ```

3. Execute:

   ```bash
   mvn springboot:run ou use a IDE 
   ```

### Endpoints
- login               (Responsável por efetuar a autenticação e gerar o token de acesso ao cliente)
- register            (Responsável por cadastrar um novo usuario)
- validar             (Responsável por buscar e verificar se usuario existe em nosso banco de dados)
- usuario/{login}     (Responsável por encontrar usuario)



