package ucab.dsw.accesodatos;


import ucab.dsw.entidades.Ocupacion_Encuestado;

import javax.persistence.EntityManager;

public class DaoOcupacion_Encuestado extends Dao<Ocupacion_Encuestado>
{
    private EntityManager _em;
    static DaoHandler _handler = new DaoHandler();


    public DaoOcupacion_Encuestado( )
    {
        super( _handler );
    }
}