create table teams
(
    id                 bigint auto_increment
        primary key,
    title              varchar(255)   null unique,
    balance            decimal(19, 2) null,
    commission_percent decimal(19, 2) null
);
