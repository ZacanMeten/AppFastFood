USE master
GO

DROP DATABASE BDFastandFood
GO

CREATE DATABASE BDFastandFood
GO
USE BDFastandFood
GO

/*
DROP TABLE DETALLE_PED;
DROP TABLE PEDIDOS;
DROP TABLE CLIENTES;
DROP TABLE STAFF;
DROP TABLE USUARIOS;
DROP TABLE PRODUCTOS;
*/

CREATE TABLE USUARIOS
(
	[id_user] int IDENTITY(1,1) PRIMARY KEY,
	[nombre] varchar(30) NOT NULL UNIQUE,
	[contrasenia] varchar(100) NOT NULL
)
GO
CREATE TABLE CLIENTES
(
	[id_cli] [int] IDENTITY(1,1) PRIMARY KEY,
	[nombres] [varchar](30) NOT NULL,
	[apellidos] [varchar](35) NOT NULL,
	[edad] [int] NOT NULL,
	[correo] [varchar](45) NOT NULL,
	[direccion] [varchar](50) NOT NULL,
	[id_user] [int] Foreign key REFERENCES USUARIOS(id_user)
)
CREATE TABLE STAFF
(
	[id_staff] [int] IDENTITY(1,1) PRIMARY KEY,
	[nombres] [varchar](30) NOT NULL,
	[apellidos] [varchar](35) NOT NULL,
	[correo] [varchar](35) NOT NULL,
	[rol] [varchar](30) NOT NULL,
	[id_user] [int] FOREIGN KEY REFERENCES USUARIOS(id_user)
)

CREATE TABLE PRODUCTOS
(
	id_producto int IDENTITY(1,1) primary key,
	nombre varchar(30) not null,
	precio_unit decimal(7,2) not null,
	dsct_prc int NOT NULL CHECK (dsct_prc <= 100) DEFAULT(0),
	image_data NTEXT
)

CREATE TABLE PEDIDOS
(
	[id_pedido] [int] IDENTITY(1,1) PRIMARY KEY,
	[id_cli] [int] FOREIGN KEY REFERENCES CLIENTES(id_cli),
	[fecha_emi] DATE DEFAULT CURRENT_TIMESTAMP,
	[estado] [varchar](10) DEFAULT 'PENDIENTE',
	[precio_total] decimal(8,2) NOT NULL,
	[id_staff] [int] FOREIGN KEY REFERENCES STAFF(id_staff)
)

CREATE TABLE DETALLE_PED
(
	[id] [int] IDENTITY(1,1) PRIMARY KEY,
	[id_pedido] [int] FOREIGN KEY REFERENCES PEDIDOS(id_pedido) NOT NULL,
	[id_prod] [int] FOREIGN KEY REFERENCES PRODUCTOS(id_producto) NOT NULL,
	[cantidad] [int] NOT NULL,
	[precio] [decimal](8, 2) NOT NULL
)

/** inserts 
Contraseñas anthony1 - maricielo - CarlosDiaz / Leriene
**/
INSERT INTO USUARIOS
VALUES
	('anthony', '$2a$10$rdmOMpYi905bbEUP0jvJ3.82MRUQBe6IuTk8zHxVzSmsQspSS0p5a'),
	('maricielo', '$2a$10$2L8dUlIJPJ6eRB411pnXDO0z3glMK.hLHLcIM6EsGAPnK9ypxa0ga'),
	('carlos', '$2a$10$PeHT3cNi0CD7X7UUpT7Z4.uUh.GdtcpuzNJdwE5Fn2ZQttgTIlFjW'),
	('Leriene', '$2a$10$XzziDjS2M4YtOq6mza2Ox.0sGuoHYNCPe8ZFb7ZdWB8FKPkvgyxSa')
GO

INSERT INTO CLIENTES
VALUES
	('Leriene Rosa', 'Marie Lein', 22, 'leriene@gmail.com', 'Calle Torres 250', 4)
GO

INSERT INTO PRODUCTOS
VALUES
	('Tres Leches', 15.9, 0, null),
	('Torta Helada', 13.5, 0, null),
	('Torta de Matrimonio', 240.2, 22, null),
	('Soda Dietetica', 10.3, 0, null),
	('Pastel de Pistacho', 24.5, 20, null),
	('Ratatouille', 22.5, 10, null)
GO

/** SELECTS **/
SELECT *
FROM USUARIOS;

Select *
from CLIENTES;
SELECT *
FROM PRODUCTOS;

SELECT *
FROM PEDIDOS;
SELECT *
FROM DETALLE_PED;