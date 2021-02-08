package ucab.dsw.logica.comando.presentacion;

import org.eclipse.persistence.exceptions.DatabaseException;
import ucab.dsw.accesodatos.DaoPresentacion;
import ucab.dsw.accesodatos.DaoSubcategoria;
import ucab.dsw.dtos.PresentacionDto;
import ucab.dsw.dtos.SubcategoriaDto;
import ucab.dsw.entidades.Presentacion;
import ucab.dsw.entidades.Subcategoria;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.excepciones.PruebaExcepcion;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import ucab.dsw.mappers.PresentacionMapper;
import ucab.dsw.mappers.SubcategoriaMapper;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.PersistenceException;

public class UpdatePresentacionComando extends BaseComando {
    public long _id;
    public PresentacionDto presentacionDto;


    public UpdatePresentacionComando(long _id, PresentacionDto presentacionDto) {
        this._id = _id;
        this.presentacionDto = presentacionDto;
    }

    @Override
    public void execute() throws EmpresaException {
        try {
            DaoPresentacion dao = Fabrica.crear(DaoPresentacion.class);
            Presentacion presentacion= PresentacionMapper.mapDtoToEntityUpdate(_id,presentacionDto);

            Presentacion resul= dao.update(presentacion);

            presentacionDto= PresentacionMapper.mapEntityToDto(resul);

        }
        catch (PersistenceException | DatabaseException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-PRE06-DUP",ex.getMessage(), "La Presentacion ya se encuestra registrada");
        }
        catch (PruebaExcepcion ex) {
            ex.printStackTrace();
            throw new EmpresaException("C-PRE06-ZERO-ID",ex.getMessage(), "Intento asignar el valor de 0 a un ID");
        }
    }

    @Override
    public JsonObject getResult() throws EmpresaException {
        try{
            JsonObject data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("mensaje","Presentacion actualizada correctamente")
                    .add("nombre_presentacion",presentacionDto.getNombre()).build();
            return data;
        }
        catch (NullPointerException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-PRE06-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }

    }
}
