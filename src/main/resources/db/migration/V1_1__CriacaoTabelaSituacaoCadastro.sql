CREATE TABLE public.situacaocadastro (
	id int4 NOT NULL,
	descricao varchar(8) NOT NULL,
	CONSTRAINT pk_situacaocadastro PRIMARY KEY (id)
);

INSERT INTO public.situacaocadastro (id, descricao) VALUES(0, 'EXCLUIDO');
INSERT INTO public.situacaocadastro (id, descricao) VALUES(1, 'ATIVO');