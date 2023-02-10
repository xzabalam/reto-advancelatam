DROP TABLE IF EXISTS pico_placa CASCADE;
CREATE TABLE pico_placa
(
    id                      bigint AUTO_INCREMENT PRIMARY KEY,
    restriccion_dia         varchar(10) not null CHECK (restriccion_dia IN ('LUNES', 'MARTES', 'MIERCOLES', 'JUEVES', 'VIERNES', 'SABADO', 'DOMINGO')),
    restriccion_hora_inicio TIME     not null,
    restriccion_hora_fin    TIME     not null,
    fecha_crea              DATETIME not null default current_timestamp,
    fecha_modifica          DATETIME,
    id_usuario_crea         bigint   not null default '',
    id_usuario_modifica     bigint   null,
    estado                  varchar  not null default 'A'
);