package ucab.dsw.accesodatos;

import ucab.dsw.entidades.Nivel_Academico;

import javax.persistence.EntityManager;

public class DaoNivel_Academico extends Dao<Nivel_Academico>
{
    private EntityManager _em;
    static DaoHandler _handler = new DaoHandler();


    public DaoNivel_Academico( )
    {
        super( _handler );
    }
}