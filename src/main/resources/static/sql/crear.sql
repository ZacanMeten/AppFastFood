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

CREATE TABLE USUARIOS(
	[id_user] int IDENTITY(1,1) PRIMARY KEY,
	[usuario] varchar(30) NOT NULL,
	[contrasenia] varchar(100) NOT NULL
)
GO
CREATE TABLE CLIENTES(
	[id_cli] [int] IDENTITY(1,1) PRIMARY KEY,
	[nombres] [varchar](30) NOT NULL,
	[apellidos] [varchar](35) NOT NULL,
	[edad] [int] NOT NULL,
	[correo] [varchar](45) NOT NULL,
	[direccion] [varchar](50) NOT NULL,
	[id_user] [int] Foreign key REFERENCES USUARIOS(id_user)
)
CREATE TABLE STAFF(
	[id_staff] [int] IDENTITY(1,1) PRIMARY KEY,
	[nombres] [varchar](30) NOT NULL,
	[apellidos] [varchar](35) NOT NULL,
	[correo] [varchar](35) NOT NULL,
	[rol] [varchar](30) NOT NULL,
	[id_user] [int] FOREIGN KEY REFERENCES USUARIOS(id_user)
)

CREATE TABLE PRODUCTOS(
	id_producto int IDENTITY(1,1) primary key,
	nombre varchar(30) not null,
	precio_unit decimal(7,2) not null,
	dsct_prc int NOT NULL CHECK (dsct_prc <= 100) DEFAULT(0)
)

CREATE TABLE PEDIDOS(
	[id_pedido] [int] IDENTITY(1,1) PRIMARY KEY,
	[id_cli] [int] FOREIGN KEY REFERENCES CLIENTES(id_cli),
	[fecha_emi] datetime DEFAULT GETDATE(),
	[estado] [varchar](10) NOT NULL,
	[precio_total] decimal(8,2) NOT NULL,
	[id_staff] [int] FOREIGN KEY REFERENCES STAFF(id_staff)
)

CREATE TABLE DETALLE_PED(
	[id_pedido] [int] FOREIGN KEY REFERENCES PEDIDOS(id_pedido) NOT NULL,
	[id_prod] [int] FOREIGN KEY REFERENCES PRODUCTOS(id_producto) NOT NULL,
	[cantidad] [int] NOT NULL,
	[precio] [decimal](8, 2) NOT NULL
)

/** inserts **/
/*
INSERT INTO USUARIOS VALUES ('anthony','anthony12'),
			('maricielo','maricielo12'),
			('carlos','carlos12')
GO
*/

INSERT INTO PRODUCTOS VALUES
           ('Tres Leches', 15.9, 0),
		   ('Torta Helada', 13.5, 0),
           ('Torta de Matrimonio', 240.25, 22),
		   ('Soda Dietetica', 10.34, 0),
		   ('Pastel de Pistacho', 24.5, 20),
		   ('Ratatouille', 22.5, 10)
GO


/** SELECTS **/
SELECT * FROM USUARIOS;
SELECT * FROM PRODUCTOS;