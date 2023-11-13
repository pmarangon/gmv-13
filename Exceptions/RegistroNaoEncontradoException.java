public class RegistroNaoEncontradoException extends RuntimeException {

    private final Long id;

    public RegistroNaoEncontradoException(Long id) {
        super("Não foi encontrado registro para o id: " + id);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
