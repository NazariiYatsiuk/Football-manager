create table players
(
    id                   bigint auto_increment
        primary key,
    name                 varchar(255) null,
    second_name          varchar(255) null,
    age                  int          not null,
    experience_in_months int          not null,
    team_id              bigint       null,
    unique (name, second_name),
    constraint FK5nglidr00c4dyybl171v6kask
        foreign key (team_id) references teams (id)
);
