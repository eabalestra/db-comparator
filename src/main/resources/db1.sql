create table Cliente (
	nro_cliente int not null primary key,
	apellido VARCHAR(50) not null,
	nombre VARCHAR(50) not null,
	direccion VARCHAR(100) not null, 
	telefono VARCHAR(100),
	dni int
)

create table Producto (
	cod_producto int not null primary key,
	precio float check (precio > 0),
	stock_minimo int not null check (stock_minimo <= stock_maximo),
	stock_maximo int not null,
	cantidad float not null
)

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
