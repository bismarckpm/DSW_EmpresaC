package ucab.dsw.accesodatos;

import ucab.dsw.entidades.Metodo_conexion;

import javax.persistence.EntityManager;

public class DaoMetodo_Conexion extends Dao<Metodo_conexion>
{
    private EntityManager _em;
    static DaoHandler _handler = new DaoHandler();


    public DaoMetodo_Conexion( )
    {
        super( _handler );
    }
}