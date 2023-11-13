
public class ArtefatoController {

  private final ArtefatoRepository artefatoRepository;

  public ArtefatoController(ArtefatoRepository artefatoRepository) {
    this.artefatoRepository = artefatoRepository;
  }

  public Artefato criaArtefato(Artefato entidade) {
    return artefatoRepository.cria(entidade);
  }

  public Artefato obtemArtefato(String id) {
    return artefatoRepository.obtem(id);
  }

  public List<Artefato> listaArtefatos() {
    return artefatoRepository.lista();
  }

  public Artefato atualizaArtefato(Artefato entidade, String id) {
    return artefatoRepository.atualiza(entidade, id);
  }

  public void deletaArtefato(String id) {
    artefatoRepository.deleta(id);
  }
}
