create database imobiliaria;
use imobiliaria;

create table cliente(
idCliente int not null auto_increment,
nome varchar(50) not null,
dataNasc varchar(50),
rg varchar(50),
cpf varchar(15) not null unique,
sexo varchar(10),
telefone varchar(15) not null,
celular varchar(15),
email varchar(50),
cep varchar(50) not null,
logradouro varchar(50) not null,
numero varchar(8) not null,
complemento varchar(20),
bairro varchar(20) not null,
cidade varchar(20) not null,
estado varchar(2),
idEmpresa int,
enabled boolean,
primary key(idCliente)
);

select * from cliente;

create table login(
idLogin int not null auto_increment,
idFuncionario int,
login varchar(20) not null unique,
senha varchar(30) not null,
tipoLogin varchar(20) not null,
idEmpresa int,
enabled boolean,
primary key(idLogin)
);

insert into login(idFuncionario, login, senha, tipoLogin, idEmpresa, enabled) values (null, 'admin', 'admin', 'admin', null, true);

select * from funcionario;

create table empresa(
idEmpresa int not null auto_increment,
nome varchar(50) not null,
cnpj varchar(25) not null,
cep varchar(50) not null,
logradouro varchar(50) not null,
numero varchar(20) not null,
complemento varchar(20),
bairro varchar(20) not null,
cidade varchar(20) not null,
estado varchar(3),
tipo varchar(50),
enabled boolean,
primary key(idEmpresa)
);

select * from empresa;

create table funcionario(
idFuncionario int not null auto_increment,
nome varchar(50) not null,
comissao double,
rg varchar(12),
cpf varchar(15) not null unique,
telefone varchar(15) not null,
email varchar(50),
cep varchar(50) not null,
logradouro varchar(50) not null,
numero varchar(8) not null,
complemento varchar(20),
bairro varchar(20) not null,
cidade varchar(20) not null,
estado varchar(2),
idEmpresa int not null,
enabled boolean,
primary key(idFuncionario),
foreign key(idEmpresa) references empresa(idEmpresa)
);

select * from funcionario;

create table imovel(
idImovel int not null auto_increment,
nome varchar(50),
CEP varchar(50) not null,
logradouro varchar(50) not null,
numero varchar(8) not null,
complemento varchar(20),
bairro varchar(20) not null,
cidade varchar(20) not null,
estado varchar(2),
descricao varchar(50) not null,
numDormitorios int (4) not null,
tamanho double not null,
vagas int(2) not null,
andar int,
mobiliado boolean,
pet boolean,
tipoImovel varchar(20) not null,
valor double  not null,
condominio double,
IPTU double,
seguro double,
parcela boolean,
valorEntrada double,
primary key(idImovel)
);

create table venda(
idVenda int not null auto_increment,
idImovel int not null,
idCliente int not null,
idFuncionario int not null,
parcelado boolean,
valorEntrada double,
numeroParcela int,
valorParcela double,
primary key(idVenda),
foreign key(idImovel) references imovel(idImovel),
foreign key(idCliente) references cliente(idCliente),
foreign key(idFuncionario) references funcionario(idFuncionario)
);	