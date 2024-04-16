INSERT INTO public.film(id, name, release_date, description)
VALUES
    (1, 'Interstellar', '2014-01-01', 'Interstellar movie'),
    (2, 'The Theory of Everything', '2014-01-01', 'The Theory of Everything description'),
    (3, 'La La Land', '2016-01-01', 'La La Land, description'),
    (4, 'Deadpool', '2016-01-01', 'Deadpool, description'),
    (5, 'Joker', '2019-01-01', 'The Theory of Everything description'),
    (6, 'Avengers: Endgame', '2019-01-01', 'Avengers: Endgame description'),
    (7, 'Dune', '2021-01-01', 'Dune description'),
    (8, 'Dune: Part Two', '2024-01-01', 'Dune: Part Two description');

INSERT INTO public.users(id, name, email, username, password)
VALUES
    (1, 'admin', '2014-01-01', 'adminlogin', 'password'),
    (2, 'Dmitry', 'litvinko@mail.ru', 'dilit', 'password'),
    (3, 'Igor', 'igor@mail.ru', 'igorlog', 'password'),
    (4, 'Alyona', 'alyona@mail.ru', 'alyonalog', 'password'),
    (5, 'Vova', 'Vova@mail.ru', 'Vovalog', 'password');

INSERT INTO public.comment(id, text, user_id, film_id)
VALUES
    (1, 'Great storyline!', '1', '2'),
    (2, 'Superb acting!', '1', '3'),
    (3, 'Incredible special effects!', '1', '6'),
    (4, 'Awesome soundtrack!', '1', '8'),
    (5, 'Fantastic cinematography!', '2', '3'),
    (6, 'Breathtaking scenery!', '2', '4'),
    (7, 'Mesmerizing performances!', '2', '7'),
    (8, 'Captivating plot twists!', '2', '8'),
    (9, 'Stunning visual effects!', '3', '1'),
    (10, 'Emotionally gripping!', '3', '4'),
    (11, 'Riveting storytelling!', '3', '6'),
    (12, 'Unforgettable characters!', '4', '1'),
    (13, 'Intense action sequences!', '4', '3'),
    (14, 'Mind-blowing cinematography!', '4', '2'),
    (15, 'Powerful soundtrack!', '4', '4'),
    (16, 'Heart-pounding suspense!', '4', '8'),
    (17, 'Epic adventure!', '5', '1'),
    (18, 'Enchanting romance!', '5', '2'),
    (19, 'Exciting twists and turns!', '5', '3'),
    (20, 'Captivating performances!', '5', '4'),
    (21, 'Unforgettable journey!', '5', '5');

INSERT INTO public.rating(id, points, user_id, film_id)
VALUES
    (1, 1, '1', '2'),
    (2, 2, '1', '3'),
    (3, 3, '1', '6'),
    (4, 4, '1', '8'),
    (5, 5, '2', '3'),
    (6, 6, '2', '4'),
    (7, 7, '2', '7'),
    (8, 8, '2', '8'),
    (9, 9, '3', '1'),
    (10, 10, '3', '4'),
    (11, 1, '3', '6'),
    (12, 2, '4', '1'),
    (13, 3, '4', '3'),
    (14, 4, '4', '2'),
    (15, 5, '4', '4'),
    (16, 6, '4', '8'),
    (17, 7, '5', '1'),
    (18, 8, '5', '2'),
    (19, 9, '5', '3'),
    (20, 10, '5', '4'),
    (21, 1, '5', '5');

INSERT INTO public.role(id, name)
VALUES
    (1, 'ADMIN'),
    (2, 'USER');

INSERT INTO public.user_role(user_id, role_id)
VALUES
    (1, 1),
    (1, 2),
    (2, 2),
    (3, 2),
    (4, 2),
    (5, 2);