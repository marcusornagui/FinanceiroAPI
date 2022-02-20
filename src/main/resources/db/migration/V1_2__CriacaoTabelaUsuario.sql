CREATE TABLE IF NOT EXISTS public.usuario (
	id SERIAL NOT NULL, 
	login VARCHAR(12) NOT NULL,
	nome VARCHAR(30) NOT NULL,
	email VARCHAR(50) NOT NULL,
	senha VARCHAR(32) NOT NULL,
	id_situacaocadastro int4 NOT NULL,
	datahoraultimoacesso timestamp NULL,
        CONSTRAINT fk_id_situacaocadastro FOREIGN KEY (id_situacaocadastro) REFERENCES public.situacaocadastro(id),
	CONSTRAINT pk_usuario PRIMARY KEY (id)
);
