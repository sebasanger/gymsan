-- ROLES --
insert into roles (id, rol) values (1,'ADMIN');
insert into roles (id, rol) values (2,'ENTRENADOR'); 
insert into roles (id, rol) values (3,'INSTRUCTOR'); 
insert into roles (id, rol) values (4,'RECEPCIONSITA'); 
insert into roles (id, rol) values (5,'CLIENTE'); 
insert into roles (id, rol) values (6,'GIMNASIO'); 
--ROLES--

-- CATEGORIAS --
insert into categorias (id, categoria, tipo) values (1, 'Pecho y Biceps', "ENTRENAMIENTO");
insert into categorias (id, categoria, tipo) values (2, 'Espalda y Triceps',"ENTRENAMIENTO");
insert into categorias (id, categoria, tipo) values (3, 'Piernas',"ENTRENAMIENTO");


insert into categorias (id, categoria, tipo) values (4, 'Pecho',"EJERCICIO");
insert into categorias (id, categoria, tipo) values (5, 'Biceps',"EJERCICIO");
insert into categorias (id, categoria, tipo) values (6, 'Espalda',"EJERCICIO");
insert into categorias (id, categoria, tipo) values (7, 'Triceps',"EJERCICIO");
--CATEGORIAS--

--USESRS--
-- Contraseña: Admin1
insert into usuarios (id, full_name, email, username, password, created_at, last_password_change_at, enabled) 
values (1, 'Sebastian Sangermano', 'seba_sanger@hotmail.com','seba_sanger@hotmail.com','$2a$10$DBJhFdEGTeAqoLLsGfXwYObYXpt/amU0wpsRtKQtwJdC5n.MOXgxC',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);


insert into roles_usuarios (usuarios_id, roles_id) values (1,1);
-- USESRS --


-- RUTINAS --
insert into rutinas (id, nombre, descripcion, activa) values (1,'Rutina para seba Avanzada', "Rutina para pierna, pecho, espalda y biceps avanzada", true); 

-- RUTINAS --


-- RUTINAS USUARIOS --
insert into rutinas_usuarios (usuarios_id, rutinas_id, activa, fecha_inicio, fecha_fin) values (1,1, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- RUTINAS USUARIOS --

-- ENTRENAMIENTOS --
insert into entrenamientos (id, nombre, descripcion, categorias_id) values (1,"Entrenamiento de pecho y biceps", "Entrenamiento de pecho y biceps", 1);
insert into entrenamientos (id, nombre, descripcion, categorias_id) values (2,"Entrenamiento de espalda y triceps", "Entrenamiento de espalda y triceps", 2);
insert into entrenamientos (id, nombre, descripcion, categorias_id) values (3,"Entrenamiento piernas pesado", "Entrenamiento piernas pesado", 3);

-- ENTRENAMIENTOS --

-- ENTRENAMIENTOS RUTINAS--
insert into entrenamientos_rutina (rutinas_id, entrenamientos_id) values (1, 1);
insert into entrenamientos_rutina (rutinas_id, entrenamientos_id) values (1, 2);
insert into entrenamientos_rutina (rutinas_id, entrenamientos_id) values (1, 3);

-- ENTRENAMIENTOS RUTINAS--