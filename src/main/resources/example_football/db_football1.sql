-- Crear el esquema Liga1
CREATE SCHEMA IF NOT EXISTS "football1";

-- Establecer el esquema de búsqueda
SET search_path = 'football1';

-- Tabla Equipo
CREATE TABLE Equipo (
    id_equipo SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    ciudad VARCHAR(100),
    fecha_fundacion DATE,
    presupuesto DECIMAL(12, 2) NOT NULL
);

-- Tabla Jugador
CREATE TABLE Jugador (
    id_jugador SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    posicion VARCHAR(50) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    id_equipo INT REFERENCES Equipo(id_equipo)
);

-- Tabla Partido
CREATE TABLE Partido (
    id_partido SERIAL PRIMARY KEY,
    equipo_local INT REFERENCES Equipo(id_equipo),
    equipo_visitante INT REFERENCES Equipo(id_equipo),
    fecha DATE NOT NULL,
    marcador_local INT DEFAULT 0,
    marcador_visitante INT DEFAULT 0
);

-- Índice para optimizar las búsquedas de jugadores por posición
CREATE INDEX idx_jugador_posicion ON Jugador (posicion);

-- Función para calcular la edad de un jugador
CREATE OR REPLACE FUNCTION calcular_edad_jugador(p_id_jugador INT)
RETURNS INT AS $$
DECLARE
    edad INT;
BEGIN
    SELECT EXTRACT(YEAR FROM AGE(fecha_nacimiento)) INTO edad FROM Jugador WHERE id_jugador = p_id_jugador;
    RETURN edad;
END;
$$ LANGUAGE plpgsql;

-- Procedimiento para registrar un partido
CREATE OR REPLACE PROCEDURE registrar_partido(
    p_equipo_local INT,
    p_equipo_visitante INT,
    p_fecha DATE
) AS $$
BEGIN
    INSERT INTO Partido (equipo_local, equipo_visitante, fecha)
    VALUES (p_equipo_local, p_equipo_visitante, p_fecha);
END;
$$ LANGUAGE plpgsql;

-- Trigger para actualizar el presupuesto del equipo cuando se inserta un nuevo jugador
CREATE OR REPLACE FUNCTION actualizar_presupuesto_equipo() RETURNS TRIGGER AS $$
BEGIN
    UPDATE Equipo
    SET presupuesto = presupuesto + 10000 -- Supongamos que cada jugador aumenta el presupuesto en 10000
    WHERE id_equipo = NEW.id_equipo;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_actualizar_presupuesto
BEFORE INSERT ON Jugador
FOR EACH ROW
EXECUTE FUNCTION actualizar_presupuesto_equipo();

-- Trigger para actualizar el marcador de un equipo después de que se inserta un partido
CREATE OR REPLACE FUNCTION actualizar_marcador_equipo() RETURNS TRIGGER AS $$
BEGIN
    UPDATE Equipo
    SET presupuesto = presupuesto + 5000 -- Supongamos que cada partido aumenta el presupuesto en 5000
    WHERE id_equipo = NEW.equipo_local OR id_equipo = NEW.equipo_visitante;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_actualizar_marcador
BEFORE INSERT ON Partido
FOR EACH ROW
EXECUTE FUNCTION actualizar_marcador_equipo();

-- Crear la función para registrar el nuevo jugador
CREATE OR REPLACE FUNCTION log_new_jugador() RETURNS TRIGGER AS $$
BEGIN
    -- Log mensaje con el nombre, equipo y fecha de nacimiento del jugador
    RAISE NOTICE 'Nuevo jugador añadido: % % (Equipo: %, Fecha de Nacimiento: %)', NEW.nombre, NEW.apellido, NEW.id_equipo, NEW.fecha_nacimiento;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Crear el trigger para ejecutar la función cuando se inserta un nuevo jugador
CREATE TRIGGER after_insert_jugador
AFTER INSERT ON Jugador
FOR EACH ROW
EXECUTE FUNCTION log_new_jugador();