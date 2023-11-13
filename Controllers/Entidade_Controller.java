package com.guiame.controllers;

import com.guiame.models.Entidade;
import com.guiame.repositories.EntidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/entidades")
public class EntidadeController {

    @Autowired
    private EntidadeRepository entidadeRepository;

    @PostMapping
    public Entidade criaEntidade(@RequestBody Entidade entidade) {
        return entidadeRepository.save(entidade);
    }

    @GetMapping("/{id}")
    public Entidade obtemEntidade(@PathVariable Long id) {
        return entidadeRepository.findById(id).orElse(null);
    }

    @GetMapping
    public List<Entidade> listaEntidades() {
        return entidadeRepository.findAll();
    }

    @PutMapping("/{id}")
    public Entidade atualizaEntidade(@PathVariable Long id, @RequestBody Entidade entidade) {
        entidade.setId(id);
        return entidadeRepository.save(entidade);
    }

    @DeleteMapping("/{id}")
    public void deletaEntidade(@PathVariable Long id) {
        entidadeRepository.deleteById(id);
    }
}
