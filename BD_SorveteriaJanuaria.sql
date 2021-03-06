CREATE TABLE USUARIO
(
        ID INTEGER NOT NULL PRIMARY KEY IDENTITY,
	NOME VARCHAR(60) NOT NULL,
	LOGIN VARCHAR(30) NOT NULL,
	SENHA VARCHAR(32) NOT NULL
);

CREATE TABLE FORNECEDOR
(
        ID INTEGER NOT NULL PRIMARY KEY IDENTITY,
	NOME VARCHAR(60) NOT NULL,
	CNPJ VARCHAR(18) NOT NULL,
	TELEFONE VARCHAR(13) NOT NULL,
	ENDERECO  VARCHAR(80) NOT NULL,
	OBSERVACOES VARCHAR(120) NOT NULL
);

CREATE TABLE PRODUTO
(
        ID INTEGER NOT NULL PRIMARY KEY IDENTITY,
	NOME VARCHAR(60) NOT NULL,
	PRECO DOUBLE NOT NULL,
	SABOR VARCHAR(60) NOT NULL,
);

CREATE TABLE REVENDEDOR
(
        ID INTEGER NOT NULL PRIMARY KEY IDENTITY,
	NOME VARCHAR(60) NOT NULL,
	CPF VARCHAR(15),
	CNPJ VARCHAR(18),
        RG VARCHAR(12) NOT NULL,
	TELEFONE VARCHAR(13) NOT NULL, 
	ENDERECO VARCHAR (60) NOT NULL,
	DATA_NASCIMENTO DATE  NOT NULL
);

CREATE TABLE ITENS_VENDA(
	ID INT IDENTITY  NOT NULL,
    	QUANTIDADE INT NOT NULL,
	ID_PRODUTO INT,
	ID_VENDA INTEGER NOT NULL,
	FOREIGN KEY(ID_PRODUTO) REFERENCES PRODUTO(ID)
);

CREATE TABLE VENDA(
	ID INTEGER IDENTITY NOT NULL,
        DATA_VENDA DATE NOT NULL,
        FORMA_PAGAMENTO VARCHAR(20) NOT NULL,
	ID_REVENDEDOR INTEGER DEFAULT 0,
   	STATUS BOOLEAN DEFAULT TRUE,
    	FOREIGN KEY(ID_REVENDEDOR) REFERENCES REVENDEDOR(ID)
);