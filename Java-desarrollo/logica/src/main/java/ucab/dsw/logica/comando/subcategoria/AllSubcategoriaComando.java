package ucab.dsw.logica.comando.subcategoria;

import ucab.dsw.accesodatos.DaoCategoria;
import ucab.dsw.accesodatos.DaoSubcategoria;
import ucab.dsw.entidades.Categoria;
import ucab.dsw.entidades.Subcategoria;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.List;

public class AllSubcategoriaComando extends BaseComando {

    public JsonArrayBuilder subcategoriaArrayJson= Json.createArrayBuilder();

    @Override
    public void execute() throws EmpresaException {

        try {
            DaoSubcategoria dao = Fabrica.crear(DaoSubcategoria.class);
            DaoCategoria daoCategoria = Fabrica.crear(DaoCategoria.class);

            List<Subcategoria> resultado = dao.findAll(Subcategoria.class);

            for (Subcategoria obj : resultado) {

                Categoria categoria = daoCategoria.find(obj.get_categoria().get_id(), Categoria.class);
                JsonObject subcategoria = Json.createObjectBuilder().add("id", obj.get_id())
                        .add("nombre", obj.get_nombre())
                        .add("categoria_id", categoria.get_id())
                        .add("categoria", categoria.get_nombre())
                        .add("estado", obj.get_estado()).build();

                subcategoriaArrayJson.add(subcategoria);

            }
        }
        catch (NullPointerException ex){
            throw new EmpresaException("C-SUB02-E-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }

    }

    @Override
    public JsonObject getResult() throws EmpresaException{

        try {
            JsonObject data = Json.createObjectBuilder()
                    .add("estado", "success")
                    .add("mensaje", "Todas las subcategorias")
                    .add("subcategorias", subcategoriaArrayJson).build();

            return data;
        }
        catch (NullPointerException ex){
            throw new EmpresaException("C-SUB02-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }
}
