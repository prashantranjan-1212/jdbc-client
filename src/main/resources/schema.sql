drop table if exists Post;

create table Post (
    id varchar(255) not null primary key,
    title varchar(255) not null,
    slug varchar(255) not null,
    date date not null,
    time_to_read int not null,
    tags varchar(255) not null
);