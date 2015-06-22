create table APP.Users(
id int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
username varchar(45) not null unique,
password varchar(45) not null,
email varchar (45) not null,
notificationAllow  int not null check(notificationAllow>=0 and notificationAllow<=1) default 0,
levelNo smallint not null default 1,
score int not null default 0,
choosen_weapon_id int not null default 1,
last_activity_on timestamp, 
primary key(id)
);

create table APP.Weapons(
id int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) ,
damage int not null default 0,
price int not null default 0,
primary key (id)
); 

create table APP.UnlockedWeapons( 
user_id int not null ,
weapon_id int not null,
primary key (user_id, weapon_id),
foreign key (weapon_id) references app.Weapons (id),
foreign key (user_id) references app.Users (id)
);


