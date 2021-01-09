package ucab.dsw.accesodatos;

import ucab.dsw.entidades.MetodoConexion;

import javax.persistence.EntityManager;

public class DaoMetodoConexion extends Dao<MetodoConexion>
{
    private EntityManager _em;
    static DaoHandler _handler = new DaoHandler();


    public DaoMetodoConexion( )
    {
        super( _handler );
    }
}