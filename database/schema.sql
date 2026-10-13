CREATE DATABASE IF NOT EXISTS gestor_proyectos_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_general_ci;

USE gestor_proyectos_db;

CREATE TABLE IF NOT EXISTS usuarios (
    id_usuario INT(11) NOT NULL AUTO_INCREMENT,
    nombre_usuario VARCHAR(50) NOT NULL,
    nombre_completo VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(20) NOT NULL,
    PRIMARY KEY (id_usuario),
    UNIQUE KEY nombre_usuario (nombre_usuario),
    UNIQUE KEY email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS proyectos (
    id_proyecto INT(11) NOT NULL AUTO_INCREMENT,
    id_administrador INT(11) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT DEFAULT NULL,
    fecha_inicio DATE DEFAULT NULL,
    fecha_fin_estimada DATE DEFAULT NULL,
    PRIMARY KEY (id_proyecto),
    KEY fk_proyecto_admin (id_administrador),
    CONSTRAINT fk_proyecto_admin
        FOREIGN KEY (id_administrador) REFERENCES usuarios (id_usuario)
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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

CREATE TABLE IF NOT EXISTS tareas (
    id_tarea INT(11) NOT NULL AUTO_INCREMENT,
    id_proyecto INT(11) NOT NULL,
    id_asignado INT(11) NOT NULL,
    id_asignador INT(11) DEFAULT NULL,
    titulo VARCHAR(100) NOT NULL,
    descripcion TEXT DEFAULT NULL,
    fecha_vencimiento DATE DEFAULT NULL,
    estado VARCHAR(30) NOT NULL,
    prioridad VARCHAR(20) NOT NULL,
    PRIMARY KEY (id_tarea),
    KEY fk_tarea_proyecto (id_proyecto),
    KEY fk_tarea_asignado (id_asignado),
    CONSTRAINT fk_tarea_proyecto
        FOREIGN KEY (id_proyecto) REFERENCES proyectos (id_proyecto)
        ON UPDATE CASCADE,
    CONSTRAINT fk_tarea_asignado
        FOREIGN KEY (id_asignado) REFERENCES usuarios (id_usuario)
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

