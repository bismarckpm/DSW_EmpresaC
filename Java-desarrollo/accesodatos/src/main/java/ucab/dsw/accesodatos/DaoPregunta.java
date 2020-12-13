package ucab.dsw.accesodatos;

import ucab.dsw.entidades.Pregunta;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class DaoPregunta extends Dao<Pregunta> {


    private EntityManager _em;
    static DaoHandler _handler = new DaoHandler();


    public DaoPregunta( )
    {
        super( _handler );
    }

}
