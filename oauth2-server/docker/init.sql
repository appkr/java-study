-- NOTE keycloak does not support utf8mb4
create database if not exists `auth` default character set = utf8 default collate = utf8_general_ci;
flush privileges;