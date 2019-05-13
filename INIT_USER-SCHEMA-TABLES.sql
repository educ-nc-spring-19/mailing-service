-- create educ_nc_spring_19 database as user 'postgres'
-- CREATE DATABASE educ_nc_spring_19;
-- you must connect to 'educ_nc_spring_19' DB first and after it execute next statements

-- create user
CREATE USER mailing_service WITH PASSWORD 'mailing_service';
-- create schema
CREATE SCHEMA IF NOT EXISTS AUTHORIZATION mailing_service;

-- BEGIN CREATE TABLES

-- BEGIN CREATE TABLE mailing_service.letter
CREATE TABLE mailing_service.letter (
    id uuid PRIMARY KEY,
    date_creating timestamp with time zone NOT NULL,
    header character varying(255) COLLATE pg_catalog."default",
    receiver_id uuid,
    text character varying(4095) COLLATE pg_catalog."default",
    type character varying(255) COLLATE pg_catalog."default"
) WITH (
    OIDS = FALSE
) TABLESPACE pg_default;

ALTER TABLE mailing_service.letter OWNER to mailing_service;
-- END CREATE TABLE mailing_service.letter

-- BEGIN CREATE TABLE mailing_service.template
CREATE TABLE mailing_service.template (
    id uuid PRIMARY KEY,
    creator_id uuid,
    date_creating timestamp with time zone NOT NULL,
    header character varying(255) COLLATE pg_catalog."default",
    text character varying(4095) COLLATE pg_catalog."default",
    type character varying(255) COLLATE pg_catalog."default" UNIQUE
) WITH (
    OIDS = FALSE
) TABLESPACE pg_default;

ALTER TABLE mailing_service.template OWNER to mailing_service;
-- END CREATE TABLE mailing_service.template

-- END CREATE TABLES