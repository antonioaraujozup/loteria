package br.com.zup.edu.loteria.api.controller;

import br.com.zup.edu.loteria.api.model.Sorteio;
import br.com.zup.edu.loteria.api.repository.SorteioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
public class CadastrarNovoSorteioController {

    private final SorteioRepository repository;

    public CadastrarNovoSorteioController(SorteioRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/sorteios")
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody @Valid NovoSorteioRequest request, UriComponentsBuilder uriComponentsBuilder) {
        Sorteio sorteio = request.toModel();

        repository.save(sorteio);

        URI location = uriComponentsBuilder.path("/sorteios/{id}")
                .buildAndExpand(sorteio.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
