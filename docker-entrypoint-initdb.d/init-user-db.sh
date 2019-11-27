#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER user;
    CREATE DATABASE dev;
    GRANT ALL PRIVILEGES ON DATABASE dev TO user;
EOSQL