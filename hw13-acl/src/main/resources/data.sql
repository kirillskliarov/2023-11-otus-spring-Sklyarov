insert into authors(full_name)
values ('Shakespeare'), ('Tolkien'), ('Hawking');

insert into genres(name)
values ('fiction'), ('fantasy'), ('science');

insert into books(title, author_id, genre_id)
values ('Romeo and Juliet', 1, 1), ('The Lord of the Rings', 2, 2), ('A Brief History of Time', 3, 3);

insert into comments(text, book_id)
values ('Первый', 1), ('Второй', 1), ('не читал, но осуждаю', 1),
('Аффтарр жжот', 2), ('норм, мне зашло', 2),
('Кто-нибудь понял что-нибудь?', 3);

insert into users(username, password)
values ('admin', 'password'), ('user', 'qwerty');

insert into roles(name)
values ('ROLE_ADMIN'), ('ROLE_USER');

insert into users_roles(user_id, role_id)
values (1, 1), (2, 2);
