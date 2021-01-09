package ucab.dsw.accesodatos;

import ucab.dsw.entidades.MarcaTipo;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class DaoMarcaTipo extends Dao<MarcaTipo>{

    private EntityManager _em;
    static DaoHandler _handler = new DaoHandler();


    public DaoMarcaTipo( )
    {
        super( _handler );
        this._em=_handler.getSession();
    }

    public List<MarcaTipo> getAllMarcaTiposByMarca(long marca_id){
        TypedQuery<MarcaTipo> marcaTiposByMarca= this._em.createNamedQuery("MarcaTiposByMarca", MarcaTipo.class);
        marcaTiposByMarca.setParameter("marca_id", marca_id);
        List<MarcaTipo> resultList= marcaTiposByMarca.getResultList();

        return resultList;
    }
}
