package ucab.dsw.logica.comando.categoria;

import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.ResponseDto;
import ucab.dsw.entidades.*;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

public class AllCategorialComando extends BaseComando {

    public JsonArrayBuilder categorias= Json.createArrayBuilder();

    @Override
    public void execute() {

        DaoCategoria dao= Fabrica.crear(DaoCategoria.class);
        List<Categoria> resultado= dao.findAll(Categoria.class);


        for(Categoria obj: resultado){

            JsonObject categoria2 = Json.createObjectBuilder().add("id",obj.get_id())
                                                              .add("nombre",obj.get_nombre())
                                                              .add("estado",obj.get_estado()).build();

            categorias.add(categoria2);
        }


    }

    @Override
    public JsonObject getResult() {
        ResponseDto responseDto =Fabrica.crear(ResponseDto.class);
        responseDto.mensaje="Todas las categorias";
        responseDto.estado="success";
        responseDto.objeto=this.categorias;


        JsonObject data= Json.createObjectBuilder().add("mensaje","Todas las categorias")
                                                    .add("estado","success")
                                                    .add("categorias",categorias).build();

        return data;
    }

}
