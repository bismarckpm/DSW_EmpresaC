package ucab.dsw.accesodatos;

import ucab.dsw.entidades.Participacion;
import ucab.dsw.entidades.SolicitudEstudio;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class DaoParticipacion extends Dao<Participacion>{
    private EntityManager _em;
    static DaoHandler _handler = new DaoHandler();


    public DaoParticipacion( )
    {
        super( _handler );
        this._em=_handler.getSession();
    }

    public List<Participacion> getParticipacionByEstudio(long _id){
        TypedQuery<Participacion> participacionByEstudio= this._em.createNamedQuery("ParticipacionByEstudio", Participacion.class);
        participacionByEstudio.setParameter("estudio_id", _id);
        List<Participacion> resultList= participacionByEstudio.getResultList();

        return resultList;
    }

}
