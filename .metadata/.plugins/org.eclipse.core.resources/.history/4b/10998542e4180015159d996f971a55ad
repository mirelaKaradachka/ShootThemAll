create database shoot_them_all;
use shoot_them_all;

create table Users(
id int not null auto_increment ,
username varchar(45) not null unique,
password varchar(45) not null,
email varchar (45) not null,
notificationAllow  tinyint(1)  not null default 0,
levelNo smallint not null default 1,
score int not null,
choosen_weapon_id int not null default 1, 
last_activity_on datetime,
primary key(id)
);

create table Weapons(
id int not null auto_increment,
damage int not null,
price int not null,
primary key (id)
);

create table UnlockedWeapons( 
user_id int not null,
weapon_id int not null,
primary key (user_id, weapon_id),
foreign key (weapon_id) references Weapons (id),
foreign key (user_id) references Users (id)
);


