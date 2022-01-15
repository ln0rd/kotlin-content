
CREATE TABLE promocao (
 id INT NOT NULL AUTO_INCREMENT,
 descricao varchar(255),
 local VARCHAR(255) NOT NULL,
 IsAllInclusive BOOLEAN,
 qtdDias INT,
 preco DOUBLE,
 PRIMARY KEY (id));
