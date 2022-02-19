package br.com.mo.financeiroapi.model.dto;

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

}
