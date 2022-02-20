package br.com.mo.financeiroapi.model.enums;

public enum StatusEnvio {
    
    FALHA(0, "FALHA"),
    SUCESSO(1, "SUCESSO");

    private int id = 0;
    private String descricao = "";

    private StatusEnvio(int i_id, String i_descricao) {
        this.id = i_id;
        this.descricao = i_descricao;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusEnvio getById(int id) {
        for (StatusEnvio value : values()) {
            if (value.id == id) {
                return value;
            }
        }

        return null;
    }
}
