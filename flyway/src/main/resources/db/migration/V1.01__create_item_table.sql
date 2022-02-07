create table customers
(
    id         int primary key auto_increment,
    first_name varchar(128) not null,
    last_name  varchar(128) not null,
    email      varchar(128) not null
);

INSERT INTO customers
VALUES (default, "太郎", "山田⛰", "example@example.com"),
       (default, "花子🌼", "佐藤🍬", "example@example.com");
