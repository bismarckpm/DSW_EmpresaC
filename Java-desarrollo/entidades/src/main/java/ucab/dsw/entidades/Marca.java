package ucab.dsw.entidades;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table( name = "Marca" )
public class Marca extends EntidadBase{
    @Column( name = "nombre" )
    private String _nombre;

    @ManyToOne(optional = false , fetch = FetchType.EAGER)
    @JoinColumn( name = "Subcategoria_id" )
    private Subcategoria _subcategoria;

    public Subcategoria get_subcategoria()
    {
        return _subcategoria;
    }

    public void set_subcategoria( Subcategoria _subcategoria )
    {
        this._subcategoria = _subcategoria;
    }

    @OneToMany( mappedBy = "_marca_encuesta", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<Encuesta> _encuesta;


    @OneToMany( mappedBy = "_marca_solicitud", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<SolicitudEstudio> _solicitud;


    public String get_nombre() {
        return _nombre;
    }

    public void set_nombre(String _nombre) {
        this._nombre = _nombre;
    }



    public Marca( long id )
    {
        super( id );
    }

    public Marca()
    {

    }
}
