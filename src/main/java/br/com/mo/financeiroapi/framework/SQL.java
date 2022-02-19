package br.com.mo.financeiroapi.framework;

public class SQL {

    public static String getLike(String i_campo, String i_texto) throws Exception {
        String[] aPalavra = i_texto.split(" ");
        String retorno = "";

        for (int i = 0; i < aPalavra.length; i++) {
            if (!aPalavra[i].equals("")) {
                if (!retorno.equals("")) {
                    retorno += " AND ";
                }

                if (aPalavra[i].startsWith("=")) {
                    retorno += "(" + i_campo + " ILIKE '" + aPalavra[i].substring(1, aPalavra[i].length()) + "%')";
                } else {
                    retorno += "(" + i_campo + " ILIKE '" + aPalavra[i] + "%' OR " + i_campo + " ILIKE '%" + aPalavra[i] + "%')";
                }
            }
        }

        return "(" + retorno + ")";
    }

}
