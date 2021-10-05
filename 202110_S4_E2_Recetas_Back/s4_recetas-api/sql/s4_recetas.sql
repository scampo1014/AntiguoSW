delete from CalificacionEntity;
delete from ComentarioEntity;
delete from VideoEntity;
delete from FotoEntity;
delete from IngredienteEntity;
delete from UtensilioEntity;
delete from Recetaentity;
delete from UsuarioEntity;
delete from AnuncioEntity;
delete from metodoPagoEntity;
delete from ProveedorEntity;


insert into UsuarioEntity(id,login,correo,contrasenia,nombre,foto) values (100,'login100','correo100','contrasenia100','Juana','https://image.jimcdn.com/app/cms/image/transf/none/path/sc1b3202e1ddf50d9/image/i55b3678ea605e776/version/1423158650/chef-vivir-en-australia-emigrar-a-australia-migracion-trabajo-en-el-extranjero-trabajar-en-australia.jpg');
insert into UsuarioEntity(id,login,correo,contrasenia,nombre,foto) values (101,'login101','correo101','contrasenia101','Juan','https://www.slowfood.com/wp-content/uploads/network/Bledar-Kola-SFCA.jpg');
insert into UsuarioEntity(id,login,correo,contrasenia,nombre,foto) values (102,'login102','correo102','contrasenia102','Juanita','https://image.freepik.com/vector-gratis/chef-mujer-trabajadora-avatar_18591-58459.jpg');
insert into UsuarioEntity(id,login,correo,contrasenia,nombre,foto) values (103,'login103','correo103','contrasenia103','Juanito','https://www.revistaerrante.com/sites/default/files/styles/crop_vertical/public/2020-05/the%20Chef%20is%20back_7.jpg?itok=gScMMjVI');
insert into UsuarioEntity(id,login,correo,contrasenia,nombre,foto) values (104,'login104','correo104','contrasenia104','Maria','https://image.freepik.com/foto-gratis/chef-mujer-vertiendo-comida-placa_23-2148763152.jpg');
insert into UsuarioEntity(id,login,correo,contrasenia,nombre,foto) values (105,'login105','correo105','contrasenia105','Mario','https://s.ineventos.com/blog/2021/100181/013031-1080w.jpg');
insert into UsuarioEntity(id,login,correo,contrasenia,nombre,foto) values (106,'login106','correo106','contrasenia106','Ana','https://image.freepik.com/foto-gratis/retrato-feliz-chef-mujer-adornando-comida_107420-17570.jpg');
insert into UsuarioEntity(id,login,correo,contrasenia,nombre,foto) values (107,'login107','correo107','contrasenia107','Carlos','https://lasallecuernavaca.edu.mx/CC5/171174/wp-content/uploads/2019/12/3.jpg');
insert into UsuarioEntity(id,login,correo,contrasenia,nombre,foto) values (108,'login108','correo108','contrasenia108','Pedro','https://cdn.theforkmanager.com/static/styles/blog_article_header_image/public/wp-blog/el-tenedor-el-chef-perfecto-gestion-de-restaurantes-2.jpg?itok=2bdW5s8O');

insert into ProveedorEntity(id,login,correo,contrasenia,nombre) values (100,'login100','correo100','contrasenia100','proveedor100');
insert into ProveedorEntity(id,login,correo,contrasenia,nombre) values (101,'login101','correo101','contrasenia101','proveedor101');
insert into ProveedorEntity(id,login,correo,contrasenia,nombre) values (102,'login102','correo102','contrasenia102','proveedor102');
insert into ProveedorEntity(id,login,correo,contrasenia,nombre) values (103,'login103','correo103','contrasenia103','proveedor103');
insert into ProveedorEntity(id,login,correo,contrasenia,nombre) values (104,'login104','correo104','contrasenia104','proveedor104');
insert into ProveedorEntity(id,login,correo,contrasenia,nombre) values (105,'login105','correo105','contrasenia105','proveedor105');
insert into ProveedorEntity(id,login,correo,contrasenia,nombre) values (106,'login106','correo106','contrasenia106','proveedor106');
insert into ProveedorEntity(id,login,correo,contrasenia,nombre) values (107,'login107','correo107','contrasenia107','proveedor107');

