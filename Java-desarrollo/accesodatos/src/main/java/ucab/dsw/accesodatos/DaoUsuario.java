package ucab.dsw.accesodatos;

import ucab.dsw.entidades.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class DaoUsuario extends Dao<Usuario>
{
    private EntityManager _em;
    static DaoHandler _handler = new DaoHandler();


    public DaoUsuario( )
    {
        super( _handler );
    }

    public List<Usuario> getAnalistas(){
        _em=_handler.getSession();
        _handler.beginTransaction();
        TypedQuery<Usuario> analistas= this._em.createNamedQuery("analistas", Usuario.class);
        List<Usuario> resultList= analistas.getResultList();
        _em.flush();
        _em.clear();
        _handler.finishTransaction();


        return resultList;
    }
}