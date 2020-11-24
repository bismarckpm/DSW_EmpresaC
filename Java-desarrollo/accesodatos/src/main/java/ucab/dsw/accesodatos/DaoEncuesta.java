package ucab.dsw.accesodatos;

import ucab.dsw.entidades.Encuesta;

import javax.persistence.EntityManager;
public class DaoEncuesta extends Dao<Encuesta>{


    private EntityManager _em;
    static DaoHandler _handler = new DaoHandler();


    public DaoEncuesta( )
    {
        super( _handler );
    }
}
