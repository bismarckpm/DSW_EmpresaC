package ucab.dsw.entidades;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class EntidadBase implements Serializable
{

    @Id
    @Column( name = "id" )
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long _id;

    public EntidadBase( long id )
    {
        _id = id;
    }

    public EntidadBase()
    {
    }

    public long get_id()
    {
        return _id;
    }

    @Column( name = "estado" )
    private String _estados;

    public String get_estados()
    {
        return _estados;
    }

    public void set_estados( String _estados )
    {
        this._estados = _estados;
    }


}
