package com.guiame.controllers;

import com.guiame.models.Evento;
import com.guiame.models.EventoSchema;
import com.guiame.repositories.EventoRepository;
import com.guiame.websocket.WebsocketConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

  @Autowired
  private EventoRepository eventoRepository;

  @Autowired
  private WebsocketConnector websocketConnector;

  @PostMapping
  public Evento criaEvento(@RequestBody Evento evento) {
    evento.setId(null);
    eventoRepository.save(evento);

    if (evento.getArtefato().getAtivo()) {
      websocketConnector.enviaMensagemParaTodos(EventoSchema.toJson(evento));
    }

    return evento;
  }

  @GetMapping("/{id}")
  public Evento obtemEvento(@PathVariable Long id) {
    return eventoRepository.findById(id).orElse(null);
  }

  @GetMapping
  public List<Evento> listaEventos() {
    return eventoRepository.findAll();
  }

  @PutMapping("/{id}")
  public Evento atualizaEvento(@PathVariable Long id, @RequestBody Evento evento) {
    evento.setId(id);
    eventoRepository.save(evento);

    if (evento.getArtefato().getAtivo()) {
      websocketConnector.enviaMensagemParaTodos(EventoSchema.toJson(evento));
    }

    return evento;
  }

  @DeleteMapping("/{id}")
  public void deletaEvento(@PathVariable Long id) {
    eventoRepository.deleteById(id);
  }
}
