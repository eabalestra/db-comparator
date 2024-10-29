create table Cliente (
	nro_cliente int not null primary key,
	apellido VARCHAR(50) not null,
	nombre VARCHAR(50) not null,
	direccion VARCHAR(100) not null, 
	telefono int
)

create table Producto (
	cod_producto int not null primary key,
	descripcion VARCHAR(100),
	precio float check (precio > 0),
	stock_minimo int not null check (stock_minimo <= stock_maximo),
	stock_maximo int not null,
	cantidad int not null
)

create table ItemFactura (
	cod_producto int not null,
	nro_factura int not null,
	cantidad int, 
	precio float,
	primary key (cod_producto, nro_factura),
	foreign key (cod_producto) references Producto(cod_producto) on delete restrict,
	foreign key (nro_factura) references Factura(nro_factura) on delete cascade
)

create table Factura (
	nro_factura serial not null primary key,
	nro_cliente int,
	fecha date,
	monto float,
	foreign key (nro_cliente) references Cliente(nro_cliente) on delete cascade 
)

CREATE OR REPLACE FUNCTION log_new_cliente() RETURNS TRIGGER AS $$
BEGIN
    RAISE NOTICE 'New cliente added: % % (nro_cliente: %)', NEW.nombre, NEW.apellido, NEW.nro_cliente;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER after_insert_cliente
AFTER INSERT ON Cliente
FOR EACH ROW
EXECUTE FUNCTION log_new_cliente();


CREATE OR REPLACE FUNCTION check_stock_level() RETURNS TRIGGER AS $$
BEGIN
    IF NEW.cantidad < NEW.stock_minimo THEN
        RAISE NOTICE 'Stock for producto % is below minimum level. Resetting to minimum stock.', NEW.cod_producto;
        NEW.cantidad := NEW.stock_minimo;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER after_update_cantidad
AFTER UPDATE OF cantidad ON Producto
FOR EACH ROW
EXECUTE FUNCTION check_stock_level();
