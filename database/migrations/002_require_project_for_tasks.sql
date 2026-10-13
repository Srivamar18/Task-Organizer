USE gestor_proyectos_db;

-- Run only after assigning a valid project to every existing task.
ALTER TABLE tareas
    MODIFY COLUMN id_proyecto INT(11) NOT NULL;