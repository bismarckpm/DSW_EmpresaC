package ucab.dsw.accesodatos;

import ucab.dsw.entidades.MarcaTipo;

import javax.persistence.EntityManager;

public class DaoMarcaTipo extends Dao<MarcaTipo>
{
    private EntityManager _em;
    static DaoHandler _handler = new DaoHandler();


    public DaoMarcaTipo( )
    {
        super( _handler );
    }
}