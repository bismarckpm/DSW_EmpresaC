package ucab.dsw.logica.comando.tipo;

import org.eclipse.persistence.exceptions.DatabaseException;
import ucab.dsw.accesodatos.DaoSubcategoria;
import ucab.dsw.accesodatos.DaoTipo;
import ucab.dsw.dtos.SubcategoriaDto;
import ucab.dsw.dtos.TipoDto;
import ucab.dsw.entidades.Subcategoria;
import ucab.dsw.entidades.Tipo;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.excepciones.PruebaExcepcion;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import ucab.dsw.mappers.SubcategoriaMapper;
import ucab.dsw.mappers.TipoMapper;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.PersistenceException;

public class UpdateTipoComando extends BaseComando {
    public long _id;
    public TipoDto tipoDto;

    public UpdateTipoComando(long _id, TipoDto tipoDto) {
        this._id = _id;
        this.tipoDto = tipoDto;
    }

    @Override
    public void execute() throws EmpresaException {
        try {
            DaoTipo dao = Fabrica.crear(DaoTipo.class);
            Tipo tipo= TipoMapper.mapDtoToEntityUpdate(_id,tipoDto);

            Tipo resul = dao.update(tipo);
            tipoDto=TipoMapper.mapEntityToDto(resul);

        }
        catch (PersistenceException | DatabaseException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-TI06-DUP",ex.getMessage(), "El tipo ya se encuestra registrado");
        }
        catch (PruebaExcepcion ex) {
            ex.printStackTrace();
            throw new EmpresaException("C-TI06-ZERO-ID",ex.getMessage(), "Intento asignar el valor de 0 a un ID");
        }
    }

    @Override
    public JsonObject getResult() throws EmpresaException  {

        try{
            JsonObject data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("mensaje","Tipo actualizado correctamente")
                    .add("nombre_tipo",tipoDto.getNombre()).build();
            return data;
        }
        catch (NullPointerException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-TI06-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }
}
