/*
DROP TABLE IF EXISTS ITENS;
DROP TABLE IF EXISTS PRODUTOS;
DROP TABLE IF EXISTS PEDIDOS;
DROP TABLE IF EXISTS CLIENTES;
*/
/************ CLIENTES ************/
CREATE TABLE IF NOT EXISTS CLIENTES (
    CLIENTE_ID SERIAL PRIMARY KEY
    , NOME_COMPLETO VARCHAR(100)
    , CRIADO_EM TIMESTAMP
);

/************ PEDIDOS ************/
CREATE TABLE IF NOT EXISTS PEDIDOS (
    PEDIDO_ID SERIAL PRIMARY KEY
    , CLIENTE_ID INT
    , CRIADO_EM TIMESTAMP
    , FOREIGN KEY (CLIENTE_ID) REFERENCES CLIENTES (CLIENTE_ID)
);

/************ PRODUTOS ************/
CREATE TABLE IF NOT EXISTS PRODUTOS (
    PRODUTO_ID SERIAL PRIMARY KEY
    , DESCRICAO VARCHAR(100)
    , VALOR_PADRAO DECIMAL(10, 2)
    , CRIADO_EM TIMESTAMP
);

/************ ITENS ************/
CREATE TABLE IF NOT EXISTS ITENS (
    PEDIDO_ID INT
    , PRODUTO_ID INT
    , VALOR DECIMAL(10, 2)
    , QTDE INT
    , CRIADO_EM TIMESTAMP
    , PRIMARY KEY (PEDIDO_ID, PRODUTO_ID)
    , FOREIGN KEY (PEDIDO_ID) REFERENCES PEDIDOS (PEDIDO_ID)
    , FOREIGN KEY (PRODUTO_ID) REFERENCES PRODUTOS (PRODUTO_ID)
);