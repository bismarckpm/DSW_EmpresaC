package ucab.dsw.accesodatos;

import ucab.dsw.entidades.Ciudad;

import javax.persistence.EntityManager;

public class DaoCiudad extends Dao<Ciudad>
{
    private EntityManager _em;
    static DaoHandler _handler = new DaoHandler();


    public DaoCiudad( )
    {
        super( _handler );
    }
}