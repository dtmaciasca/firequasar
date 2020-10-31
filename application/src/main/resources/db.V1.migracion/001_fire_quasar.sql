CREATE USER user_fire_quasar WITH
  LOGIN
  NOSUPERUSER
  INHERIT
  NOCREATEDB
  NOCREATEROLE
  NOREPLICATION;

CREATE DATABASE db_fire_quasar WITH OWNER = user_fire_quasar;
GRANT ALL ON DATABASE db_fire_quasar TO user_fire_quasar;

CREATE SCHEMA fire_quasar;
GRANT ALL ON SCHEMA fire_quasar TO user_fire_quasar;

CREATE TABLE fire_quasar.satellite
(
    id serial NOT NULL,
    name character varying(20) COLLATE pg_catalog."default" NOT NULL,
    position_x integer NOT NULL,
    position_y integer NOT NULL,
    fecha_insert timestamp without time zone NOT NULL DEFAULT now(),
    fecha_update timestamp without time zone,
    fecha_delete timestamp without time zone,
    deleted boolean NOT NULL DEFAULT false,
    version integer NOT NULL DEFAULT 1,
    CONSTRAINT pk_satelite PRIMARY KEY (id)
);

CREATE INDEX ix_satelite_name
    ON fire_quasar.satellite USING btree
    (name COLLATE pg_catalog."default")
    TABLESPACE pg_default;

GRANT ALL ON TABLE fire_quasar.satellite TO user_fire_quasar;

CREATE TABLE fire_quasar.message_received
(
    id serial NOT NULL,
    satellite_id integer NOT NULL,
    message text COLLATE pg_catalog."default" NOT NULL,
    distance numeric NOT NULL,
    CONSTRAINT pk_satellite_message PRIMARY KEY (id),
    CONSTRAINT fk_satellite_message_satellite_id FOREIGN KEY (satellite_id)
    REFERENCES fire_quasar.satellite (id)
);

GRANT ALL ON TABLE fire_quasar.message_received TO user_fire_quasar;

insert into fire_quasar.satellite(name, position_x, position_y) values
('kenobi',-500,-200),('skywalker',100,-100),('sato',500,100);
