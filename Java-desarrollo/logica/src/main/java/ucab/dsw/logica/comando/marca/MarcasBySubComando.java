package ucab.dsw.logica.comando.marca;

import ucab.dsw.accesodatos.DaoMarca;
import ucab.dsw.entidades.Marca;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.List;

public class MarcasBySubComando extends BaseComando {

    public long _id;
    public JsonArrayBuilder marcasByCategoriaIdJson= Json.createArrayBuilder();

    public MarcasBySubComando(long _id) {
        this._id = _id;
    }

    @Override
    public void execute() throws EmpresaException {

        try {
            DaoMarca dao = Fabrica.crear(DaoMarca.class);
            List<Marca> resultado = dao.getMarcasBySubcategoria(_id);

            for (Marca obj : resultado) {

                JsonObject marca = Json.createObjectBuilder().add("id", obj.get_id())
                        .add("nombre", obj.get_nombre()).build();

                marcasByCategoriaIdJson.add(marca);

            }
        }
        catch (NullPointerException ex){
            throw new EmpresaException("C-MA06-E-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }


    }

    @Override
    public JsonObject getResult() throws EmpresaException{

        try {
            JsonObject data = Json.createObjectBuilder()
                    .add("estado", "success")
                    .add("mensaje", "Marcas por subcategoria")
                    .add("marcasBySubcategoria", marcasByCategoriaIdJson).build();

            return data;
        }
        catch (NullPointerException ex){
            throw new EmpresaException("C-MA06-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }
}
