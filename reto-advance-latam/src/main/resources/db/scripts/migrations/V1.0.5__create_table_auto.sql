DROP TABLE IF EXISTS auto CASCADE;
CREATE TABLE auto
(
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    placa               VARCHAR(10)  NOT NULL CHECK (placa IS NOT NULL AND LENGTH(placa) > 0),
    modelo              VARCHAR(500) NOT NULL CHECK (modelo IS NOT NULL AND LENGTH(modelo) > 0),
    chasis              VARCHAR(50)  NOT NULL CHECK (chasis IS NOT NULL AND LENGTH(chasis) > 0),
    color               VARCHAR(50)  NOT NULL CHECK (color IS NOT NULL AND LENGTH(color) > 0),
    id_tipo_auto                bigint       NOT NULL default '',
    fecha_crea          DATETIME     not null default current_timestamp,
    fecha_modifica      DATETIME,
    id_usuario_crea     bigint       not null default '',
    id_usuario_modifica bigint       null,
    estado              varchar      not null default 'A',
    version             bigint, 
    foreign key (id_tipo_auto) references tipo_auto(id)
);

CREATE UNIQUE INDEX indx_auto_placa ON auto (placa);