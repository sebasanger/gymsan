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


-- EJERCICIOS --
insert into ejercicios (id, nombre, descripcion, categorias_id) values
(1, 'Press banca plano con barra', 'Ejercicio de pecho con barra en banco plano', 1),
(2, 'Press inclinado con mancuernas', 'Ejercicio de pecho superior en banco inclinado', 1),
(3, 'Remo con barra', 'Ejercicio de espalda media con barra', 2),
(4, 'Dominadas', 'Ejercicio de tracción corporal para espalda y bíceps', 2),
(5, 'Sentadillas', 'Ejercicio compuesto para piernas y glúteos', 3),
(6, 'Prensa de piernas', 'Ejercicio de pierna en máquina con carga guiada', 3),
(7, 'Curl de bíceps con barra', 'Ejercicio de brazos enfocado en bíceps', 4),
(8, 'Extensiones de tríceps en polea', 'Ejercicio de brazos enfocado en tríceps', 4),
(9, 'Peso muerto', 'Ejercicio compuesto para piernas y espalda baja', 2),
(10, 'Abdominales crunch', 'Ejercicio de abdomen tradicional en el suelo', 5),
(11, 'Press banca plano con mancueras', 'Ejercicio de pecho con barra en banco plano', 1);
-- EJERCICIOS --

-- EJERCICIOS ENTRENAMIENTOS --
insert into ejercicios_entrenamientos 
(id, ejercicios_id, entrenamientos_id, series, peso, repeticiones) 
values 
(1, 1, 1, 4, 130, 10),
(2, 2, 1, 4, 100, 8),
(3, 3, 2, 4, 100, 10),
(4, 4, 2, 4, 100, 10),
(5, 5, 3, 3, 150, 10),
(6, 6, 3, 3, 400, 10),
(7, 11, 1, 4, 50, 10);

-- EJERCICIOS ENTRENAMIENTOS --


-- EJERCICIOS ALTERNATIVOS --
insert into ejercicios_alternativos 
(ejercicio_entrenamiento_id, alternativo_id) 
values (1, 7);
-- EJERCICIOS ALTERNATIVOS --