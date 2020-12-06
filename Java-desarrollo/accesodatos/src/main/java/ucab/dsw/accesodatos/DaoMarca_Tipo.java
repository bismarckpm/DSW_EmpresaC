package ucab.dsw.accesodatos;

import ucab.dsw.entidades.Marca_Tipo;

import javax.persistence.EntityManager;
public class DaoMarca_Tipo extends Dao<Marca_Tipo>{

    private EntityManager _em;
    static DaoHandler _handler = new DaoHandler();


    public DaoMarca_Tipo( )
    {
        super( _handler );
    }
}
