package ucab.dsw.logica.comando.categoria;

import org.eclipse.persistence.exceptions.DatabaseException;
import ucab.dsw.accesodatos.DaoCategoria;
import ucab.dsw.dtos.CategoriaDto;
import ucab.dsw.dtos.ResponseDto;
import ucab.dsw.entidades.Categoria;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.excepciones.PruebaExcepcion;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import ucab.dsw.mappers.CategoriaMapper;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.PersistenceException;
import javax.ws.rs.core.Response;


public class InsertCategoriaComando extends BaseComando {

    public CategoriaDto categoriaDto;

    public InsertCategoriaComando(CategoriaDto categoriaDto) {
        this.categoriaDto = categoriaDto;
    }

    @Override
    public void execute() throws EmpresaException {

        try {
            DaoCategoria dao = Fabrica.crear(DaoCategoria.class);
            Categoria categoria = CategoriaMapper.mapDtoToEntityInsert(this.categoriaDto);
            Categoria resul = dao.insert( categoria );
            this.categoriaDto=CategoriaMapper.mapEntityToDto(resul);

        }
        catch (PruebaExcepcion ex) {
            ex.printStackTrace();
            throw new EmpresaException("C-CA05-ZERO-ID",ex.getMessage(), "Intento asignar el valor de 0 a un ID");
        }
        catch (PersistenceException | DatabaseException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-CA05-DUP",ex.getMessage(), "La categoria ya se encuestra registrada");
        }
    }

    @Override
    public JsonObject getResult() throws EmpresaException{

        try{
            JsonObject data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("mensaje","Categoria añadida")
                    .add("categoria_id",this.categoriaDto.getId()).build();

            return data;
        }
        catch (NullPointerException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-CA05-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }
}
