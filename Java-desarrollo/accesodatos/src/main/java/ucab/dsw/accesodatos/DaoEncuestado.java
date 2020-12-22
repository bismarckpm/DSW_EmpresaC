package ucab.dsw.accesodatos;

import ucab.dsw.entidades.Encuestado;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class DaoEncuestado extends Dao<Encuestado>
{
    private EntityManager _em;
    static DaoHandler _handler = new DaoHandler();


    public DaoEncuestado( )
    {
        super( _handler );
        this._em=_handler.getSession();
    }

    public Encuestado getEncuestadoId(long usuario_id){
        TypedQuery<Encuestado> encuestadoId= this._em.createNamedQuery("EncuestadoId", Encuestado.class);
        encuestadoId.setParameter("usuario_id", usuario_id);
        Encuestado resultList= encuestadoId.getSingleResult();

        return resultList;
    }
}