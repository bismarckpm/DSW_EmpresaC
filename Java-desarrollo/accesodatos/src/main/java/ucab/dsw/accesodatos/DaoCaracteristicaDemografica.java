package ucab.dsw.accesodatos;

import ucab.dsw.entidades.CaracteristicaDemografica;

import javax.persistence.EntityManager;

public class DaoCaracteristicaDemografica extends Dao<CaracteristicaDemografica>
{
    private EntityManager _em;
    static DaoHandler _handler = new DaoHandler();



    public DaoCaracteristicaDemografica( )
    {
        super( _handler );
    }
}