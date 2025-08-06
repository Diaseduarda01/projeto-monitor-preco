CREATE DATABASE IF NOT EXISTS monitor_preco;
USE monitor_preco;

-- Tabela de Roles
CREATE TABLE IF NOT EXISTS role (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE
);

-- Tabela de Usuários (com relação 1:1 com role)
CREATE TABLE IF NOT EXISTS usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    role_id INT,
    FOREIGN KEY (role_id) REFERENCES role(id)
);

-- Produtos que o usuário quer monitorar
CREATE TABLE IF NOT EXISTS produto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    nome VARCHAR(200) NOT NULL,
    url TEXT NOT NULL,
    classe VARCHAR(255) NOT NULL,
    preco_desejado DECIMAL(10, 2) NOT NULL,
    ativo BOOLEAN DEFAULT TRUE,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

-- Histórico de preços monitorados
CREATE TABLE IF NOT EXISTS historico_preco (
    id INT AUTO_INCREMENT PRIMARY KEY,
    produto_id INT NOT NULL,
    preco_coletado DECIMAL(10, 2) NOT NULL,
    data_coleta TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (produto_id) REFERENCES produto(id)
);

-- Notificações disparadas ao usuário
CREATE TABLE IF NOT EXISTS notificacao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    produto_id INT NOT NULL,
    preco_alvo DECIMAL(10, 2),
    preco_atingido DECIMAL(10, 2),
    data_notificacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    enviado BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    FOREIGN KEY (produto_id) REFERENCES produto(id)
);

-- Insere roles iniciais
INSERT INTO role (id, nome) VALUES (1, 'ADMIN'), (2, 'COMUM')
ON DUPLICATE KEY UPDATE nome=VALUES(nome);