insert into MetodoPagoEntity(id, metodoPago, proveedor_id) values (100, 'metodoPago100',100);
insert into MetodoPagoEntity(id, metodoPago, proveedor_id) values (101, 'metodoPago101',100);
insert into MetodoPagoEntity(id, metodoPago, proveedor_id) values (102, 'metodoPago102',101);
insert into MetodoPagoEntity(id, metodoPago, proveedor_id) values (103, 'metodoPago103',101);
insert into MetodoPagoEntity(id, metodoPago, proveedor_id) values (104, 'metodoPago104',102);
insert into MetodoPagoEntity(id, metodoPago, proveedor_id) values (105, 'metodoPago105',103);
insert into MetodoPagoEntity(id, metodoPago, proveedor_id) values (106, 'metodoPago106',103);
insert into MetodoPagoEntity(id, metodoPago, proveedor_id) values (107, 'metodoPago107',103);

insert into AnuncioEntity(id, nombre,costo, duracion, contenido, medioDePago, tiempoDisponible, proveedor_id) values (100, 'anuncio101', 400, 800, 'contenido100', 'medioDePago100', 600,100);
insert into AnuncioEntity(id, nombre,costo, duracion, contenido, medioDePago, tiempoDisponible, proveedor_id) values (101, 'anuncio101', 401, 801, 'contenido100', 'medioDePago100', 601,100);
insert into AnuncioEntity(id, nombre,costo, duracion, contenido, medioDePago, tiempoDisponible, proveedor_id) values (102, 'anuncio101', 402, 802, 'contenido100', 'medioDePago100', 602,101);
insert into AnuncioEntity(id, nombre,costo, duracion, contenido, medioDePago, tiempoDisponible, proveedor_id) values (103, 'anuncio101', 403, 803, 'contenido100', 'medioDePago100', 603,104);
insert into AnuncioEntity(id, nombre,costo, duracion, contenido, medioDePago, tiempoDisponible, proveedor_id) values (104, 'anuncio101', 404, 804, 'contenido100', 'medioDePago100', 604,104);
insert into AnuncioEntity(id, nombre,costo, duracion, contenido, medioDePago, tiempoDisponible, proveedor_id) values (105, 'anuncio101', 405, 805, 'contenido100', 'medioDePago100', 605,105);
insert into AnuncioEntity(id, nombre,costo, duracion, contenido, medioDePago, tiempoDisponible, proveedor_id) values (106, 'anuncio101', 406, 806, 'contenido100', 'medioDePago100', 606,106);
insert into AnuncioEntity(id, nombre,costo, duracion, contenido, medioDePago, tiempoDisponible) values (107, 'anuncio101', 407, 807, 'contenido100', 'medioDePago100', 607);

insert into RecetaEntity(id,calorias,cantingredientes,descripcion,dificultad,nombre,popular,tiempoprep,Usuario_ID) values (100,400,5,'Receta100','Baja','Arroz con pollo',1,'50min',100);
insert into RecetaEntity(id,calorias,cantingredientes,descripcion,dificultad,nombre,popular,tiempoprep,Usuario_ID) values (101,450,15,'Receta101','Alta','Sopa de tomate',1,'150min',100);
insert into RecetaEntity(id,calorias,cantingredientes,descripcion,dificultad,nombre,popular,tiempoprep,Usuario_ID) values (102,40,2,'Receta102','Baja','Tiramisu de café',0,'10min',100);
insert into RecetaEntity(id,calorias,cantingredientes,descripcion,dificultad,nombre,popular,tiempoprep,Usuario_ID) values (103,45,6,'Receta103','Baja','Pastel de jamón y queso',1,'40min',101);
insert into RecetaEntity(id,calorias,cantingredientes,descripcion,dificultad,nombre,popular,tiempoprep,Usuario_ID) values (104,500,8,'Receta104','Alta','Pavo relleno',0,'160min',101);
insert into RecetaEntity(id,calorias,cantingredientes,descripcion,dificultad,nombre,popular,tiempoprep,Usuario_ID) values (105,1000,10,'Receta105','Baja','Arroz con leche',0,'50min',102);
insert into RecetaEntity(id,calorias,cantingredientes,descripcion,dificultad,nombre,popular,tiempoprep,Usuario_ID) values (106,800,3,'Receta106','Baja','Mojarra con arroz con coco',0,'30min',102);
insert into RecetaEntity(id,calorias,cantingredientes,descripcion,dificultad,nombre,popular,tiempoprep,Usuario_ID) values (107,200,5,'Receta107','Baja','Empanadas',0,'120min',103);
insert into RecetaEntity(id,calorias,cantingredientes,descripcion,dificultad,nombre,popular,tiempoprep,Usuario_ID) values (108,320,12,'Receta108','Media','Ajiaco',1,'50min',103);
insert into RecetaEntity(id,calorias,cantingredientes,descripcion,dificultad,nombre,popular,tiempoprep,Usuario_ID) values (109,650,6,'Receta109','Media','Flan de tres leches',0,'180min',104);
insert into RecetaEntity(id,calorias,cantingredientes,descripcion,dificultad,nombre,popular,tiempoprep,Usuario_ID) values (110,220,4,'Receta110','Baja','Galletas de avena',0,'60min',105);
insert into RecetaEntity(id,calorias,cantingredientes,descripcion,dificultad,nombre,popular,tiempoprep,Usuario_ID) values (111,120,2,'Receta111','Baja','Crema de calabaza',1,'60min',101);

