package ucab.dsw.accesodatos;

import ucab.dsw.entidades.OpcionSimpleMultiple;

import javax.persistence.EntityManager;
public class DaoOpcionSimpleMultiple extends Dao<OpcionSimpleMultiple> {


    private EntityManager _em;
    static DaoHandler _handler = new DaoHandler();


    public DaoOpcionSimpleMultiple( )
    {
        super( _handler );
    }
}
