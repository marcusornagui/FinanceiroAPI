package br.com.mo.financeiroapi.controller;

import br.com.mo.financeiroapi.mensageria.AtividadesSender;
import br.com.mo.financeiroapi.properties.VersaoProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class InfoController {

    private final AtividadesSender atividadesSender;
    private final VersaoProperties versao;

    @Autowired
    public InfoController(AtividadesSender atividadesSender, VersaoProperties versao) {
        this.atividadesSender = atividadesSender;
        this.versao = versao;
    }

    @RequestMapping(value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public VersaoProperties info() {
        atividadesSender.sendInfo(InfoController.class, "Versão do sistema consultada.");

        return versao;
    }
}
