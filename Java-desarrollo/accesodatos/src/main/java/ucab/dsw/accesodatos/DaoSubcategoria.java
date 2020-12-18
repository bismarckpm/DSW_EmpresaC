package ucab.dsw.accesodatos;

import ucab.dsw.entidades.Subcategoria;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class DaoSubcategoria extends Dao<Subcategoria>{
    private EntityManager _em;
    static DaoHandler _handler = new DaoHandler();


    public DaoSubcategoria( )
    {
        super( _handler );
        this._em=_handler.getSession();
    }

    public List<Subcategoria> getSubcategoriasByCategoria(long categoria_id){
        TypedQuery<Subcategoria> subcategoriasBycategoriaId= this._em.createNamedQuery("SubcategoriaByCategoriaId", Subcategoria.class);
        subcategoriasBycategoriaId.setParameter("categoria_id", categoria_id);
        List<Subcategoria> resultList= subcategoriasBycategoriaId.getResultList();

        return resultList;
    }
}
