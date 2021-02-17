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

public class InsertPresentacionComando extends BaseComando {
    public PresentacionDto presentacionDto;

    public InsertPresentacionComando(PresentacionDto presentacionDto) {
        this.presentacionDto = presentacionDto;
    }

    @Override
    public void execute() throws EmpresaException {
        try {
            DaoPresentacion daoPresentacion = Fabrica.crear(DaoPresentacion.class);
            Presentacion presentacion= PresentacionMapper.mapDtoToEntityInsert(presentacionDto);


            Presentacion resul = daoPresentacion.insert(presentacion);
            presentacionDto= PresentacionMapper.mapEntityToDto(resul);

        }
        catch (PruebaExcepcion ex) {
            ex.printStackTrace();
            throw new EmpresaException("C-PRE05-ZERO-ID",ex.getMessage(), "Intento asignar el valor de 0 a un ID");
        }
        catch (PersistenceException | DatabaseException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-PRE05-DUP",ex.getMessage(), "La presentacion ya se encuestra registrada");
        }

    }

    @Override
    public JsonObject getResult() throws EmpresaException {

        try{
            JsonObject data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("mensaje","Presentacion agregada exitosamente")
                    .add("presentacion_id",presentacionDto.getId()).build();

        return data;
        }
        catch (NullPointerException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-PRE05-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }
}
