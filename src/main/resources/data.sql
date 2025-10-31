-- ROLES --
insert into roles (id, rol) values (1,'ADMIN');
insert into roles (id, rol) values (2,'ENTRENADOR'); 
insert into roles (id, rol) values (3,'INSTRUCTOR'); 
insert into roles (id, rol) values (4,'RECEPCIONSITA'); 
insert into roles (id, rol) values (5,'CLIENTE'); 
insert into roles (id, rol) values (6,'GIMNASIO'); 
--ROLES--

--USESRS--
-- Contraseña: Admin1
insert into usuarios (id, full_name, email, username, password, created_at, last_password_change_at, enabled) 
values (1, 'Sebastian Sangermano', 'seba_sanger@hotmail.com','seba_sanger@hotmail.com','$2a$10$DBJhFdEGTeAqoLLsGfXwYObYXpt/amU0wpsRtKQtwJdC5n.MOXgxC',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, true);

insert into roles_usuarios (usuarios_id, roles_id) values (1,1);

-- Contraseña: admin
--insert into users (id, full_name, email, username, password, avatar, created_at, last_password_change_at,enabled) 
--values (2, 'Admin', 'admin@hotmail.com','admin@hotmail.com','$2a$04$4X2.gX.iYZpqyJGliIDP.evdubunBFAOebikWxGcJp74QMeVX1UTC','',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,true);


-- Contraseña: user
--insert into users (id, full_name, email, username, password, avatar, created_at, last_password_change_at,enabled) 
--values (3, 'User', 'user@hotmail.com','user@hotmail.com','$2a$04$xwhhMoyJDNiOxhnnbATDO.YZAAElNcSIf.y7G8.cBhH8IHSlaOgsG','',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,true);


-- USESRS --
