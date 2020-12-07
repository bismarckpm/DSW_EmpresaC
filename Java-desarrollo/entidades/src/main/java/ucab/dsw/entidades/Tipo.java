package ucab.dsw.entidades;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table( name = "Tipo" )
public class Tipo extends EntidadBase{

    @Column( name = "nombre" )
    private String _nombre;

    @Column( name = "estado" )
    private String _estado;

    @OneToMany( mappedBy = "_tipo_Presentacion", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<Presentacion> _presentacion;

<<<<<<< HEAD
    @OneToMany( mappedBy = "_tipo_encuesta", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<MarcaTipo> _encuestatipo;
=======
    @OneToMany( mappedBy = "_tipo_marca", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<Marca_Tipo> _marcatipo;
>>>>>>> 269b5caca644055e5622875a19c1a5450d20a301

    public String get_nombre() {
        return _nombre;
    }

    public void set_nombre(String _nombre) {
        this._nombre = _nombre;
    }

    public String get_estado() {
        return _estado;
    }

    public void set_estado(String _estado) {
        this._estado = _estado;
    }


    public Tipo( long id )
    {
        super( id );
    }

    public Tipo()
    {

    }
}
