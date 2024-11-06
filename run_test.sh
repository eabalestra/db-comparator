#!/bin/bash

DB1_SQL_PATH="src/main/resources/example_football/db_football1.sql"
DB2_SQL_PATH="src/main/resources/example_football/db_football2.sql"
PROPERTIES_FILE="src/main/resources/database.properties"

# Solicitar al usuario que ingrese su nombre de usuario y contraseña de PostgreSQL
read -p "Ingrese su nombre de usuario de PostgreSQL: " PG_USER

echo "Eliminando la base de datos existente si existe..."
dropdb --if-exists -U "$PG_USER" football
echo "Creando la base de datos..."
createdb -U "$PG_USER" football

echo "Ejecutando scripts SQL para cargar la base de datos football1..."
psql -U "$PG_USER" -d football -f "$DB1_SQL_PATH"
echo "Ejecutando scripts SQL para cargar la base de datos football2..."
psql -U "$PG_USER" -d football -f "$DB2_SQL_PATH"

echo "Ejecutando la aplicación..."
mvn clean compile && mvn exec:java -Dexec.mainClass="app.Main"