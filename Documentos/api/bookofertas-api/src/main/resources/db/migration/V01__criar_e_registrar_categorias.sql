delete from oferta.categoria;
insert into oferta.categoria (color, foto, nome, seguimento_id) values	('#000000',  'default.png',	'Cerverjas', 1);
insert into oferta.categoria (color, foto, nome, seguimento_id)	values	('#000001',  'default.png',	'Refrigerantes', 1);
insert into oferta.categoria (color, foto, nome, seguimento_id)	values	('#000003',	'default.png',	'Sucos, Refrescos, Chás', 1);
insert into oferta.categoria (color, foto, nome, seguimento_id)	values	('#000004',	'default.png',	'Espumantes', 1);
insert into oferta.categoria (color, foto, nome, seguimento_id)	values	('#000005',	'default.png',	'Vinhos', 1);
insert into oferta.categoria (color, foto, nome, seguimento_id)	values	('#000006',	'default.png',	'Destilados', 1);
insert into oferta.categoria (color, foto, nome, seguimento_id)	values	('#000007',	'default.png',	'Água, Energético e Isotônicos, Leites', 1);


insert into oferta.categoria (color, foto, nome, seguimento_id) values	('#000000',  'default.png',	'Alimentos Básicos', 2);
insert into oferta.categoria (color, foto, nome, seguimento_id)	values	('#000001',  'default.png',	'Massas', 2);
insert into oferta.categoria (color, foto, nome, seguimento_id)	values	('#000003',	'default.png',	'Chocolates, Balas e Guloseimas', 2);
insert into oferta.categoria (color, foto, nome, seguimento_id)	values	('#000004',	'default.png',	'Azeite, Óleo e Vinagre', 2);
insert into oferta.categoria (color, foto, nome, seguimento_id)	values	('#000005',	'default.png',	'Molhos e Sopas', 2);
insert into oferta.categoria (color, foto, nome, seguimento_id)	values	('#000006',	'default.png',	'Confeitaria, Doces e Salgados', 2);
insert into oferta.categoria (color, foto, nome, seguimento_id)	values	('#000007',	'default.png',	'Conservas e Enlatados', 2);

insert into oferta.subcategoria (nome, categoria_id) values	('Conservas Tradicionais', 2);
insert into oferta.subcategoria (nome, categoria_id) values	('Conservas Especiais', 2);
insert into oferta.subcategoria (nome, categoria_id) values	('Conservas sem Alcool', 2);
insert into oferta.subcategoria (nome, categoria_id) values	('Kits de Cervejas', 2);