insert into IngredienteEntity(ID,nombre,precio,cantidad,Receta_ID) values (100,'Zanahoria',1000,'2 unidades',100);
insert into IngredienteEntity(ID,nombre,precio,cantidad,Receta_ID) values (101,'Pepino',2000,'1 unidad',100);
insert into IngredienteEntity(ID,nombre,precio,cantidad,Receta_ID) values (102,'Ajo',10000,'10 dientes',100);
insert into IngredienteEntity(ID,nombre,precio,cantidad,Receta_ID) values (103,'Sal',4500,'3 cucharadas',102);
insert into IngredienteEntity(ID,nombre,precio,cantidad,Receta_ID) values (104,'Lechuga',1000,'2 hojas',102);
insert into IngredienteEntity(ID,nombre,precio,cantidad,Receta_ID) values (105,'Leche',1000,'2 tazas',103);
insert into IngredienteEntity(ID,nombre,precio,cantidad,Receta_ID) values (106,'Sal',4500,'3 cucharaditas',103);
insert into IngredienteEntity(ID,nombre,precio,cantidad,Receta_ID) values (107,'Arroz',1000,'2 tazas',103);
insert into IngredienteEntity(ID,nombre,precio,cantidad,Receta_ID) values (108,'Zanahoria',20000,'2 unidades',103);

insert into UtensilioEntity(ID,nombre,precio,descripcion,Receta_ID) values (100,'Cuchara',10000,'Cuchara grande', 100);
insert into UtensilioEntity(ID,nombre,precio,descripcion,Receta_ID) values (101,'Sarten',100000,'Sarten antiadherente', 102);
insert into UtensilioEntity(ID,nombre,precio,descripcion,Receta_ID) values (102,'Olla',80000,'Olla grande', 103);
insert into UtensilioEntity(ID,nombre,precio,descripcion,Receta_ID) values (103,'Licuadora',100000,'Licuadura cualquiera', 103);
insert into UtensilioEntity(ID,nombre,precio,descripcion,Receta_ID) values (104,'Sarten',50000,'Sarten pequeño', 105);
insert into UtensilioEntity(ID,nombre,precio,descripcion,Receta_ID) values (105,'Cuchara',10000,'Cuchara grande', 108);
insert into UtensilioEntity(ID,nombre,precio,descripcion,Receta_ID) values (106,'Cuchara',10000,'Cuchara pequeña', 108);
insert into UtensilioEntity(ID,nombre,precio,descripcion,Receta_ID) values (107,'Taza',20000,'Taza 10onz', 109);
insert into UtensilioEntity(ID,nombre,precio,descripcion,Receta_ID) values (108,'Horno',1000000,'Horno cualquiera', 110);

