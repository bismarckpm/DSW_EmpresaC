package ucab.dsw.accesodatos;

import ucab.dsw.entidades.Pais;

import javax.persistence.EntityManager;

public class DaoPais extends Dao<Pais>
{
    private EntityManager _em;
    static DaoHandler _handler = new DaoHandler();


    public DaoPais( )
    {
        super( _handler );
    }
}