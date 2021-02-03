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
import javax.ws.rs.core.Response;

public class InsertSubcategoriaComando extends BaseComando {

    public SubcategoriaDto subcategoriaDto;

    public InsertSubcategoriaComando(SubcategoriaDto subcategoriaDto) {
        this.subcategoriaDto = subcategoriaDto;
    }

    @Override
    public void execute() throws EmpresaException{
        try {
            DaoSubcategoria daoSubcategoria = Fabrica.crear(DaoSubcategoria.class);
            Subcategoria subcategoria = SubcategoriaMapper.mapDtoToEntityInsert(subcategoriaDto);

            Subcategoria resul = daoSubcategoria.insert(subcategoria);

            subcategoriaDto=SubcategoriaMapper.mapEntityToDto(resul);

        }
        catch (PruebaExcepcion ex) {
            ex.printStackTrace();
            throw new EmpresaException("C-SUB06-ZERO-ID",ex.getMessage(), "Intento asignar el valor de 0 a un ID");
        }
        catch (PersistenceException | DatabaseException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-SUB06-DUP",ex.getMessage(), "La subcategoria ya se encuestra registrada");
        }

    }

    @Override
    public JsonObject getResult() throws EmpresaException{

        try {
            JsonObject data = Json.createObjectBuilder()
                    .add("estado", "success")
                    .add("mensaje", "Subcategoria agregada exitosamente")
                    .add("subcategoria_id", subcategoriaDto.getId()).build();

            return data;
        }
        catch (NullPointerException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-SUB06-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }
}
