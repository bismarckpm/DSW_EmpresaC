package ucab.dsw.accesodatos;

import ucab.dsw.entidades.Participacion;

import javax.persistence.EntityManager;
public class DaoParticipacion extends Dao<Participacion>{
    private EntityManager _em;
    static DaoHandler _handler = new DaoHandler();


    public DaoParticipacion( )
    {
        super( _handler );
    }
}
