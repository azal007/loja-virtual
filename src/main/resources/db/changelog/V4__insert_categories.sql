INSERT INTO category (id, name, active, parent_category_id) VALUES
(1, 'Hardware', 1, NULL),
(2, 'Memoria RAM', 1, 1),
(3, 'DDR 4', 1, 2),
(4, 'DDR 5', 1, 2),
(5, 'Processadores', 1, 1),
(6, 'AMD', 1, 5),
(7, 'Intel', 1, 5),
(8, 'Games', 1, NULL),
(9, 'Playstation 5', 1, 8),
(10, 'Acessorios', 1, 8);