insert into ComentarioEntity (id, aprobado, comentario, positivo, receta_id, usuario_id, proveedor_id) values (101, 0, 'Vestibulum ac est lacinia nisi venenatis tristique. Fusce congue, diam id ornare imperdiet, sapien urna pretium nisl, ut volutpat sapien arcu sed augue. Aliquam erat volutpat. In congue.', 0, 100, null, null);
insert into ComentarioEntity (id, aprobado, comentario, positivo, receta_id, usuario_id, proveedor_id) values (102, 0, 'Phasellus id sapien in sapien iaculis congue. Vivamus metus arcu, adipiscing molestie, hendrerit at, vulputate vitae, nisl. Aenean lectus. Pellentesque eget nunc.', 0, 107, null, null);
insert into ComentarioEntity (id, aprobado, comentario, positivo, receta_id, usuario_id, proveedor_id) values (103, 0, 'Ut tellus. Nulla ut erat id mauris vulputate elementum. Nullam varius.', 1, 105, null, null);
insert into ComentarioEntity (id, aprobado, comentario, positivo, receta_id, usuario_id, proveedor_id) values (104, 0, 'Maecenas rhoncus aliquam lacus. Morbi quis tortor id nulla ultrices aliquet. Maecenas leo odio, condimentum id, luctus nec, molestie sed, justo.', 1, 100, null, null);
insert into ComentarioEntity (id, aprobado, comentario, positivo, receta_id, usuario_id, proveedor_id) values (105, 0, 'Nam ultrices, libero non mattis pulvinar, nulla pede ullamcorper augue, a suscipit nulla elit ac nulla. Sed vel enim sit amet nunc viverra dapibus. Nulla suscipit ligula in lacus.', 1, 102, null, null);
insert into ComentarioEntity (id, aprobado, comentario, positivo, receta_id, usuario_id, proveedor_id) values (106, 0, 'Aliquam erat volutpat. In congue. Etiam justo. Etiam pretium iaculis justo.', 0, 107, null, null);
insert into ComentarioEntity (id, aprobado, comentario, positivo, receta_id, usuario_id, proveedor_id) values (107, 1, 'Proin eu mi. Nulla ac enim. In tempor, turpis nec euismod scelerisque, quam turpis adipiscing lorem, vitae mattis nibh ligula nec sem.', 1, 108, null, null);
insert into ComentarioEntity (id, aprobado, comentario, positivo, receta_id, usuario_id, proveedor_id) values (108, 1, 'In quis justo.', 1, null, 100, null);
insert into ComentarioEntity (id, aprobado, comentario, positivo, receta_id, usuario_id, proveedor_id) values (109, 1, 'In hac habitasse platea dictumst. Etiam faucibus cursus urna.', 0, 101, null, null);
insert into ComentarioEntity (id, aprobado, comentario, positivo, receta_id, usuario_id, proveedor_id) values (110, 1, 'Proin interdum mauris non ligula pellentesque ultrices. Phasellus id sapien in sapien iaculis congue. Vivamus metus arcu, adipiscing molestie, hendrerit at, vulputate vitae, nisl.', 0, 100, null, null);
insert into ComentarioEntity (id, aprobado, comentario, positivo, receta_id, usuario_id, proveedor_id) values (111, 1, 'Proin eu mi. Nulla ac enim. In tempor, turpis nec euismod scelerisque, quam turpis adipiscing lorem, vitae mattis nibh ligula nec sem.', 1, null, null, 100);
insert into ComentarioEntity (id, aprobado, comentario, positivo, receta_id, usuario_id, proveedor_id) values (112, 1, 'In quis justo.', 1, null, null, 105);
insert into ComentarioEntity (id, aprobado, comentario, positivo, receta_id, usuario_id, proveedor_id) values (113, 1, 'In hac habitasse platea dictumst. Etiam faucibus cursus urna.', 0, null, null, 106);
insert into ComentarioEntity (id, aprobado, comentario, positivo, receta_id, usuario_id, proveedor_id) values (114, 1, 'Proin interdum mauris non ligula pellentesque ultrices. Phasellus id sapien in sapien iaculis congue. Vivamus metus arcu, adipiscing molestie, hendrerit at, vulputate vitae, nisl.', 0, null, null, 106);


