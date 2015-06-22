create table app.Users
(
	id int not null,
	username varchar(40) not null unique,
	password varchar(40) not null,
	email varchar(40) not null,
	allowNotification integer not null check(allowNotification >= 0 and allowNotification <=1),
	level smallint not null,
	score int not null,
	choosenWeaponId int not null,
	primary key(id)	
);



create table app.Weapons(
	id int not null,
	damage int not null,
	price int not null,
	primary key(id)
	
);

create table app.UnlockedWeapons(	
	userId int not null,
	weaponId int not null,
	primary key(userId, weaponId),
	foreign key(userId) references app.Users(id),
	foreign key(weaponId) references app.Weapons(id)
);

insert into app.Weapons values(3,3,3); 
insert into app.Weapons values(2,2,2); 
insert into app.Weapons values(1,1,1); 
insert into app.Users values(2,'2','1','1',1,1,1,1);
insert into app.UnlockedWeapons values(1,1);



drop table app.USERS;
DROP TABLE  app.UnlockedWeapons;