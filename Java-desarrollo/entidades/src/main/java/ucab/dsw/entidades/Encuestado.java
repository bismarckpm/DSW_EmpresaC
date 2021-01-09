package ucab.dsw.entidades;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table( name = "encuestado" )
@NamedQueries({
        @NamedQuery(name="EncuestadoId", query="select c FROM Encuestado c where c._usuario_encuestado._id=:usuario_id")
})
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

    @Column( name = "estado")
    private String _estado;

    public String get_estado() { return _estado; }

    public void set_estado(String _estado) { this._estado = _estado; }

    @OneToMany( mappedBy = "_encuestado_telefono", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<Telefono> _telefono;

    public List<Telefono> get_telefono()
    {
        return _telefono;
    }

    public void set_telefono( List<Telefono> _telefono )
    {
        this._telefono = _telefono;
    }

    @OneToMany( mappedBy = "_encuestado_hijo", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<Hijo> _hijo;

    public List<Hijo> get_hijo()
    {
        return _hijo;
    }

    public void set_hijo( List<Hijo> _hijo )
    {
        this._hijo = _hijo;
    }

    @OneToMany( mappedBy = "_encuestado_metodo_conexion", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<MetodoConexionEncuestado> _metodo_Conexion_Encuestado;

    public List<MetodoConexionEncuestado> get_metodo_Conexion_Encuestado() { return _metodo_Conexion_Encuestado; }

    public void set_metodo_Conexion_Encuestado( List<MetodoConexionEncuestado> _metodo_Conexion_Encuestado )
    {
        this._metodo_Conexion_Encuestado = _metodo_Conexion_Encuestado;
    }

    @OneToMany( mappedBy = "_encuestado_ocupacion", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<OcupacionEncuestado> _ocupacion_Encuestado;

    public List<OcupacionEncuestado> get_ocupacion_Encuestado()
    {
        return _ocupacion_Encuestado;
    }

    public void set_ocupacion_Encuestado( List<OcupacionEncuestado> _ocupacion_Encuestado )
    {
        this._ocupacion_Encuestado = _ocupacion_Encuestado;
    }

    @ManyToOne(optional = false , fetch = FetchType.EAGER)
    @JoinColumn( name = "Parroquia_id" )
    private Parroquia _Parroquia_encuestado;

    public Parroquia get_Parroquia_encuestado()
    {
        return _Parroquia_encuestado;
    }

    public void set_Parroquia_encuestado( Parroquia _Parroquia_encuestado )
    {
        this._Parroquia_encuestado = _Parroquia_encuestado;
    }

    @ManyToOne(optional = false , fetch = FetchType.EAGER)
    @JoinColumn( name = "Nivel_academico_id" )
    private NivelAcademico _nivel_academico_encuestado;

    public NivelAcademico get_nivel_academico_encuestado()
    {
        return _nivel_academico_encuestado;
    }

    public void set_nivel_academico_encuestado( NivelAcademico _nivel_academico_encuestado )
    {
        this._nivel_academico_encuestado = _nivel_academico_encuestado;
    }

    @ManyToOne(optional = false , fetch = FetchType.EAGER /*,cascade = CascadeType.PERSIST*/)
    @JoinColumn( name = "Usuario_id" )
    private Usuario _usuario_encuestado;

    public Usuario get_usuario_encuestado()
    {
        return _usuario_encuestado;
    }

    public void set_usuario_encuestado( Usuario _usuario_encuestado )
    {
        this._usuario_encuestado = _usuario_encuestado;
    }

    @OneToMany( mappedBy = "_encuestado", fetch = FetchType.LAZY , cascade = CascadeType.PERSIST )
    private List<Participacion> _participacion;
}