insert into FotoEntity (id, formato, direccion, tamanio, receta_id) values (101, '.bmp', 'https://cdn.kiwilimon.com/recetaimagen/30884/35199.jpg', 3599954, 103);
insert into FotoEntity (id, formato, direccion, tamanio, receta_id) values (103, '.jpeg', 'https://i.ytimg.com/vi/3FHRKGewq6o/maxresdefault.jpg', 3711214, 103);
insert into FotoEntity (id, formato, direccion, tamanio, receta_id) values (104, '.jpeg', 'https://saborgourmet.com//wp-content/uploads/pastel-papa-jamon-queso-pasos-600x400.jpg', 10568501, 103);
insert into FotoEntity (id, formato, direccion, tamanio, receta_id) values (105, '.gif', 'https://www.alqueria.com.co/sites/default/files/receta-de-sopa-de-tomate_2.jpg', 11312252, 101);
insert into FotoEntity (id, formato, direccion, tamanio, receta_id) values (106, '.png', 'https://img-global.cpcdn.com/recipes/ee4d30c7fb5bac63/751x532cq70/arroz-de-coco-con-mojarra-frita-%F0%9F%A4%A4-foto-principal.jpg', 3998650, 106);
insert into FotoEntity (id, formato, direccion, tamanio, receta_id) values (107, '.png', 'https://www.vvsupremo.com/wp-content/uploads/2015/11/900X570_Classic-Tomato-Soup.jpg', 12268175, 101);
insert into FotoEntity (id, formato, direccion, tamanio, receta_id) values (108, '.bmp', 'https://www.hola.com/imagenes/cocina/recetas/20191105153224/pavo-relleno-tradicional-accion-gracias/0-741-738/pavo-relleno-salsa-arandanos-m.jpg', 128125, 104);
insert into FotoEntity (id, formato, direccion, tamanio, receta_id) values (109, '.gif', 'https://cdn.kiwilimon.com/recetaimagen/37505/th5-640x426-47375.jpg', 12973325, 102);
insert into FotoEntity (id, formato, direccion, tamanio, receta_id) values (111, '.jpg', 'https://images.rappi.com/products/3b2a9a0b-1237-4aeb-8598-1e165f744cbc-1585878277373.png?d=128x90', 2177194, 100);
insert into FotoEntity (id, formato, direccion, tamanio, receta_id) values (112, '.jpg', 'https://t1.rg.ltmcdn.com/es/images/9/0/0/autentico_pavo_relleno_para_el_dia_de_accion_de_gracias_10009_orig.jpg', 2177194, 104);
insert into FotoEntity (id, formato, direccion, tamanio, receta_id) values (113, '.jpg', 'https://cdn.colombia.com/gastronomia/2011/08/04/arroz-de-leche-1618.gif', 2177194, 105);
insert into FotoEntity (id, formato, direccion, tamanio, receta_id) values (114, '.jpg', 'https://i.pinimg.com/474x/c0/b3/fb/c0b3fbad996e1633b90300fccf8ab809.jpg', 2177194, 105);
insert into FotoEntity (id, formato, direccion, tamanio, receta_id) values (115, '.jpg', 'https://www.goya.com/media/6693/argentinean-style-beef-empanadas-new.jpg?quality=80', 2177194, 107);
insert into FotoEntity (id, formato, direccion, tamanio, receta_id) values (117, '.jpg', 'https://www.comidascolombianas.com/wp-content/uploads/Ajiaco-Santafere%C3%B1o.jpg', 2177194, 108);
insert into FotoEntity (id, formato, direccion, tamanio, receta_id) values (110, '.jpg', 'https://bigoven-res.cloudinary.com/image/upload/t_recipe-256/vanilla-flan-b83077-04d414cfb0aa02db0c8a0e5f.jpg', 2177194, 109);
insert into FotoEntity (id, formato, direccion, tamanio, receta_id) values (102, '.jpg', 'https://cdn.kiwilimon.com/recetaimagen/3145/th5-640x640-3466.jpg', 2177194, 110);
insert into FotoEntity (id, formato, direccion, tamanio, receta_id) values (100, '.jpg', 'https://okdiario.com/img/2018/02/22/crema-calabaza-zanahoria.jpg', 2177194, 111);

insert into VideoEntity (id, formato, direccion, tamanio, duracion, receta_id) values (101, '.wmv', 'https://www.youtube.com/embed/YZwm91Lebzg', 46718216, 587, 103);
insert into VideoEntity (id, formato, direccion, tamanio, duracion, receta_id) values (102, '.wmv', 'https://www.youtube.com/embed/UEgMll-cS7o', 533850811, 400, 106);
insert into VideoEntity (id, formato, direccion, tamanio, duracion, receta_id) values (103, '.mov', 'https://www.youtube.com/embed/c9cADx4wCWs', 214788687, 77, 111);
insert into VideoEntity (id, formato, direccion, tamanio, duracion, receta_id) values (104, '.wmv', 'https://www.youtube.com/embed/Kd-T_oc7Xhg', 48277627, 259, 106);
insert into VideoEntity (id, formato, direccion, tamanio, duracion, receta_id) values (105, '.wmv', 'https://www.youtube.com/embed/2eKYzlGgTvQ', 369981853, 3, 100);
insert into VideoEntity (id, formato, direccion, tamanio, duracion, receta_id) values (106, '.mp4', 'https://www.youtube.com/embed/hLFS5WvARdU', 143045674, 562, 108);
insert into VideoEntity (id, formato, direccion, tamanio, duracion, receta_id) values (107, '.wmv', 'https://www.youtube.com/embed/BQn06MxDXbU', 134620344, 446, 101);
insert into VideoEntity (id, formato, direccion, tamanio, duracion, receta_id) values (108, '.mov', 'https://www.youtube.com/embed/yIyIznsRM6s', 83835836, 563, 108);
insert into VideoEntity (id, formato, direccion, tamanio, duracion, receta_id) values (109, '.avi', 'https://www.youtube.com/embed/aTJZlAJ8B7k', 246898060, 148, 108);
insert into VideoEntity (id, formato, direccion, tamanio, duracion, receta_id) values (110, '.mp4', 'https://www.youtube.com/embed/QVGaVXmxHF8', 117429237, 517, 101);

