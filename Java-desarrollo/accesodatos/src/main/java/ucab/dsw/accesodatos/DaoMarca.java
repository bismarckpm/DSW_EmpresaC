package ucab.dsw.accesodatos;

import ucab.dsw.entidades.Marca;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class DaoMarca extends Dao<Marca>{

    private EntityManager _em;
    static DaoHandler _handler = new DaoHandler();


    public DaoMarca( )
    {
        super( _handler );
        this._em=_handler.getSession();
    }



    public List<Marca> getMarcasBySubcategoria(long subcategoria_id){
        TypedQuery<Marca> marcasBySubcategoriaId= this._em.createNamedQuery("MarcasBySubcategoriaId", Marca.class);
        marcasBySubcategoriaId.setParameter("subcategoria_id", subcategoria_id);
        List<Marca> resultList= marcasBySubcategoriaId.getResultList();

        return resultList;
    }
}
