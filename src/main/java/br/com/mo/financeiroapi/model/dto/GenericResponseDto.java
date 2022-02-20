package br.com.mo.financeiroapi.model.dto;

import java.util.Objects;

public class GenericResponseDto {

    private int status = 0;
    private String mensagem = "";

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GenericResponseDto other = (GenericResponseDto) obj;
        if (this.status != other.status) {
            return false;
        }
        return Objects.equals(this.mensagem, other.mensagem);
    }

}
