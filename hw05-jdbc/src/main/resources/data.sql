insert into authors(full_name)
values ('Shakespeare'), ('Tolkien'), ('Hawking');

insert into genres(name)
values ('fiction'), ('fantasy'), ('science');

insert into books(title, author_id, genre_id)
values ('Romeo and Juliet', 1, 1), ('The Lord of the Rings', 2, 2), ('A Brief History of Time', 3, 3);
