package br.com.mo.financeiroapi.model.dto.mensageria;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PayloadSenderDTO {

    private String mensagem;
    private String dataHora;

    public PayloadSenderDTO() {
    }

    public PayloadSenderDTO(String mensagem, String dataHora) {
        this.mensagem = mensagem;
        this.dataHora = dataHora;
    }

    public PayloadSenderDTO dados(String mensagem) {
        this.mensagem = mensagem;
        return this;
    }

    public PayloadSenderDTO dataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        return this;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

}
