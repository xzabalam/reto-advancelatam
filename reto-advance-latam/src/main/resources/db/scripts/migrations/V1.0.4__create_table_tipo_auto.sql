DROP TABLE IF EXISTS tipo_auto CASCADE;
CREATE TABLE tipo_auto
(
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    tipo                VARCHAR(100)  NOT NULL CHECK (tipo IS NOT NULL AND LENGTH(tipo) > 0),
    descripcion         VARCHAR(500) NOT NULL CHECK (descripcion IS NOT NULL AND LENGTH(descripcion) > 0),
    fecha_crea          DATETIME     not null default current_timestamp,
    fecha_modifica      DATETIME,
    id_usuario_crea     bigint       not null default '',
    id_usuario_modifica bigint       null,
    estado              varchar      not null default 'A'
);

insert into tipo_auto(tipo, descripcion,fecha_crea,id_usuario_crea, estado) values('SUV','Sport Utility Vehicle',current_timestamp,0,'A');
insert into tipo_auto(tipo, descripcion,fecha_crea,id_usuario_crea, estado) values('Hatchback','La parte más notable de un hatchback es su escotilla o una puerta trasera que se abre hacia arriba',current_timestamp,0,'A');
insert into tipo_auto(tipo, descripcion,fecha_crea,id_usuario_crea, estado) values('Crossover','Combinación de SUV y hatchback',current_timestamp,0,'A');
insert into tipo_auto(tipo, descripcion,fecha_crea,id_usuario_crea, estado) values('Convertible','Un convertible o descapotable es un vehículo que tiene un techo que se puede quitar o plegar',current_timestamp,0,'A');
insert into tipo_auto(tipo, descripcion,fecha_crea,id_usuario_crea, estado) values('Sedan','Automóvil de pasajeros de cuatro puertas',current_timestamp,0,'A');
insert into tipo_auto(tipo, descripcion,fecha_crea,id_usuario_crea, estado) values('Sports Car','Autos deportivos son convertibles de dos plazas',current_timestamp,0,'A');
insert into tipo_auto(tipo, descripcion,fecha_crea,id_usuario_crea, estado) values('Coupe','Sedán compacto: un automóvil de dos puertas con maletero y techo fijo',current_timestamp,0,'A');
insert into tipo_auto(tipo, descripcion,fecha_crea,id_usuario_crea, estado) values('Minivan',' Automóvil familiar',current_timestamp,0,'A');
insert into tipo_auto(tipo, descripcion,fecha_crea,id_usuario_crea, estado) values('Station Wagon','Versión alargada de un sedán',current_timestamp,0,'A');