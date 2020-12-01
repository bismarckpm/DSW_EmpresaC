package ucab.dsw.accesodatos;

import ucab.dsw.entidades.EncuestaTipo;

import javax.persistence.EntityManager;
public class DaoEncuestaTipo extends Dao<EncuestaTipo>{


    private EntityManager _em;
    static DaoHandler _handler = new DaoHandler();


    public DaoEncuestaTipo( )
    {
        super( _handler );
    }
}