insert into CalificacionEntity (id, calificador, puntos, Comentario_ID, Proveedor_ID, Receta_ID, Usuario_ID) values (101, 'Lindsay Nevison', 3.9, null, null, null, 100);
insert into CalificacionEntity (id, calificador, puntos, Comentario_ID, Proveedor_ID, Receta_ID, Usuario_ID) values (102, 'Pier Rymill', 2.3, null, null, null, 102);
insert into CalificacionEntity (id, calificador, puntos, Comentario_ID, Proveedor_ID, Receta_ID, Usuario_ID) values (103, 'Cherianne Aucock', 2.1, null, null, null, 103);
insert into CalificacionEntity (id, calificador, puntos, Comentario_ID, Proveedor_ID, Receta_ID, Usuario_ID) values (104, 'Pet Boakes', 2.0, null, null, 100, null);
insert into CalificacionEntity (id, calificador, puntos, Comentario_ID, Proveedor_ID, Receta_ID, Usuario_ID) values (105, 'Christabel O''Toole', 2.2, null, null, 105, null);
insert into CalificacionEntity (id, calificador, puntos, Comentario_ID, Proveedor_ID, Receta_ID, Usuario_ID) values (106, 'Buddy Johnke', 1.7, null, null, 106, null);
insert into CalificacionEntity (id, calificador, puntos, Comentario_ID, Proveedor_ID, Receta_ID, Usuario_ID) values (107, 'Louisa Alfonso', 2.1, 103, null, null, null);
insert into CalificacionEntity (id, calificador, puntos, Comentario_ID, Proveedor_ID, Receta_ID, Usuario_ID) values (108, 'Deeanne O''Fairy', 2.4, 108, null, null, null);
insert into CalificacionEntity (id, calificador, puntos, Comentario_ID, Proveedor_ID, Receta_ID, Usuario_ID) values (109, 'Doti Matus', 3.5, null, 101, null, null);
insert into CalificacionEntity (id, calificador, puntos, Comentario_ID, Proveedor_ID, Receta_ID, Usuario_ID) values (110, 'Emelina Wilshin', 3.3, 101, null, null, null);
insert into CalificacionEntity (id, calificador, puntos, Comentario_ID, Proveedor_ID, Receta_ID, Usuario_ID) values (111, 'Rollie Coe', 4.6, null, 101, null, null);
insert into CalificacionEntity (id, calificador, puntos, Comentario_ID, Proveedor_ID, Receta_ID, Usuario_ID) values (112, 'Mickie McHan', 1.9, null, null, 103, null);
insert into CalificacionEntity (id, calificador, puntos, Comentario_ID, Proveedor_ID, Receta_ID, Usuario_ID) values (113, 'Shanon Biskup', 2.4, null, null, 108, null);
insert into CalificacionEntity (id, calificador, puntos, Comentario_ID, Proveedor_ID, Receta_ID, Usuario_ID) values (114, 'Lin Garmon', 3.6, null, 105, null, null);
insert into CalificacionEntity (id, calificador, puntos, Comentario_ID, Proveedor_ID, Receta_ID, Usuario_ID) values (115, 'Shay Parmiter', 3.9, null, 107, null, null);
insert into CalificacionEntity (id, calificador, puntos, Comentario_ID, Proveedor_ID, Receta_ID, Usuario_ID) values (116, 'Sean Parmiter', 3.9, null, 100, null, null);

/*select * from RecetaEntity;
select * from UsuarioEntity;
select * from IngredienteEntity;
select * from UtensilioEntity;
select * from ComentarioEntity;
select * from CalificacionEntity;
select * from AnuncioEntity;*/

select * from RecetaEntity;
select * from VideoEntity;