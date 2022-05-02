package br.com.zup.edu.loteria.api.model;

import br.com.zup.edu.loteria.api.util.Encrypter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UK_BILHETE_CELULAR_NUMERO_SORTE_SORTEIO",
        columnNames = {"hashDoCelularJogador", "hashDoNumeroDaSorte", "sorteio_id"})
})
@Entity
public class Bilhete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeJogador;

    @Column(nullable = false, length = 64)
    private String hashDoCelularJogador;

    @Column(nullable = false, length = 64)
    private String hashDoNumeroDaSorte;

    @Column(nullable = false)
    private LocalDateTime registradoEm = LocalDateTime.now();

    @ManyToOne(optional = false)
    private Sorteio sorteio;

    public Bilhete(String nomeJogador, String celularJogador, Integer numeroDaSorte, Sorteio sorteio) {
        this.nomeJogador = nomeJogador;
        this.hashDoCelularJogador = Encrypter.hash(celularJogador);
        this.hashDoNumeroDaSorte = Encrypter.hash(numeroDaSorte.toString());
        this.sorteio = sorteio;
    }

    /**
     * @deprecated Construtor para uso exclusivo do Hibernate.
     */
    @Deprecated
    public Bilhete() {
    }

    public Long getId() {
        return id;
    }
}
