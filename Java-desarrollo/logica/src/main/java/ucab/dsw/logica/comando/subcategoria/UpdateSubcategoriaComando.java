package ucab.dsw.logica.comando.subcategoria;

import org.eclipse.persistence.exceptions.DatabaseException;
import ucab.dsw.accesodatos.DaoCategoria;
import ucab.dsw.accesodatos.DaoSubcategoria;
import ucab.dsw.dtos.SubcategoriaDto;
import ucab.dsw.entidades.Categoria;
import ucab.dsw.entidades.Subcategoria;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.excepciones.PruebaExcepcion;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import ucab.dsw.mappers.SubcategoriaMapper;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.PersistenceException;

public class UpdateSubcategoriaComando extends BaseComando {

    public long _id;
    public SubcategoriaDto subcategoriaDto;

    public UpdateSubcategoriaComando(long _id, SubcategoriaDto subcategoriaDto) {
        this._id = _id;
        this.subcategoriaDto = subcategoriaDto;
    }

    @Override
    public void execute() throws EmpresaException{
        try {
            DaoSubcategoria dao = Fabrica.crear(DaoSubcategoria.class);
            Subcategoria subcategoria= SubcategoriaMapper.mapDtoToEntityUpdate(_id,subcategoriaDto);

            Subcategoria resul = dao.update(subcategoria);
            subcategoriaDto=SubcategoriaMapper.mapEntityToDto(resul);

        }
        catch (PruebaExcepcion ex) {
            ex.printStackTrace();
            throw new EmpresaException("C-SUB07-ZERO-ID",ex.getMessage(), "Intento asignar el valor de 0 a un ID");
        }
        catch (PersistenceException | DatabaseException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-SUB07-DUP",ex.getMessage(), "La subcategoria ya se encuestra registrada");
        }
    }

    @Override
    public JsonObject getResult() throws EmpresaException{
        try{
            JsonObject data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("mensaje","Subcategoria actualizada correctamente")
                    .add("nombre_subcategoria",subcategoriaDto.getNombre()).build();
            return data;
        }
        catch (NullPointerException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-SUB07-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }

    }
}
