
insert into genre VALUES (1, 'romance');
insert into genre VALUES (2, 'comedy');


insert into actor values (1, 'Brad', 'Pitt');
insert into actor values (2, 'Angelina', 'Jolie');

insert into contact_info(id, date_of_birth, gender, actor_id) values (1, '1999-10-11', 'M', 1);
insert into contact_info(id, date_of_birth, gender, actor_id) values (2, '1987-09-12', 'F', 2);

insert into movie(id, release_date, title) values(1, '1999-10-11', 'Titanic');
insert into movie(id, release_date, title) values(2, '2022-09-14', 'The in between');
insert into movie(id, release_date, title) values(3, '2000-10-09', 'Love Rosie');


insert into movie_info(id, description, length, movie_type, movie_id) values (1, 'A fost odata ca niciodata', 90, 'LONG', 1);
insert into movie_info(id, description, length, movie_type, movie_id) values (2, 'A fost odata ca niciodata', 30, 'SHORT', 2);


insert into movie_actor values(1, 1);
insert into movie_actor values(1, 2);
insert into movie_actor values(2, 2);

insert into movie_genre values (1,1);
insert into movie_genre values (1,2);
insert into movie_genre values (2,1);
insert into movie_genre values (3,1);
