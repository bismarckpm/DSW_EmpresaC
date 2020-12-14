package ucab.dsw.accesodatos;

import ucab.dsw.entidades.Marca;
import ucab.dsw.entidades.SolicitudEstudio;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class DaoSolicitudEstudio extends Dao<SolicitudEstudio>{
    private EntityManager _em;
    static DaoHandler _handler = new DaoHandler();


    public DaoSolicitudEstudio( )
    {
        super( _handler );
        this._em=_handler.getSession();
    }



    public List<SolicitudEstudio> getEstudiosByCliente(long cliente_id, long marca_id){
        TypedQuery<SolicitudEstudio> estudiosByCliente= this._em.createNamedQuery("EstudiosByCliente", SolicitudEstudio.class);
        estudiosByCliente.setParameter("cliente_id", cliente_id);
        estudiosByCliente.setParameter("marca_id", marca_id);
        List<SolicitudEstudio> resultList= estudiosByCliente.getResultList();

        return resultList;
    }

    public List<SolicitudEstudio> getEstudiosByAnalista(long analista_id){
        TypedQuery<SolicitudEstudio> estudiosByAnalista= this._em.createNamedQuery("EstudiosByAnalista", SolicitudEstudio.class);
        estudiosByAnalista.setParameter("analista_id", analista_id);
        List<SolicitudEstudio> resultList= estudiosByAnalista.getResultList();

        return resultList;
    }
}
