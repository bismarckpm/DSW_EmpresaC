package ucab.dsw.accesodatos;

import ucab.dsw.entidades.Encuestado;

import javax.persistence.EntityManager;

public class DaoEncuestado extends Dao<Encuestado>
{
    private EntityManager _em;
    static DaoHandler _handler = new DaoHandler();


    public DaoEncuestado( )
    {
        super( _handler );
    }
}