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
@Table( name = "telefono" )
public class Telefono extends EntidadBase {

        @Column( name = "numero" )
        private String _numero;

        public String get_numero()
        {
            return _numero;
        }

        public void set_numero( String _numero )
        {
            this._numero = _numero;
        }

        @Column( name = "codigo_area" )
        private String _codigo_area;

        public String get_codigo_area() { return _codigo_area; }

        public void set_codigo_area( String _codigo_area ) { this._codigo_area = _codigo_area; }

    @ManyToOne
    @JoinColumn( name = "idEncuestado" )
    private Encuestado _encuestado_telefono;

    public Encuestado get_encuestado_telefono()
    {
        return _encuestado_telefono;
    }

    public void set_encuestado_telefono( Encuestado _encuestado_telefono )
    {
        this._encuestado_telefono = _encuestado_telefono;
    }

    public Telefono( long id )
    {
        super( id );
    }

    public Telefono()
    {

    }

}
