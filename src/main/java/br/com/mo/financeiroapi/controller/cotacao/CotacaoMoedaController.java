package br.com.mo.financeiroapi.controller.cotacao;

import br.com.mo.financeiroapi.model.vo.CotacaoMoedaGenericResponseVO;
import br.com.mo.financeiroapi.service.api.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cotacaomoeda")
public class CotacaoMoedaController {

    @Autowired
    ApiService apiService;

    @GetMapping()
    @RequestMapping("/cotacao")
    public ResponseEntity cotacaoMoeda() throws Exception {
        try {
            CotacaoMoedaGenericResponseVO oGenericResponse = apiService.getCotacaoMoeda(new Object(), "");

            return new ResponseEntity(oGenericResponse, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
