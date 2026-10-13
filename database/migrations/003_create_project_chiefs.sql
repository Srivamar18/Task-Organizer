USE gestor_proyectos_db;

CREATE TABLE IF NOT EXISTS proyecto_jefes (
    id_proyecto INT(11) NOT NULL,
    id_jefe INT(11) NOT NULL,
    PRIMARY KEY (id_proyecto, id_jefe),
    KEY idx_proyecto_jefes_jefe (id_jefe),
    CONSTRAINT fk_proyecto_jefes_proyecto
        FOREIGN KEY (id_proyecto) REFERENCES proyectos (id_proyecto)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_proyecto_jefes_jefe
        FOREIGN KEY (id_jefe) REFERENCES usuarios (id_usuario)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;