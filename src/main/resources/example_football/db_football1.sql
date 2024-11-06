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