package ucab.dsw.accesodatos;

import ucab.dsw.entidades.Opcion_Simple_Multiple_Pregunta;

import javax.persistence.EntityManager;
public class DaoOpcion_Simple_Multiple_Pregunta extends Dao<Opcion_Simple_Multiple_Pregunta> {


    private EntityManager _em;
    static DaoHandler _handler = new DaoHandler();


    public DaoOpcion_Simple_Multiple_Pregunta( )
    {
        super( _handler );
    }
}
