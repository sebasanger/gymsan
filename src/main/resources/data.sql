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
insert into usuarios (id, full_name, documento, email, username, password, created_at, last_password_change_at, enabled, deleted) 
values 
(1, 'Sebastian Sangermano',"41616608", 'seba_sanger@hotmail.com','seba_sanger@hotmail.com','$2a$10$DBJhFdEGTeAqoLLsGfXwYObYXpt/amU0wpsRtKQtwJdC5n.MOXgxC',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false),
(2, 'Admin',"101010", 'admin@hotmail.com','admin@hotmail.com','$2a$10$DBJhFdEGTeAqoLLsGfXwYObYXpt/amU0wpsRtKQtwJdC5n.MOXgxC',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false),
(3, 'Gimnasio',"0", 'gimnasio@hotmail.com','gimnasio@hotmail.com','$2a$10$DBJhFdEGTeAqoLLsGfXwYObYXpt/amU0wpsRtKQtwJdC5n.MOXgxC',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false),
(4, 'Inactiva', "202020", 'inactiva@hotmail.com','inactiva@hotmail.com','$2a$10$DBJhFdEGTeAqoLLsGfXwYObYXpt/amU0wpsRtKQtwJdC5n.MOXgxC',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false);

insert into roles_usuarios (usuarios_id, roles_id) values (1,5);
insert into roles_usuarios (usuarios_id, roles_id) values (2,1);
insert into roles_usuarios (usuarios_id, roles_id) values (3,6);
-- USESRS --


-- RUTINAS --
insert into rutinas (id, tipo_rutina, nombre, descripcion, deleted) values (1, "PERSONALIZADA" ,'Rutina para seba Avanzada', "Rutina para pierna, pecho, espalda y biceps avanzada", false);
insert into rutinas (id, tipo_rutina, nombre, descripcion, deleted) values (2, "PREDETERMINADA",'Rutina para principiantes', "Rutina para pierna, pecho y esplada", false);  

-- RUTINAS --


-- RUTINAS USUARIOS --
insert into rutinas_usuarios (usuarios_id, rutinas_id) values (1,1);
insert into rutinas_usuarios (usuarios_id, rutinas_id) values (1,2);
-- RUTINAS USUARIOS --

-- ENTRENAMIENTOS --
insert into entrenamientos (id, nombre, descripcion, categorias_id, deleted) values (1,"Entrenamiento de pecho y biceps", "Entrenamiento de pecho y biceps", 1, false);
insert into entrenamientos (id, nombre, descripcion, categorias_id, deleted) values (2,"Entrenamiento de espalda y triceps", "Entrenamiento de espalda y triceps", 2, false);
insert into entrenamientos (id, nombre, descripcion, categorias_id, deleted) values (3,"Entrenamiento piernas pesado", "Entrenamiento piernas pesado", 3, false);

-- ENTRENAMIENTOS --

-- ENTRENAMIENTOS RUTINAS--
insert into entrenamientos_rutina (rutinas_id, entrenamientos_id) values (1, 1);
insert into entrenamientos_rutina (rutinas_id, entrenamientos_id) values (1, 2);
insert into entrenamientos_rutina (rutinas_id, entrenamientos_id) values (1, 3);


insert into entrenamientos_rutina (rutinas_id, entrenamientos_id) values (2, 1);
insert into entrenamientos_rutina (rutinas_id, entrenamientos_id) values (2, 2);
-- ENTRENAMIENTOS RUTINAS--


