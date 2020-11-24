package ucab.dsw.accesodatos;

import ucab.dsw.entidades.RespuestaOpcion;

import javax.persistence.EntityManager;
public class DaoRespuestaOpcion extends Dao<RespuestaOpcion>{

    private EntityManager _em;
    static DaoHandler _handler = new DaoHandler();


    public DaoRespuestaOpcion( )
    {
        super( _handler );
    }
}
