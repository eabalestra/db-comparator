create table Cliente (
	nro_cliente int not null primary key,
	apellido VARCHAR(50) not null,
	nombre VARCHAR(50) not null,
	direccion VARCHAR(100) not null, 
	telefono VARCHAR(100),
	dni int
)

CREATE INDEX idx_cliente_apellido ON Cliente (apellido);
CREATE INDEX idx_cliente_apellido_nombre ON Cliente (apellido, nombre);
CREATE INDEX idx_cliente_telefono ON Cliente (telefono);

create table Producto (
	cod_producto int not null primary key,
	precio float check (precio > 0),
	stock_minimo int not null check (stock_minimo <= stock_maximo),
	stock_maximo int not null,
	cantidad float not null
)

CREATE INDEX idx_producto_precio ON Producto (precio);
CREATE INDEX idx_producto_stock ON Producto (stock_minimo ASC, stock_maximo DESC);
CREATE INDEX idx_producto_cantidad_desc ON Producto (cantidad DESC);



create table ItemFactura (
	cod_producto int not null,
	nro_factura int not null,
	nro_cliente INT NOT NULL,
	cantidad int, 
	precio float,
	primary key (cod_producto),
	foreign key (cod_producto) references Producto(cod_producto) on delete restrict,
	foreign key (nro_factura) references Factura(nro_factura) on delete cascade,
	foreign key (nro_cliente) references Cliente(nro_cliente) on delete cascade,
	UNIQUE (cod_producto, nro_factura)
)


create table Factura (
	nro_factura serial not null primary key,
	nro_cliente int unique,
	fecha date,
	monto float,
	foreign key (nro_cliente) references Cliente(nro_cliente) on delete cascade 
)


CREATE TABLE Pago (
    nro_pago SERIAL NOT NULL PRIMARY KEY,
    nro_factura INT NOT NULL,
    fecha_pago DATE NOT NULL,
    monto_pago FLOAT CHECK (monto_pago > 0),
    metodo_pago VARCHAR(50) NOT NULL,
    FOREIGN KEY (nro_factura) REFERENCES Factura(nro_factura) ON DELETE CASCADE
);

CREATE OR REPLACE FUNCTION log_new_cliente() RETURNS TRIGGER AS $$
BEGIN
    RAISE NOTICE 'New cliente added: % % (nro_cliente: %)', NEW.nombre, NEW.apellido, NEW.nro_cliente;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER after_insert_cliente
BEFORE INSERT ON Cliente
FOR EACH ROW
EXECUTE FUNCTION log_new_cliente();