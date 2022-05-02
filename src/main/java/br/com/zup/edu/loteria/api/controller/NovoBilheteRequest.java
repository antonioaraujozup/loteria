package br.com.zup.edu.loteria.api.controller;

import br.com.zup.edu.loteria.api.model.Bilhete;
import br.com.zup.edu.loteria.api.model.Sorteio;

import javax.validation.constraints.*;

public class NovoBilheteRequest {

    @NotBlank
    private String nomeJogador;

    @NotBlank
    @Pattern(regexp = "^\\+[1-9][0-9]\\d{1,14}")
    private String celularJogador;

    @NotNull
    @Min(1)
    @Max(9999)
    private Integer numeroDaSorte;

    public NovoBilheteRequest(String nomeJogador, String celularJogador, Integer numeroDaSorte) {
        this.nomeJogador = nomeJogador;
        this.celularJogador = celularJogador;
        this.numeroDaSorte = numeroDaSorte;
    }

    public NovoBilheteRequest() {
    }

    public Bilhete toModel(Sorteio sorteio) {
        return new Bilhete(nomeJogador, celularJogador, numeroDaSorte, sorteio);
    }

    public String getNomeJogador() {
        return nomeJogador;
    }

    public String getCelularJogador() {
        return celularJogador;
    }

    public Integer getNumeroDaSorte() {
        return numeroDaSorte;
    }
}
