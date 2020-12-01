package ucab.dsw.accesodatos;

import ucab.dsw.entidades.Metodo_Conexion_Encuestado;


import javax.persistence.EntityManager;

public class DaoMetodo_Conexion_Encuestado extends Dao<Metodo_Conexion_Encuestado>
{
    private EntityManager _em;
    static DaoHandler _handler = new DaoHandler();


    public DaoMetodo_Conexion_Encuestado( )
    {
        super( _handler );
    }
}