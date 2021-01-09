package ucab.dsw.accesodatos;

import ucab.dsw.entidades.MetodoConexionEncuestado;


import javax.persistence.EntityManager;

public class DaoMetodoConexionEncuestado extends Dao<MetodoConexionEncuestado>
{
    private EntityManager _em;
    static DaoHandler _handler = new DaoHandler();


    public DaoMetodoConexionEncuestado( )
    {
        super( _handler );
    }
}