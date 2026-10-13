USE gestor_proyectos_db;

ALTER TABLE tareas
    ADD COLUMN id_proyecto INT(11) NULL AFTER id_tarea,
    ADD KEY fk_tarea_proyecto (id_proyecto),
    ADD CONSTRAINT fk_tarea_proyecto
        FOREIGN KEY (id_proyecto) REFERENCES proyectos (id_proyecto)
        ON UPDATE CASCADE;