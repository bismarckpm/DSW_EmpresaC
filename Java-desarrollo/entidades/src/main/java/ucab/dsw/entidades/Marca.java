package ucab.dsw.entidades;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name="MarcasBySubcategoriaId", query="select m FROM Marca m where m._subcategoria._id=:subcategoria_id")
})
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


    @OneToMany( mappedBy = "_marca", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<Marca_Tipo> _marcatipos;

    public String get_nombre() {
        return _nombre;
    }

    public void set_nombre(String _nombre) {
        this._nombre = _nombre;
    }

    @Column( name = "estado" )
    private String _estado;

    public String get_estado() {
        return _estado;
    }

    public void set_estado(String _estado) {
        this._estado = _estado;
    }


    public Marca( long id )
    {
        super( id );
    }

    public Marca()
    {

    }
}
