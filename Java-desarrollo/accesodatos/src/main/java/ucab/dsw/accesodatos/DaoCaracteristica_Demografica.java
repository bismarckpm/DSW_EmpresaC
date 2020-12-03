package ucab.dsw.accesodatos;

import ucab.dsw.entidades.Caracteristica_Demografica;

import javax.persistence.EntityManager;

public class DaoCaracteristica_Demografica extends Dao<Caracteristica_Demografica>
{
    private EntityManager _em;
    static DaoHandler _handler = new DaoHandler();



    public DaoCaracteristica_Demografica( )
    {
        super( _handler );
    }
}