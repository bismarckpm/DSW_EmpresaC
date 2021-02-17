package ucab.dsw.accesodatos;

import ucab.dsw.entidades.OpcionSimpleMultiplePregunta;

import javax.persistence.EntityManager;
public class DaoOpcionSimpleMultiplePregunta extends Dao<OpcionSimpleMultiplePregunta> {


    private EntityManager _em;
    static DaoHandler _handler = new DaoHandler();


    public DaoOpcionSimpleMultiplePregunta( )
    {
        super( _handler );
    }
}
