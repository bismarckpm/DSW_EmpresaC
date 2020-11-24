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
@Table( name = "encuestado" )
public class Encuestado extends EntidadBase
{

    @Column( name = "doc_id" )
    private int _doc_id;

    public int get_doc_id()
    {
        return _doc_id;
    }

    public void set_doc_id( int _doc_id )
    {
        this._doc_id = _doc_id;
    }

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

    public String get_apellido()
    {
        return _apellido;
    }

    public void set_apellido( String _apellido )
    {
        this._apellido = _apellido;
    }

    @Column( name = "correo" )
    private String _correo;

    public String get_correo()
    {
        return _correo;
    }

    public void set_correo( String _correo )
    {
        this._correo = _correo;
    }

    @Column( name = "fecha_nacimiento" )
    private Date _fecha_nacimiento;

    public Date get_fecha_nacimiento()
    {
        return _fecha_nacimiento;
    }

    public void set_fecha_nacimiento( Date _fecha_nacimiento )
    {
        this._fecha_nacimiento = _fecha_nacimiento;
    }

    @Column( name = "cant_personas_vivienda" )
    private int _cant_personas_vivienda;

    public int get_cant_personas_vivienda()
    {
        return _cant_personas_vivienda;
    }

    public void set_cant_personas_vivienda( int _cant_personas_vivienda )
    {
        this._cant_personas_vivienda = _cant_personas_vivienda;
    }

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

    @ManyToOne
    @JoinColumn( name = "idParroquia" )
    private Parroquia _Parroquia_encuestado;

    public Parroquia get_Parroquia_encuestado()
    {
        return _Parroquia_encuestado;
    }

    public void set_Parroquia_encuestado( Parroquia _Parroquia_encuestado )
    {
        this._Parroquia_encuestado = _Parroquia_encuestado;
    }

    @ManyToOne
    @JoinColumn( name = "idNivel_Academico" )
    private Nivel_Academico _nivel_academico_encuestado;

    public Nivel_Academico get_nivel_academico_encuestado()
    {
        return _nivel_academico_encuestado;
    }

    public void set_nivel_academico_encuestado( Nivel_Academico _nivel_academico_encuestado )
    {
        this._nivel_academico_encuestado = _nivel_academico_encuestado;
    }

    @ManyToOne
    @JoinColumn( name = "idUsuario" )
    private Usuario _usuario_encuestado;

    public Usuario get_usuario_encuestado()
    {
        return _usuario_encuestado;
    }

    public void set_usuario_encuestado( Usuario _usuario_encuestado )
    {
        this._usuario_encuestado = _usuario_encuestado;
    }
}
