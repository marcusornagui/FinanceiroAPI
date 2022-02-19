package br.com.mo.financeiroapi.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class Dao {

    @Qualifier("jdbcTemplateLocal")
    protected final JdbcTemplate jdbc;
    protected static JdbcTemplate jdbcStatic;

    public Dao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        jdbcStatic = jdbc;
    }

    public boolean getBoolean(String i_sql) {
        try {
            return !jdbc.queryForList(i_sql).isEmpty();

        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public double getDouble(String i_sql) {
        try {
            return jdbc.queryForObject(i_sql, Double.class);
        } catch (EmptyResultDataAccessException e) {
            return -1;
        }
    }

    public int getInt(String i_sql) {
        try {
            return jdbc.queryForObject(i_sql, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return -1;
        }
    }

    public List<Integer> getListInt(String i_sql) {
        try {
            return jdbc.queryForList(i_sql, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public List<String> getListString(String i_sql) {
        try {
            return jdbc.queryForList(i_sql, String.class);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public List<Long> getListLong(String i_sql) {
        try {
            return jdbc.queryForList(i_sql, Long.class);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public long getLong(String i_sql) {
        try {
            return jdbc.queryForObject(i_sql, Long.class);
        } catch (EmptyResultDataAccessException e) {
            return -1;
        }
    }

    public String getString(String i_sql) {
        try {
            return jdbc.queryForObject(i_sql, String.class);
        } catch (EmptyResultDataAccessException e) {
            return "";
        }
    }

    public Date getDate(String i_sql) {
        try {
            return jdbc.queryForObject(i_sql, Date.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void setUpdate(String i_sql) {
        jdbc.update(i_sql);
    }
}
