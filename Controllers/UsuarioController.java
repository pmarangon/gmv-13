public class UsuarioController {

  private final UsuarioRepository usuarioRepository;

  public UsuarioController(UsuarioRepository usuarioRepository) {
    this.usuarioRepository = usuarioRepository;
  }

  public Usuario criaUsuario(Usuario usuario) {
    return usuarioRepository.cria(usuario);
  }

  public Usuario obtemUsuario(String id) {
    return usuarioRepository.obtem(id);
  }

  public List<Usuario> listaUsuarios() {
    return usuarioRepository.lista();
  }

  public Usuario atualizaUsuario(Usuario usuario, String id) {
    return usuarioRepository.atualiza(usuario, id);
  }

  public void deletaUsuario(String id) {
    usuarioRepository.deleta(id);
  }
}
