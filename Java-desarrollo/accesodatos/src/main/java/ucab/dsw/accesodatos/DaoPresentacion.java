package ucab.dsw.accesodatos;

import ucab.dsw.entidades.Presentacion;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class DaoPresentacion extends Dao<Presentacion>{

    private EntityManager _em;
    static DaoHandler _handler = new DaoHandler();


    public DaoPresentacion( )
    {
        super( _handler );
        this._em=_handler.getSession();
    }

    public List<Presentacion> getAllPresentacionesByTipo(long marca_id){
        TypedQuery<Presentacion> presentacionesByTipo= this._em.createNamedQuery("PresentacionesByTipo", Presentacion.class);
        presentacionesByTipo.setParameter("tipo_id", marca_id);
        List<Presentacion> resultList= presentacionesByTipo.getResultList();

        return resultList;
    }
}
