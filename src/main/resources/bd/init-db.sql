-- Eliminar la base de datos si existe
DROP DATABASE IF EXISTS tenpo_db;

-- Crear la base de datos desde cero
CREATE DATABASE tenpo_db;

CREATE TABLE clientes (
    id_cliente SERIAL PRIMARY KEY,
    nombre_tenpista VARCHAR(100) NOT NULL
);

CREATE TABLE transacciones (
    id_transaccion SERIAL PRIMARY KEY,
    numero_transaccion INT NOT NULL,
    monto_pesos INT NOT NULL CHECK (monto_pesos > 0),
    giro_comercio VARCHAR(100) NOT NULL,
    fecha_transaccion TIMESTAMP NOT NULL,
    id_cliente INT NOT NULL,

    CONSTRAINT fk_cliente
        FOREIGN KEY (id_cliente)
        REFERENCES clientes(id_cliente)
        ON DELETE CASCADE
);