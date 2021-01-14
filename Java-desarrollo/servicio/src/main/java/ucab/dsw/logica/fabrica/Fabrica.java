package ucab.dsw.logica.fabrica;

public class Fabrica<T> {

    private final Class<T> tipo;

    public Fabrica(Class<T> tipo) {

        this.tipo= tipo;
    }


    public T getInstancia() {
        try {
            return tipo.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> Fabrica<T> crear(Class<T> tipo) {
        return new Fabrica<T>(tipo);
    }
}