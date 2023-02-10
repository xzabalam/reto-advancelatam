DROP TABLE IF EXISTS pico_placa_restriccion CASCADE;
CREATE TABLE pico_placa_restriccion
(
    id                  bigint AUTO_INCREMENT PRIMARY KEY,
    pico_placa_id       bigint   not null,
    ultimo_digito       TINYINT  not null,    
    FOREIGN KEY (pico_placa_id) REFERENCES pico_placa (id)
);