package ucab.dsw.logica.comando.MetodoConexion;

import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.ResponseDto;
import ucab.dsw.entidades.*;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.List;

public class AllMetodoConexionComando extends BaseComando {

    public JsonArrayBuilder metodos= Json.createArrayBuilder();

    @Override
    public void execute() {

        DaoMetodoConexion dao= Fabrica.crear(DaoMetodoConexion.class);
        List<MetodoConexion> resultado = dao.findAll(MetodoConexion.class);

        for (MetodoConexion obj : resultado) {

            JsonObject metodosConexion = Json.createObjectBuilder().add("id", obj.get_id())
                    .add("nombre", obj.get_nombre()).build();

            metodos.add(metodosConexion);
        }

    }

    @Override
    public JsonObject getResult() {
        ResponseDto responseDto =Fabrica.crear(ResponseDto.class);
        responseDto.mensaje="Todos los metodos de conexion";
        responseDto.estado="success";
        responseDto.objeto=this.metodos;


        JsonObject data= Json.createObjectBuilder().add("mensaje","Todos los metodos de conexion")
                .add("estado","success")
                .add("metodos_conexion",metodos).build();

        return data;
    }
}
