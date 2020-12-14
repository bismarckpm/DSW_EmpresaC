package ucab.dsw.accesodatos;

import ucab.dsw.entidades.Marca_Tipo;
import ucab.dsw.entidades.SolicitudEstudio;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class DaoMarca_Tipo extends Dao<Marca_Tipo>{

    private EntityManager _em;
    static DaoHandler _handler = new DaoHandler();


    public DaoMarca_Tipo( )
    {
        super( _handler );
        this._em=_handler.getSession();
    }

    public List<Marca_Tipo> getAllMarcaTiposByMarca( long marca_id){
        TypedQuery<Marca_Tipo> marcaTiposByMarca= this._em.createNamedQuery("MarcaTiposByMarca", Marca_Tipo.class);
        marcaTiposByMarca.setParameter("marca_id", marca_id);
        List<Marca_Tipo> resultList= marcaTiposByMarca.getResultList();

        return resultList;
    }
}
