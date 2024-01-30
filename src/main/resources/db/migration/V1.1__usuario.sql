-- Tabela para armazenar informações sobre clientes
CREATE TABLE cliente (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    nome_usuario VARCHAR(50) NOT NULL,
    senha VARCHAR NOT NULL,
    email VARCHAR(50) NOT NULL,
    saldo DOUBLE PRECISION DEFAULT 0.0,
    valor_bloqueado DOUBLE PRECISION DEFAULT 0 NOT NULL
);

-- Tabela com as permissões
create table permissoes(
    id BIGSERIAL primary key,
    permissao varchar not null
);

-- Tabela de relacionamento entre os clientes e permissões
create table cliente_permissao(
    id_cliente BIGSERIAL not null,
    id_permissao BIGSERIAL not null,

    foreign key (id_cliente) references cliente(id),
    foreign key (id_permissao) references permissoes(id)
);

-- Tabela para armazenar informações sobre pessoas físicas, com chave estrangeira referenciando a tabela cliente
CREATE TABLE pessoafisica (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    id_cliente BIGSERIAL NOT NULL,
    nome VARCHAR(150) NOT NULL,
    nascimento DATE NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES cliente(id)
);

-- Insert das Permissoes iniciais do sistema
insert into permissoes(id,permissao) values (1,'ROLE_ADMIN');
insert into permissoes(id,permissao) values (2,'ROLE_USER');

-- Tabela para armazenar informações sobre empresas
CREATE TABLE empresa (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    razao_social VARCHAR(150) NOT NULL,
    nome_fantasia VARCHAR(150) NOT NULL,
    cnpj VARCHAR(14) NOT NULL
);

-- Tabela para armazenar informações sobre ativos, com chave estrangeira referenciando a tabela empresa
CREATE TABLE ativo (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    id_empresa BIGINT NOT NULL,
    sigla VARCHAR(10) NOT NULL,
    nome VARCHAR(50) NOT NULL,
    atualizacao TIMESTAMP NOT NULL,
    quantidades_papeis INT NOT NULL,
    valor_max DOUBLE PRECISION NOT NULL,
    valor_min DOUBLE PRECISION NOT NULL,
    valor DOUBLE PRECISION NOT NULL,
    FOREIGN KEY (id_empresa) REFERENCES empresa(id)
);

-- Tabela para armazenar informações sobre ordens de compra, com chave estrangeira referenciando a tabela cliente
CREATE TABLE ordem (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    id_cliente BIGINT NOT NULL,
    id_ativo BIGINT NOT NULL,
    tipo_ordem VARCHAR(12) NOT NULL,
    valor_ordem DOUBLE PRECISION NOT NULL,
	data_lancamento TIMESTAMP NOT NULL,
    quantidade_ordem INT NOT NULL DEFAULT 1,
    quantidade_ordem_aberta INT NOT NULL DEFAULT 0,
    status_ordem VARCHAR(18) NOT NULL,
    versao BIGINT,
    valor_cliente_bloqueado DOUBLE PRECISION NOT NULL DEFAULT 0,
    FOREIGN KEY (id_cliente) REFERENCES cliente(id),
    FOREIGN KEY (id_ativo) REFERENCES ativo(id)
);
-- Tabela para armazenar informações sobre ordens de compra, com chave estrangeira referenciando a tabela cliente
CREATE TABLE operacao (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    id_compra BIGINT NOT NULL,
    id_venda BIGINT NOT NULL,
    valor_ativo_execucao DOUBLE PRECISION NOT NULL DEFAULT 0,
	quantidade INT NOT NULL,
    data_execucao TIMESTAMP NOT NULL,
	status_operacao VARCHAR(10) NOT NULL,
    FOREIGN KEY (id_compra) REFERENCES ordem(id),
    FOREIGN KEY (id_venda) REFERENCES ordem(id)
);
-- Tabela para armazenar histórico de preços de ativos, com chave estrangeira referenciando a tabela ativo
CREATE TABLE IF NOT EXISTS historico_preco (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    id_ativo BIGINT NOT NULL,
    data_valor TIMESTAMP NOT NULL,
    valor_do_ativo DOUBLE PRECISION NOT NULL,
    FOREIGN KEY (id_ativo) REFERENCES ativo(id)
);

-- Tabela para armazenar informações sobre a carteira de um cliente, com chaves estrangeiras referenciando as tabelas cliente e ativo
CREATE TABLE carteira (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    id_cliente BIGINT NOT NULL,
    id_ativo BIGINT NOT NULL,
    quantidade INT NOT NULL,
	data_compra timestamp NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES cliente(id),
    FOREIGN KEY (id_ativo) REFERENCES ativo(id)
);

-- Trigger de Log para gerar Histórico de Preços
-- Função que é executada após uma atualização na tabela ativo, registrando o histórico de preços
CREATE OR REPLACE FUNCTION after_update_ativo()
RETURNS TRIGGER AS $$
BEGIN
    -- Verifica se o valor da coluna "valor" foi alterado
    IF NEW.valor <> OLD.valor THEN
        -- Insere um novo registro na tabela historico_preco com data e hora atual
        INSERT INTO historico_preco (id_ativo, data_valor, valor_do_ativo)
        VALUES (NEW.id, CURRENT_TIMESTAMP, NEW.valor);
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger associada à função, executada após uma atualização na tabela ativo
CREATE TRIGGER after_update_ativo
AFTER UPDATE ON ativo
FOR EACH ROW
EXECUTE FUNCTION after_update_ativo();
