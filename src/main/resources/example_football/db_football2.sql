-- Crear el esquema Liga2
CREATE SCHEMA IF NOT EXISTS "football2";

-- Establecer el esquema de búsqueda
SET search_path = 'football2';

-- Tabla Equipo con columna adicional "entrenador"
CREATE TABLE Equipo (
    id_equipo SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    ciudad VARCHAR(100),
    fecha_fundacion DATE,
    presupuesto DECIMAL(12, 2) NOT NULL,
    entrenador VARCHAR(100) -- Columna adicional en este esquema
);

-- Tabla Jugador con una columna adicional "nacionalidad"
CREATE TABLE Jugador (
    id_jugador SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    posicion VARCHAR(50) NOT NULL,
    nacionalidad VARCHAR(50), -- Columna adicional en este esquema
    fecha_nacimiento DATE NOT NULL,
    id_equipo INT REFERENCES Equipo(id_equipo)
);

-- Tabla Partido con columna adicional "arbitro"
CREATE TABLE Partido (
    id_partido SERIAL PRIMARY KEY,
    equipo_local INT REFERENCES Equipo(id_equipo),
    equipo_visitante INT REFERENCES Equipo(id_equipo),
    fecha DATE NOT NULL,
    marcador_local INT DEFAULT 0,
    marcador_visitante INT DEFAULT 0,
    arbitro VARCHAR(100) -- Columna adicional en este esquema
);

-- Índice para optimizar las búsquedas de equipos por ciudad
CREATE INDEX idx_equipo_ciudad ON Equipo (ciudad);

-- Función para calcular la cantidad de partidos jugados por un equipo
CREATE OR REPLACE FUNCTION contar_partidos_equipo(p_id_equipo INT)
RETURNS INT AS $$
DECLARE
    total_partidos INT;
BEGIN
    SELECT COUNT(*) INTO total_partidos
    FROM Partido
    WHERE equipo_local = p_id_equipo OR equipo_visitante = p_id_equipo;
    RETURN total_partidos;
END;
$$ LANGUAGE plpgsql;

-- Procedimiento para registrar un partido con árbitro
CREATE OR REPLACE PROCEDURE registrar_partido_con_arbitro(
    p_equipo_local INT,
    p_equipo_visitante INT,
    p_fecha DATE,
    p_arbitro VARCHAR
) AS $$
BEGIN
    INSERT INTO Partido (equipo_local, equipo_visitante, fecha, arbitro)
    VALUES (p_equipo_local, p_equipo_visitante, p_fecha, p_arbitro);
END;
$$ LANGUAGE plpgsql;
