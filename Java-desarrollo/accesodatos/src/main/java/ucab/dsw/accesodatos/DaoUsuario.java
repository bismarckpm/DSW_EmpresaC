package ucab.dsw.accesodatos;

import ucab.dsw.entidades.Usuario;
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
        this._em=_handler.getSession();
    }



    public List<Usuario> getAdmins(){
        TypedQuery<Usuario> estudiosByCliente= this._em.createNamedQuery("Admins", Usuario.class);
        List<Usuario> resultList= estudiosByCliente.getResultList();

        return resultList;
    }
}