
--INSERT INTO oferta.pessoa (nome, telefone, tipo_pessoa, ativo, usuario_id, data_registro) 
--values ('Supermercado Goiana', '(95)99154-8667', 'pessoaJuridica', true, 2, '02-04-2020');

--INSERT INTO oferta.pessoa (nome, telefone, tipo_pessoa, ativo, usuario_id) 
--values ('Fabio Resplandes', '(95)99154-8667', 'pessoaFisica', true, 2);


--INSERT INTO oferta.pessoa_endereco (pessoa_id, endereco_id) values (1,1);
--INSERT INTO oferta.pessoa_endereco (pessoa_id, endereco_id) values (2,2);

INSERT INTO oferta.loja (cnpj, razao_social, loja_id)
values ('00.000.000/0000-00', 'loja tal', 3);

--INSERT INTO oferta.pessoafisica (cpf, sexo, pessoafisica_id)
--values ('000.000.000-00', 'MASCULINO', 2);