-- Se crea la tabla de usuarios
CREATE TABLE usuarios (
                          id BIGINT NOT NULL AUTO_INCREMENT,
                          email VARCHAR(255) NOT NULL,
                          username VARCHAR(100) NOT NULL,
                          password VARCHAR(255) NOT NULL,

                          PRIMARY KEY (id)
);

-- Se inserta un usuario inicial
INSERT INTO usuarios (email, username, password)
VALUES ('correo@ejemplo.com', 'default', '123456');

-- Se agrega la columna de clave foránea en la tabla tareas
ALTER TABLE tareas
    ADD COLUMN usuario_id BIGINT DEFAULT 1;  -- 1 es el ID del usuario default

-- Ahora se agrega la restricción de clave foránea
ALTER TABLE tareas
    MODIFY usuario_id BIGINT NOT NULL,  -- Se hace la columna NOT NULL después de la actualización
    ADD CONSTRAINT fk_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuarios(id);