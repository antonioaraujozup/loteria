package br.com.zup.edu.loteria.api.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Sorteio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String descricaoPremio;

    @Column(nullable = false)
    private LocalDate dataSorteio;

    public Sorteio(String descricaoPremio, LocalDate dataSorteio) {
        this.descricaoPremio = descricaoPremio;
        this.dataSorteio = dataSorteio;
    }

    /**
     * @deprecated Construtor para uso exclusivo do Hibernate.
     */
    @Deprecated
    public Sorteio() {
    }

    public Long getId() {
        return id;
    }
}
