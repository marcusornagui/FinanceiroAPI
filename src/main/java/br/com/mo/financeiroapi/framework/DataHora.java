package br.com.mo.financeiroapi.framework;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class DataHora {

    protected static JdbcTemplate jdbc;

    @Autowired
    public DataHora(JdbcTemplate jdbc) {
        DataHora.jdbc = jdbc;
    }

    public static boolean isDataValida(String i_data) throws Exception {
        try {
            String data = new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("dd/MM/yyyy").parse(i_data));

            return data.equals(i_data);

        } catch (Exception ex) {
            return false;
        }
    }

    public static String getUltimoDiaMes(String i_mesAno) throws Exception {
        return dateAdd(dateAdd("01/" + i_mesAno, "m", 1), "d", -1);
    }

    public static String getUltimoDiaAno(String i_ano) throws Exception {
        return getUltimoDiaMes("12/" + i_ano);
    }

    public static boolean isMesAnoValido(String i_data) throws Exception {
        try {
            String data = new SimpleDateFormat("MM/yyyy").format(new SimpleDateFormat("MM/yyyy").parse(i_data));

            return data.equals(i_data);

        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean isIntervaloData(String i_dataInicio, String i_dataTermino) throws ParseException {
        return (new SimpleDateFormat("dd/MM/yyyy").parse(i_dataInicio).getTime()) <= (new SimpleDateFormat("dd/MM/yyyy").parse(i_dataTermino).getTime());
    }

    public static boolean isIntervaloHora(String i_horaInicio, String i_horaTermino) throws ParseException {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

            return formatter.parse(i_horaInicio).getTime() <= formatter.parse(i_horaTermino).getTime();

        } catch (Exception ex) {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

            return formatter.parse(i_horaInicio).getTime() <= formatter.parse(i_horaTermino).getTime();
        }
    }

    public static void validarIntervaloDataHora(String i_txtInicio, String i_txtTermino,
            String i_horaInicio, String i_horaTermino) throws Exception {
        if ((i_txtInicio.equals(i_txtTermino)
                && (!isIntervaloHora(i_horaInicio, i_horaTermino)
                || i_horaInicio.equals(i_horaTermino)))
                || !isIntervaloData(i_txtInicio, i_txtTermino)) {
            throw new Exception("Período inválido!");
        }

    }

    public static long getTime(String i_data) throws Exception {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

            return formatter.parse(i_data).getTime();

        } catch (Exception ex) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

                return formatter.parse(i_data).getTime();

            } catch (Exception ex2) {
                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

                    return formatter.parse(i_data).getTime();

                } catch (Exception ex3) {
                    try {
                        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

                        return formatter.parse(i_data).getTime();

                    } catch (Exception ex4) {
                        try {
                            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                            formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

                            return formatter.parse(i_data).getTime();
                        } catch (Exception ex5) {
                            SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
                            formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

                            return formatter.parse(i_data).getTime();
                        }
                    }
                }
            }
        }
    }

    /**
     * <b>Retorna a diferenca entre duas datas</b>
     *
     * @param i_data1 Data inicial do período a ser verificado
     * @param i_data2 Data final do período a ser verificado
     * @param i_tipo Tipo da diferença a ser calculada:      <pre>
     *                       <b>s</b>  - Segundos
     *                       <b>mn</b> - Minutos
     *                       <b>d</b>  - Dias
     *                       <b>m</b>  - Meses
     *                       <b>y</b>  - Anos
     * </pre>
     *
     * @return valor de acordo com o parâmetro i_tipo
     * @throws Exception
     */
    public static long dateDiff(String i_data1, String i_data2, String i_tipo) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));

        long time1 = formatter.parse(i_data1).getTime();
        long time2 = formatter.parse(i_data2).getTime();

        if (i_tipo.equalsIgnoreCase("d")) {
            return (time2 - time1) / (1000 * 60 * 60 * 24);

        } else if (i_tipo.equalsIgnoreCase("m")) {
            LocalDate start = LocalDate.parse(Format.dataBanco(i_data1));
            LocalDate end = LocalDate.parse(Format.dataBanco(i_data2));
            Period period = Period.between(start, end);
            return period.getMonths();

        } else if (i_tipo.equalsIgnoreCase("y")) {
            return (time2 - time1) / (1000 * 60 * 60 * 24 * 365);

        } else if (i_tipo.equalsIgnoreCase("mn")) {
            LocalDateTime start = LocalDateTime.parse(i_data1, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            LocalDateTime end = LocalDateTime.parse(i_data2, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            Duration duration = Duration.between(start, end);

            return duration.toMinutes();
        } else if (i_tipo.equalsIgnoreCase("s")) {
            LocalDateTime start = LocalDateTime.parse(i_data1, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            LocalDateTime end = LocalDateTime.parse(i_data2, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            Duration duration = Duration.between(start, end);

            return duration.toSeconds();
        } else {
            throw new Exception("Tipo inválido!");
        }
    }

    public static String dateAdd(String i_data, String i_tipo, int i_qtde) throws Exception {
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat formatter;

        try {
            formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));
            gc.setTime(formatter.parse(i_data));

        } catch (Exception ex) {
            try {
                formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                formatter.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));
                gc.setTime(formatter.parse(i_data));

            } catch (Exception ex2) {
                try {
                    formatter = new SimpleDateFormat("dd/MM/yyyy");
                    gc.setTime(formatter.parse(i_data));

                } catch (Exception ex3) {
                    try {
                        formatter = new SimpleDateFormat("HH:mm:ss");
                        formatter.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));
                        gc.setTime(formatter.parse(i_data));

                    } catch (Exception ex4) {
                        try {
                            formatter = new SimpleDateFormat("HH:mm");
                            formatter.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));
                            gc.setTime(formatter.parse(i_data));

                        } catch (Exception ex5) {
                            formatter = new SimpleDateFormat("MM/yyyy");
                            gc.setTime(formatter.parse(i_data));
                        }
                    }
                }
            }
        }

        if (i_tipo.equalsIgnoreCase("d")) {
            gc.add(Calendar.DAY_OF_MONTH, i_qtde);

        } else if (i_tipo.equalsIgnoreCase("m")) {
            gc.add(Calendar.MONTH, i_qtde);

        } else if (i_tipo.equalsIgnoreCase("y")) {
            gc.add(Calendar.YEAR, i_qtde);

        } else if (i_tipo.equalsIgnoreCase("n")) {
            gc.add(Calendar.MINUTE, i_qtde);
        }

        return formatter.format(gc.getTime());
    }

    public static long getDiff(Date i_data1, Date i_data2, String i_tipo) throws Exception {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        int meses = 0;
        int anos = 0;

        c1.setTime(i_data1);
        c2.setTime(i_data2);

        meses = (-c1.get(Calendar.MONTH) + c2.get(Calendar.MONTH));
        anos = (-c1.get(Calendar.YEAR) + c2.get(Calendar.YEAR));

        meses = meses + (anos * 12);

        if (i_tipo.equals("d")) {
            return c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
        } else if (i_tipo.equals("m")) {
            return meses;
        } else if (i_tipo.equals("s")) {
            return meses % 6 > 0 ? meses / 6 + 1 : meses / 6;
        } else {
            return meses % 12 > 0 ? meses / 12 + 1 : meses / 12;
        }
    }

    public static long getDiff(String i_data1, String i_data2, String i_tipo) throws Exception {
        Date data1 = new SimpleDateFormat("dd/MM/yyyy").parse(i_data1);
        Date data2 = new SimpleDateFormat("dd/MM/yyyy").parse(i_data2);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        int meses = 0;
        int anos = 0;

        c1.setTime(data1);
        c2.setTime(data2);

        meses = (-c1.get(Calendar.MONTH) + c2.get(Calendar.MONTH));
        anos = (-c1.get(Calendar.YEAR) + c2.get(Calendar.YEAR));

        meses = meses + (anos * 12);

        if (i_tipo.equals("d")) {
            return c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
        } else if (i_tipo.equals("m")) {
            return meses;
        } else if (i_tipo.equals("s")) {
            return meses % 6 > 0 ? meses / 6 + 1 : meses / 6;
        } else {
            return meses % 12 > 0 ? meses / 12 + 1 : meses / 12;
        }
    }

    public static String getDataAtual() throws Exception {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").format(jdbc.queryForObject("SELECT NOW() AS datahora", Date.class));

        } catch (Exception ex) {
            return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        }
    }

    public static String getDataHoraAtual() throws Exception {
        try {
            return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(jdbc.queryForObject("SELECT NOW() AS datahora", Date.class));

        } catch (Exception ex) {
            return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        }
    }

    public static String getDataHoraFormatada() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    public static LocalDate getLocalDate(String data) {
        if (data == null) {
            return null;
        }

        if (data.isEmpty()) {
            return null;
        }

        if (Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$").matcher(data).matches()) {
            return LocalDate.parse(data);
        }

        if (Pattern.compile("^\\d{2}\\/\\d{2}\\/\\d{4}$").matcher(data).matches()) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(data, dtf);
        }

        throw new IllegalArgumentException("Data em formato inválido: " + data);

    }

    public static LocalDateTime getLocalDateTime(String dataHora) {
        if (dataHora == null) {
            return null;
        }

        if (dataHora.isEmpty()) {
            return null;
        }

        if (Pattern.compile("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}").matcher(dataHora).matches()) {
            return LocalDateTime.parse(dataHora);
        }

        if (Pattern.compile("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}").matcher(dataHora).matches()) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return LocalDateTime.parse(dataHora, dtf);
        }

        if (Pattern.compile("^\\d{2}\\/\\d{2}\\/\\d{4} \\d{2}:\\d{2}:\\d{2}").matcher(dataHora).matches()) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            return LocalDateTime.parse(dataHora, dtf);
        }

        throw new IllegalArgumentException("Data em formato inválido: " + dataHora);
    }

    public static String getHoraAtual() throws Exception {
        try {
            return new SimpleDateFormat("HH:mm:ss").format(jdbc.queryForObject("SELECT NOW() AS datahora", Date.class));

        } catch (Exception ex) {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));

            return formatter.format(new Date());
        }
    }
    
    public static LocalDate toLocalDate(String i_data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(i_data, formatter);
    }
    
    public static LocalDateTime toLocalDateTime(String i_data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return LocalDateTime.parse(i_data, formatter);
    }
    
    public static String toStringDate(LocalDate i_data, String i_formato) {
        DateTimeFormatter formatter = null;
        
        if (i_formato.equalsIgnoreCase("d")) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            
        } else if (i_formato.equalsIgnoreCase("g")) {
            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        }
        
        return i_data.format(formatter);
    }
    
    public static String toStringDateTime(LocalDateTime i_data, String i_formato) {
        DateTimeFormatter formatter = null;
        
        if (i_formato.equalsIgnoreCase("d")) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            
        } else if (i_formato.equalsIgnoreCase("g")) {
            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        }
        
        return i_data.format(formatter);
    }
    
    public static boolean mesAnoIgual(String i_data1, String i_data2) throws ParseException {
        LocalDate data1 = toLocalDate(i_data1);
        LocalDate data2 = toLocalDate(i_data2);
        data1.withDayOfMonth(1);
        data2.withDayOfMonth(1);

        return data1.isEqual(data2);
    }
    
    public static int getQuantidadeDiaMes(String i_mesAno) throws Exception {
        String ultimoDia = getUltimoDiaMes(i_mesAno);
        return Integer.parseInt(ultimoDia.substring(0, 2));
    }

    public static long getQuantidadeDiaAno(String i_ano) throws Exception {
        if (Integer.parseInt(i_ano) % 400 == 0) {
            return 366;
        }

        return 365;
    }
}
