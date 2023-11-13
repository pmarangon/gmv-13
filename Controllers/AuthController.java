
public class AuthController {

  private final UsuarioRepository usuarioRepository;

  public AuthController(UsuarioRepository usuarioRepository) {
    this.usuarioRepository = usuarioRepository;
  }

  public boolean autoriza(String email, String senha) {
    return usuarioRepository.autoriza(email, senha);
  }
}