-- EJERCICIOS --
insert into ejercicios (id, nombre, descripcion, categorias_id, deleted) values
(1, 'press banca plano con barra', 'Ejercicio de pecho con barra en banco plano', 1, false),
(2, 'press inclinado con mancuernas', 'Ejercicio de pecho superior en banco inclinado', 1, false),
(3, 'remo con barra', 'Ejercicio de espalda media con barra', 2, false),
(4, 'dominadas', 'Ejercicio de tracción corporal para espalda y bíceps', 2, false),
(5, 'sentadillas', 'Ejercicio compuesto para piernas y glúteos', 3, false),
(6, 'prensa de piernas', 'Ejercicio de pierna en máquina con carga guiada', 3, false),
(7, 'curl de bíceps con barra', 'Ejercicio de brazos enfocado en bíceps', 4, false),
(8, 'extensiones de tríceps en polea', 'Ejercicio de brazos enfocado en tríceps', 4, false),
(9, 'peso muerto', 'Ejercicio compuesto para piernas y espalda baja', 2, false),
(10, 'abdominales crunch', 'Ejercicio de abdomen tradicional en el suelo', 5, false),
(11, 'press banca plano con mancueras', 'Ejercicio de pecho con barra en banco plano', 1, false);
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



-- FOTOS --
insert into fotos (id, foto) values (1, "https://images.pexels.com/photos/3837757/pexels-photo-3837757.jpeg");
insert into fotos (id, foto) values (2, "https://images.pexels.com/photos/5327556/pexels-photo-5327556.jpeg");
insert into fotos (id, foto) values (3, "https://images.pexels.com/photos/4944955/pexels-photo-4944955.jpeg");
-- FOTOS --

-- VIDEOS --
insert into videos (id, video) values (1, "https://www.youtube.com/shorts/hWbUlkb5Ms4?feature=share");
-- VIDEOS --

-- FOTOS EJERCICIOS --
insert into fotos_ejercicio (fotos_id, ejercicios_id) values (1, 1);
insert into fotos_ejercicio (fotos_id, ejercicios_id) values (2, 1);
insert into fotos_ejercicio (fotos_id, ejercicios_id) values (3, 1);
-- FOTOS EJERCICIOS --

-- FOTOS VIDEOS --
insert into videos_ejercicio (videos_id, ejercicios_id) values (1, 1);
-- FOTOS VIDEOS --

-- MEMBRESIAS --
insert into membresias (id, nombre, precio, descripcion, cantidad_clases, deleted) values 
(1, "Membresia estandar", 50000, "Membresia basica", 0, false),
(2, "Membresia gold", 55000, "Membresia con adicional a 5 clases a inscribirse", 5, false);
-- MEMBRESIAS --

-- MEMBRESIAS USUARIOS --
insert into membresias_usuarios (id, membresias_id, usuarios_id, fecha_inscripcion, fecha_vencimiento, deleted, enabled) values 
(1, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true, false),
(2, 2, 1, CURRENT_TIMESTAMP, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 30 DAY), false, true);
-- MEMBRESIAS USUARIOS --

-- PAGOS --
insert into pagos (id, fecha, monto, transaction, aceptada, descripcion) values 
(1, CURRENT_TIMESTAMP, 50000, "kjkszpj", true, "pago de la mensualidad"),
(2, CURRENT_TIMESTAMP, 55000, "kjkszpj", true, "pago de la mensualidad e inscripcion");
-- PAGOS --

-- PAGOS MEMBRESIAS --
insert into pagos_membresias (membresias_usuarios_id, pagos_id) values (1, 1);
insert into pagos_membresias (membresias_usuarios_id, pagos_id) values (2, 2);
-- PAGOS MEMBRESIAS --


-- POGRESOS RUTINAS --
insert into progresos_rutinas (id, usuarios_id, rutinas_id, entrenamientos_id, check_in, check_out, fecha) values (1, 1, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIME);

insert into progresos_rutinas (id, usuarios_id, rutinas_id, entrenamientos_id, check_in, check_out, fecha) values (2, 1, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIME );
-- POGRESOS RUTINAS --


-- POGRESOS EJERCICIOS --
insert into progresos_ejercicios (id, progresos_rutinas_id, ejercicios_id, cantidad_series) values (1, 1, 1, 4), (2, 1, 2, 4), (3, 2, 2, 4);
-- POGRESOS EJERCICIOS --


-- SERIES --
insert into series (id, repeticiones, peso) values (1, 10, 140), (2, 9, 140), (3, 8, 140), (4, 10, 120);

