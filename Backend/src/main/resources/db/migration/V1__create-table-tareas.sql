CREATE TABLE tareas (
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        titulo VARCHAR(100) NOT NULL,
                        descripcion TEXT DEFAULT NULL,
                        fecha_creacion DATETIME NOT NULL,
                        fecha_limite DATETIME NOT NULL,
                        estado VARCHAR(10) NOT NULL DEFAULT 'PENDIENTE',

                        PRIMARY KEY (id)
);