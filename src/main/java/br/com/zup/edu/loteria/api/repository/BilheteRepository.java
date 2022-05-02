package br.com.zup.edu.loteria.api.repository;

import br.com.zup.edu.loteria.api.model.Bilhete;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BilheteRepository extends JpaRepository<Bilhete,Long> {
    boolean existsByHashDoCelularJogadorAndHashDoNumeroDaSorteAndSorteioId(String hashCelular,
                                                                           String hashNumeroSorte,
                                                                           Long id);
}
