package br.com.mo.financeiroapi.framework;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Numero {

    public static double round(double i_valor, int i_qtd) {
        if (Double.isNaN(i_valor) || Double.isInfinite(i_valor)) {
            i_valor = 0;
        }

        BigDecimal valorExato = new BigDecimal(String.valueOf(i_valor)).setScale(i_qtd, RoundingMode.HALF_UP);
        return valorExato.doubleValue();
    }
}
