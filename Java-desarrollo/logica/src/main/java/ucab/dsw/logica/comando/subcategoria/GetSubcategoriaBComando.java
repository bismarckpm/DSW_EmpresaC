package ucab.dsw.logica.comando.subcategoria;


import ucab.dsw.accesodatos.DaoSubcategoria;
import ucab.dsw.entidades.Subcategoria;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.List;

public class GetSubcategoriaBComando extends BaseComando {

   public long _id;
   public JsonArrayBuilder subcategoriasByCategoriaId= Json.createArrayBuilder();

    public GetSubcategoriaBComando(long _id) {
        this._id = _id;
    }

    @Override
    public void execute() throws EmpresaException {

        try {
            DaoSubcategoria dao = Fabrica.crear(DaoSubcategoria.class);
            List<Subcategoria> resultado = dao.getSubcategoriasByCategoria(_id);


            for (Subcategoria obj : resultado) {

                JsonObject subcategoria = Json.createObjectBuilder().add("id", obj.get_id())
                        .add("nombre", obj.get_nombre())
                        .add("estado", obj.get_estado()).build();

                subcategoriasByCategoriaId.add(subcategoria);

            }
        }
        catch (NullPointerException ex){
            throw new EmpresaException("C-SUB04-E-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }


    }

    @Override
    public JsonObject getResult() throws EmpresaException{
        try {
            JsonObject data = Json.createObjectBuilder()
                    .add("estado", "success")
                    .add("mensaje", "Subcategorias por categoria_id")
                    .add("subcategoriasByCategoria", subcategoriasByCategoriaId).build();
            return data;
        }
        catch (NullPointerException ex){
            throw new EmpresaException("C-SUB04-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }
}
