package ucab.dsw.accesodatos;

import ucab.dsw.entidades.Cliente;

import javax.persistence.EntityManager;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class DaoCliente extends Dao<Cliente>
{
    private EntityManager _em;
    static DaoHandler _handler = new DaoHandler();


    public DaoCliente( )
    {
        super( _handler );
        this._em=_handler.getSession();
    }

    public Cliente getClienteId(long usuario_id){
        TypedQuery<Cliente> clienteId= this._em.createNamedQuery("ClienteId", Cliente.class);
        clienteId.setParameter("usuario_id", usuario_id);
        Cliente resultList= clienteId.getSingleResult();

        return resultList;
    }

}