package ucab.dsw.accesodatos;

import ucab.dsw.entidades.Cliente;

import javax.persistence.EntityManager;

public class DaoCliente extends Dao<Cliente>
{
    private EntityManager _em;
    static DaoHandler _handler = new DaoHandler();


    public DaoCliente( )
    {
        super( _handler );
    }
}