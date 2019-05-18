
DROP DATABASE IF EXISTS onlineshop;
CREATE DATABASE onlineshop;
USE onlineshop;

drop table if exists user;
create table user (
  id int not null unique auto_increment,
  firstName varchar(50) not null,
  lastName varchar(50) not null,
  patronymic varchar(50),
  login varchar(30) not null unique,
  password varchar(30) not null,
  userType enum('ADMIN', 'CLIENT'),
  primary key (id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

drop table if exists session;
create table session(
  userId int not null,
  cookie varchar(50) not null,
  primary key(userId),
  foreign key(userId) references user(id) on update cascade on delete cascade
)ENGINE=INNODB DEFAULT CHARSET=utf8;

drop table if exists admin;
create table admin(
  id int not null,
  position varchar(50) not null,
  primary key(id),
  foreign key(id) references user(id) on update cascade on delete cascade
)ENGINE=INNODB DEFAULT CHARSET=utf8;

create table client(
  id_c int not null,
  email varchar(30) not null,
  address varchar(30) not null,
  phone int not null,
  primary key(id_c),
  foreign key(id_c) references user(id) on update cascade on delete cascade
)ENGINE=INNODB DEFAULT CHARSET=utf8;

create table deposit(
  clientId int not null,
  amount int not null,
  primary key(clientId),
  foreign key(clientId) references client(id_c)  on update cascade on delete cascade
)ENGINE=INNODB DEFAULT CHARSET=utf8;

create table product(
  id int not null auto_increment,
  name varchar(50) not null,
  price int not null,
  count int not null,
  primary key(id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

create table basket(
  id_b int not null auto_increment,
  productId int not null,
  clientId int not null,
  primary key(id_b),
  foreign key(productId) references product(id)on update cascade on delete cascade,
  foreign key(clientId) references client(id_c)on update cascade on delete cascade
)ENGINE=INNODB DEFAULT CHARSET=utf8;

drop table if exists category;
create table category(
  id int not null auto_increment,
  name varchar (30) not null unique,
  parentId int null,
  primary key(id),
  constraint FK_CATEGORY_in_CATEGORY_as_parent
  foreign key(parentId) references category(id) on update cascade on delete cascade
)ENGINE=INNODB DEFAULT CHARSET=utf8;

create table category_product(
  id int not null auto_increment,
  id_category int not null,
  id_product int not null,
  primary key(id),
  foreign key(id_category) references category(id)on update cascade on delete cascade,
  foreign key(id_product) references product(id)on update cascade on delete cascade
)ENGINE=INNODB DEFAULT CHARSET=utf8;

create table purchase(
  id int not null auto_increment,
  id_client int not null,
  id_product int not null,
  date_purchase datetime not null,
  money int not null,
  primary key(id),
  foreign key(id_product) references product(id)on update cascade on delete cascade,
  foreign key(id_client) references client(id_c)on update cascade on delete cascade
)ENGINE=INNODB DEFAULT CHARSET=utf8;