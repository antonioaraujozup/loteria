package br.com.zup.edu.loteria.api.controller;

import br.com.zup.edu.loteria.api.model.Bilhete;
import br.com.zup.edu.loteria.api.model.Sorteio;
import br.com.zup.edu.loteria.api.repository.BilheteRepository;
import br.com.zup.edu.loteria.api.repository.SorteioRepository;
import br.com.zup.edu.loteria.api.util.Encrypter;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class CadastrarNovoBilheteController {

    private final SorteioRepository sorteioRepository;
    private final BilheteRepository bilheteRepository;

    public CadastrarNovoBilheteController(SorteioRepository sorteioRepository, BilheteRepository bilheteRepository) {
        this.sorteioRepository = sorteioRepository;
        this.bilheteRepository = bilheteRepository;
    }

    @PostMapping("/sorteios/{sorteioId}/bilhetes")
    @Transactional
    public ResponseEntity<?> cadastrar(@PathVariable Long sorteioId, @RequestBody @Valid NovoBilheteRequest request,
                                       UriComponentsBuilder uriComponentsBuilder) {

        Sorteio sorteio = sorteioRepository.findById(sorteioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorteio não encontrado"));

        String hashCelular = Encrypter.hash(request.getCelularJogador());
        String hashNumeroSorte = Encrypter.hash(request.getNumeroDaSorte().toString());
        if (bilheteRepository.existsByHashDoCelularJogadorAndHashDoNumeroDaSorteAndSorteioId(hashCelular, hashNumeroSorte, sorteio.getId())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Bilhete já cadastrado");
        }

        Bilhete bilhete = request.toModel(sorteio);

        bilheteRepository.save(bilhete);

        URI location = uriComponentsBuilder.path("/sorteios/{sorteioId}/bilhetes/{bilheteId}")
                .buildAndExpand(sorteioId, bilhete.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        Map<String, Object> body = Map.of(
                "status", 422,
                "error", "Unprocessable Entity",
                "path", request.getDescription(false).replace("uri=", ""),
                "timestamp", LocalDateTime.now(),
                "message", "Bilhete já cadastrado"
        );

        return ResponseEntity.unprocessableEntity().body(body);
    }
}
