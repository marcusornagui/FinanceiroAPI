package br.com.mo.financeiroapi.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "versao")
public class VersaoProperties {
    private String servico;
    private Integer build;
    private Integer beta;
    private Integer major;
    private Integer minor;
    private Integer release;
    private String data;

    public VersaoProperties() {
        this.servico = "FinanceiroAPI";
    }

    public String getServico() {
        return servico;
    }

    public VersaoProperties setServico(String servico) {
        this.servico = servico;
        return this;
    }

    public Integer getBuild() {
        return build;
    }

    public VersaoProperties setBuild(Integer build) {
        this.build = build;
        return this;
    }

    public Integer getBeta() {
        return beta;
    }

    public VersaoProperties setBeta(Integer beta) {
        this.beta = beta;
        return this;
    }

    public Integer getMajor() {
        return major;
    }

    public VersaoProperties setMajor(Integer major) {
        this.major = major;
        return this;
    }

    public Integer getMinor() {
        return minor;
    }

    public VersaoProperties setMinor(Integer minor) {
        this.minor = minor;
        return this;
    }

    public Integer getRelease() {
        return release;
    }

    public VersaoProperties setRelease(Integer release) {
        this.release = release;
        return this;
    }

    public String getData() {
        return data;
    }

    public VersaoProperties setData(String data) {
        this.data = data;
        return this;
    }

}