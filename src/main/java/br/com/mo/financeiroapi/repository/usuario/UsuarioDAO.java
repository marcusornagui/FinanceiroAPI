package br.com.mo.financeiroapi.repository.usuario;

import br.com.mo.financeiroapi.model.dto.usuario.UsuarioSalvarDto;
import br.com.mo.financeiroapi.repository.Dao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioDAO extends Dao {

    public UsuarioDAO(JdbcTemplate jdbc) {
        super(jdbc);
    }

    public int getIdLogin(int pId, String pLogin) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT id FROM usuario");
        sql.append(" WHERE login = '" + pLogin + "'");
        sql.append(" AND id != " + pId);
        sql.append(" LIMIT 1");

        return getInt(sql.toString());
    }

    public void alteraUsuario(UsuarioSalvarDto pUsuario) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE usuario SET");
        sql.append(" login = '" + pUsuario.getLogin() + "',");
        sql.append(" nome = '" + pUsuario.getNome() + "',");
        sql.append(" email = '" + pUsuario.getEmail() + "',");
        sql.append(" senha = '" + pUsuario.getSenha() + "',");
        sql.append(" id_situacaocadastro = " + pUsuario.getIdSituacaoCadastro());
        sql.append(" WHERE id = " + pUsuario.getId());

        setUpdate(sql.toString());
    }

    public int inserirUsuario(UsuarioSalvarDto pUsuario) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO usuario (login, nome, email, senha,  id_situacaocadastro, datahoraultimoacesso)");
        sql.append(" VALUES (");
        sql.append("'" + pUsuario.getLogin() + "', ");
        sql.append("'" + pUsuario.getNome() + "', ");
        sql.append("'" + pUsuario.getEmail() + "', ");
        sql.append("'" + pUsuario.getSenha() + "', ");
        sql.append(pUsuario.getIdSituacaoCadastro() + ", ");
        sql.append("NULL)");
        sql.append(" RETURNING id");

        return getInt(sql.toString());
    }

}
