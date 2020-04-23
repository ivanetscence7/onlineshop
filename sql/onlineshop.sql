DROP DATABASE IF EXISTS onlineshop;
CREATE DATABASE onlineshop;
USE onlineshop;

drop table if exists user;
create table user
(
    id         int         not null unique auto_increment,
    firstName  varchar(50) not null,
    lastName   varchar(50) not null,
    patronymic varchar(50),
    login      varchar(30) not null unique,
    password   varchar(30) not null,
    userType   enum ('ADMIN', 'CLIENT'),
    primary key (id)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

drop table if exists session;
create table session
(
    id_user int         not null,
    cookie  varchar(50) not null,
    primary key (id_user),
    foreign key (id_user) references user (id) on update cascade on delete cascade
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

drop table if exists admin;
create table admin
(
    id_admin       int         not null,
    position varchar(50) not null,
    primary key (id_admin),
    foreign key (id_admin) references user (id) on update cascade on delete cascade
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

create table client
(
    id_client    int         not null,
    email   varchar(30) not null,
    address varchar(30) not null,
    phone   varchar(30) not null,
    primary key (id_client),
    foreign key (id_client) references user (id) on update cascade on delete cascade
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

create table deposit
(
    id_client_ref int not null,
    amount    int not null,
    primary key (id_client_ref),
    foreign key (id_client_ref) references client (id_client) on update cascade on delete cascade
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

ALTER TABLE deposit ADD version INT UNSIGNED NOT NULL DEFAULT 0;


create table product
(
    id    int         not null auto_increment,
    name  varchar(50) not null,
    price int         not null,
    count int         not null,
    primary key (id)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

ALTER TABLE product ADD version INT UNSIGNED NOT NULL DEFAULT 0;

create table basket
(
    id_client_ref int not null,
    primary key (id_client_ref),
    foreign key (id_client_ref) references client (id_client) on update cascade on delete cascade
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

create table basket_product
(
    id                      int         not null auto_increment,
    id_basket               int         not null,
    id_product_in_basket    int         not null,
    name_product_in_basket  varchar(50) not null,
    price_product_in_basket int         not null,
    count_product_in_basket int         not null,
    primary key (id),
    foreign key (id_basket) references basket (id_client_ref) on update cascade on delete cascade
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

drop table if exists category;
create table category
(
    id        int         not null auto_increment,
    name      varchar(30) not null unique,
    id_parent int         null,
    primary key (id),
    constraint FK_CATEGORY_in_CATEGORY_as_parent
        foreign key (id_parent) references category (id) on update cascade on delete cascade
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

create table category_product
(
    id          int not null auto_increment,
    id_category int not null,
    id_product  int not null,
    primary key (id),
    foreign key (id_category) references category (id) on update cascade on delete cascade,
    foreign key (id_product) references product (id) on update cascade on delete cascade
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

create table purchase
(
    id              int      not null auto_increment,
    id_client_ref       int      not null,
    date_purchase   datetime not null,
    purchase_amount int      not null,
    primary key (id),
    foreign key (id_client_ref) references client (id_client) on update cascade on delete cascade
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

create table purchase_product
(
    id                     int         not null auto_increment,
    id_purchase            int         not null,
    name_product_purchase  varchar(50) not null,
    price_product_purchase int         not null,
    count_product_purchase int         not null,
    primary key (id),
    foreign key (id_purchase) references purchase (id) on update cascade on delete cascade
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;