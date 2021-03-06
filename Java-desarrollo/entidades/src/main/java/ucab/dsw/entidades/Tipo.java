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

    @OneToMany( mappedBy = "_tipo", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<MarcaTipo> _marcatipos;

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
