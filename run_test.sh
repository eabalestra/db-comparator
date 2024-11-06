#!/bin/bash

DB1_SQL_PATH="src/main/resources/example_football/db_football1.sql"
DB2_SQL_PATH="src/main/resources/example_football/db_football2.sql"
PROPERTIES_FILE="src/main/resources/database.properties"

echo "Eliminando la base de datos existente si existe..."
dropdb --if-exists football
echo "Creando la base de datos..."
createdb football

echo "Ejecutando scripts SQL para cargar la base de datos football1..."
psql -d football -f "$DB1_SQL_PATH"
echo "Ejecutando scripts SQL para cargar la base de datos football2..."
psql -d football -f "$DB2_SQL_PATH"

echo "Ejecutando la aplicación..."
mvn clean compile && mvn exec:java -Dexec.mainClass="app.Main"