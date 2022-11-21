package br.com.mo.financeiroapi.model.vo;

import java.util.ArrayList;
import java.util.List;

public class CotacaoMoedaGenericResponseVO {

    List<CotacaoMoedaResponseVO> vCotacaoMoeda = new ArrayList();

    public List<CotacaoMoedaResponseVO> getvCotacaoMoeda() {
        return vCotacaoMoeda;
    }

    public void setvCotacaoMoeda(List<CotacaoMoedaResponseVO> vCotacaoMoeda) {
        this.vCotacaoMoeda = vCotacaoMoeda;
    }

}
