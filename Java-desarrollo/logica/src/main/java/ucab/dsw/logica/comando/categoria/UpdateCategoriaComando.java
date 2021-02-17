package ucab.dsw.logica.comando.categoria;
import org.eclipse.persistence.exceptions.DatabaseException;
import ucab.dsw.dtos.ResponseDto;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.excepciones.PruebaExcepcion;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.*;
import ucab.dsw.entidades.*;
import ucab.dsw.logica.fabrica.Fabrica;
import ucab.dsw.mappers.CategoriaMapper;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.PersistenceException;


public class UpdateCategoriaComando extends BaseComando {

    public long _id;
    public CategoriaDto categoriaDto;

    public UpdateCategoriaComando(long _id, CategoriaDto categoriaDto) {
        this._id = _id;
        this.categoriaDto = categoriaDto;
    }

    @Override
    public void execute() throws EmpresaException {
        try{
            DaoCategoria dao = Fabrica.crear(DaoCategoria.class);
            Categoria categoria= CategoriaMapper.mapDtoToEntityUpdate(_id,categoriaDto);
            Categoria resul = dao.update(categoria);
            this.categoriaDto=CategoriaMapper.mapEntityToDto(resul);
        }
        catch (PersistenceException | DatabaseException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-CA06-DUP",ex.getMessage(), "La categoria ya se encuentra registrada");
        }
        catch (PruebaExcepcion ex) {
            ex.printStackTrace();
            throw new EmpresaException("C-CA06-ZERO-ID",ex.getMessage(), "Intento asignar el valor de 0 a un ID");
        }



    }

    @Override
    public JsonObject getResult() throws EmpresaException{

        try {
            JsonObject data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("mensaje","Categoria actualizada")
                    .add("categoria_nombre",this.categoriaDto.getNombre()).build();

            return data;
        }
        catch (NullPointerException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-CA06-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }

    }
}
