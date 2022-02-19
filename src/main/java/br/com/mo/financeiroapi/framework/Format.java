package br.com.mo.financeiroapi.framework;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Format {

    public static String CPF(long i_cpf) {
        String cpf = new DecimalFormat("00000000000").format(i_cpf);

        return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
    }

    public static String CNPJ(long i_cpf) {
        String cpf = new DecimalFormat("00000000000000").format(i_cpf);

        return cpf.substring(0, 2) + "." + cpf.substring(2, 5) + "." + cpf.substring(5, 8) + "/" + cpf.substring(8, 12) + "-" + cpf.substring(12, 14);
    }

    public static String decimal2(double i_valor) {
        return new DecimalFormat("###,##0.00").format(Numero.round(Numero.round(i_valor, 3), 2));
    }

    public static String decimal3(double i_valor) {
        return new DecimalFormat("###,##0.000").format(Numero.round(i_valor, 3));
    }

    public static String data(Date i_dataHora, String i_formato) {
        return new SimpleDateFormat(i_formato).format(i_dataHora);
    }

    public static String data(String i_dataHora, String i_formatoEntrada, String i_formatoSaida) throws ParseException {
        return new SimpleDateFormat(i_formatoSaida).format(new SimpleDateFormat(i_formatoEntrada).parse(i_dataHora));
    }

    public static String dataBanco(String i_data) throws Exception {
        return new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(i_data));
    }

    public static String dataGUI(String i_data) throws Exception {
        return dataGUI(new SimpleDateFormat("yyyy-MM-dd").parse(i_data));
    }

    public static String dataGUI(Date i_data) throws Exception {
        return new SimpleDateFormat("dd/MM/yyyy").format(i_data);
    }

    public static String dataHoraGUI(String i_dataHora) throws Exception {
        return dataHoraGUI(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(i_dataHora));
    }

    public static String dataHoraGUI(Date i_dataHora) {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(i_dataHora);
    }

    public static String dataHoraBanco(String i_dataHora) throws Exception {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(i_dataHora));

        } catch (Exception ex) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(i_dataHora));
        }
    }

    public static String diaSemana(String i_data) throws Exception {
        return diaSemana(new SimpleDateFormat("dd/MM/yyyy").parse(i_data));
    }

    public static String diaSemana(Date i_data) throws Exception {
        return new SimpleDateFormat("EEEE").format(i_data).replace("á", "a").replace("ç", "c").toUpperCase();
    }

    public static String horaGUI(Date i_hora) throws Exception {
        return new SimpleDateFormat("HH:mm:ss").format(i_hora);
    }

    public static String mesAno(String i_data) throws Exception {
        return i_data.substring(3);
    }

    public static String number(String i_valor, int i_tamanho) {
        String zero = "";

        for (int i = 0; i < (i_tamanho - i_valor.length()); i++) {
            zero += "0";
        }

        return zero + i_valor;
    }

    public static String number(long i_valor, int i_tamanho) {
        return number(String.valueOf(i_valor), i_tamanho);
    }

    public static String string(String i_textoEsquerda, String i_textoDireita, int i_tamanho) throws Exception {
        int tamanho1 = i_tamanho - i_textoDireita.length() - 1;
        return string(i_textoEsquerda, tamanho1) + " " + i_textoDireita;
    }

    public static String string(String i_texto, int i_tamanho) {
        return string(i_texto, i_tamanho, "e");
    }

    /**
     *
     * @param i_texto texto a ser centralizado
     * @param i_tamanho quantidade de colunas
     * @param i_alinhamento e esquerda, d direita, c centralizado
     * @return String alinhada
     * @throws Exception
     */
    public static String string(String i_texto, int i_tamanho, String i_alinhamento) {
        String texto = "";
        String espaco = "";

        if (i_texto.length() > i_tamanho) {
            texto = i_texto.substring(0, i_tamanho);
        } else {
            texto = i_texto;
        }

        if (i_alinhamento.equalsIgnoreCase("e")) {
            for (int i = 0; i < (i_tamanho - texto.length()); i++) {
                espaco += " ";
            }
            return texto + espaco;

        } else if (i_alinhamento.equalsIgnoreCase("d")) {
            for (int i = 0; i < (i_tamanho - texto.length()); i++) {
                espaco += " ";
            }
            return espaco + texto;

        } else if (i_alinhamento.equalsIgnoreCase("c")) {
            for (int i = 0; i < ((i_tamanho - texto.length()) / 2); i++) {
                espaco += " ";
            }

            String retorno = espaco + texto + espaco;

            if (retorno.length() < i_tamanho) {
                retorno += string(" ", i_tamanho - retorno.length());
            }

            return retorno;

        } else {
            return i_texto;
        }

    }
}