insert into series (id, repeticiones, peso) values (5, 8, 100), (6, 9, 100), (7, 7, 100), (8, 8, 100);

insert into series (id, repeticiones, peso) values (9, 8, 100), (10, 9, 100), (11, 7, 100), (12, 8, 100);
-- SERIES --


-- SERIES PROGRESOS EJERCICIOS --
insert into series_progresos_rutinas (series_id, progresos_ejercicios_id) values (1, 1), (2, 1), (3, 1), (4, 1);

insert into series_progresos_rutinas (series_id, progresos_ejercicios_id) values (5, 2), (6, 2), (7, 2), (8, 2);

insert into series_progresos_rutinas (series_id, progresos_ejercicios_id) values (9, 3), (10, 3), (11, 3), (12, 3);
-- SERIES PROGRESOS EJERCICIOS --


-- NUEVAS RUTINAS --
insert into rutinas (id, tipo_rutina, nombre, descripcion, deleted) values 
(3, "PERSONALIZADA", 'Rutina Full Body Intermedia', "Rutina de cuerpo completo para nivel intermedio", false),
(4, "PREDETERMINADA", 'Rutina para Avanzados', "Rutina avanzada de fuerza y resistencia", false);

-- RUTINAS USUARIOS --
insert into rutinas_usuarios (usuarios_id, rutinas_id) values 
(1, 3),
(1, 4);

-- NUEVOS ENTRENAMIENTOS --
insert into entrenamientos (id, nombre, descripcion, categorias_id, deleted) values 
(4, "Full Body Intermedio", "Entrenamiento de cuerpo completo intermedio", 1, false),
(5, "Entrenamiento de fuerza avanzada", "Entrenamiento avanzado para fuerza", 2, false);

-- ENTRENAMIENTOS RUTINAS --
insert into entrenamientos_rutina (rutinas_id, entrenamientos_id) values 
(3, 4),
(4, 5);

-- NUEVOS EJERCICIOS --
insert into ejercicios (id, nombre, descripcion, categorias_id, deleted) values
(12, 'dominadas asistidas', 'Ejercicio de tracción con asistencia', 2, false),
(13, 'curl martillo', 'Ejercicio de bíceps con agarre neutro', 5, false),
(14, 'press militar', 'Ejercicio de hombro con barra', 4, false);

-- EJERCICIOS ENTRENAMIENTOS --
insert into ejercicios_entrenamientos (id, ejercicios_id, entrenamientos_id, series, peso, repeticiones) values
(8, 12, 4, 3, 50, 10),
(9, 13, 4, 4, 20, 12),
(10, 14, 5, 4, 40, 8);

-- EJERCICIOS ALTERNATIVOS --
insert into ejercicios_alternativos (ejercicio_entrenamiento_id, alternativo_id) values
(8, 4),
(9, 7);

-- PROGRESOS RUTINAS NUEVOS --
insert into progresos_rutinas (id, usuarios_id, rutinas_id, entrenamientos_id, check_in, check_out, fecha) values
(3, 1, 3, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIME),
(4, 1, 4, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIME);

-- PROGRESOS EJERCICIOS NUEVOS --
insert into progresos_ejercicios (id, progresos_rutinas_id, ejercicios_id, cantidad_series) values
(4, 3, 12, 3),
(5, 3, 13, 4),
(6, 4, 14, 4);

-- SERIES NUEVAS --
insert into series (id, repeticiones, peso) values
(13, 10, 50), (14, 12, 50), (15, 8, 50),
(16, 12, 20), (17, 12, 20), (18, 12, 20), (19, 12, 20),
(20, 8, 40), (21, 8, 40), (22, 8, 40), (23, 8, 40);

-- SERIES PROGRESOS EJERCICIOS NUEVAS --
insert into series_progresos_rutinas (series_id, progresos_ejercicios_id) values
(13, 4), (14, 4), (15, 4),
(16, 5), (17, 5), (18, 5), (19, 5),
(20, 6), (21, 6), (22, 6), (23, 6);
