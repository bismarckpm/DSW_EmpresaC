package ucab.dsw.entidades;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Date;
import java.util.List;


@Entity
@Table( name = "hijo" )
public class Hijo extends EntidadBase {

    @Column( name = "nombre" )
    private String _nombre;

    public String get_nombre()
    {
        return _nombre;
    }

    public void set_nombre( String _nombre )
    {
        this._nombre = _nombre;
    }

    @Column( name = "apellido" )
    private String _apellido;

    public String get_apellido() { return _apellido; }

    public void set_apellido( String _apellido ) { this._apellido = _apellido; }

    @Column( name = "genero" )
    private String _genero;

    public String get_genero()
    {
        return _genero;
    }

    public void set_genero( String _genero )
    {
        this._genero = _genero;
    }

    @Column( name = "fecha_nacimiento" )
    private Date _fecha_nacimiento;

    public Date get_fecha_nacimiento() { return _fecha_nacimiento; }

    public void set_fecha_nacimiento( Date _fecha_nacimiento ) { this._fecha_nacimiento = _fecha_nacimiento; }



    @ManyToOne(optional = false, cascade = CascadeType.PERSIST , fetch = FetchType.EAGER)
    @JoinColumn( name = "Encuestado_id" )
    private Encuestado _encuestado_hijo;

    public Encuestado get_encuestado_hijo()
    {
        return _encuestado_hijo;
    }

    public void set_encuestado_hijo( Encuestado _encuestado_hijo )
    {
        this._encuestado_hijo = _encuestado_hijo;
    }

    public Hijo( long id )
    {
        super( id );
    }

    public Hijo()
    {

    }
}
