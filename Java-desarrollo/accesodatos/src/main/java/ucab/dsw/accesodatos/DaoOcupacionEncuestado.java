package ucab.dsw.accesodatos;


import ucab.dsw.entidades.OcupacionEncuestado;

import javax.persistence.EntityManager;

public class DaoOcupacionEncuestado extends Dao<OcupacionEncuestado>
{
    private EntityManager _em;
    static DaoHandler _handler = new DaoHandler();


    public DaoOcupacionEncuestado( )
    {
        super( _handler );
    }
}