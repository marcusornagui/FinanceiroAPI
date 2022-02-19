package br.com.mo.financeiroapi.controller;

import br.com.mo.financeiroapi.properties.VersaoProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class InfoController {

    @Autowired
    VersaoProperties versao;
    
    
    @RequestMapping(value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public VersaoProperties info() {
        return versao;
    }
}
