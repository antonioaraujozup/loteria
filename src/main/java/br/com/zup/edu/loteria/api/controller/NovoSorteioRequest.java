package br.com.zup.edu.loteria.api.controller;

import br.com.zup.edu.loteria.api.model.Sorteio;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class NovoSorteioRequest {

    @NotBlank
    @Size(max = 150)
    private String descricaoPremio;

    @NotNull
    @Future
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataSorteio;

    public NovoSorteioRequest(String descricaoPremio, LocalDate dataSorteio) {
        this.descricaoPremio = descricaoPremio;
        this.dataSorteio = dataSorteio;
    }

    public NovoSorteioRequest() {
    }

    public Sorteio toModel(){
        return new Sorteio(descricaoPremio,dataSorteio);
    }

    public String getDescricaoPremio() {
        return descricaoPremio;
    }

    public LocalDate getDataSorteio() {
        return dataSorteio;
    }
}
