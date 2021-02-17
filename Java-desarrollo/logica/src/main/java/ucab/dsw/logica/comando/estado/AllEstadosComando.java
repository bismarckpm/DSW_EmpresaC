package ucab.dsw.logica.comando.estado;

import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.entidades.*;
import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.ResponseDto;
import ucab.dsw.logica.fabrica.Fabrica;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.List;

public class AllEstadosComando extends BaseComando {

    public JsonArrayBuilder estados= Json.createArrayBuilder();

    @Override
    public void execute() throws EmpresaException {

        try{
            DaoEstado dao= Fabrica.crear(DaoEstado.class);
            List<Estado> resultado= dao.findAll(Estado.class);

            for(Estado obj: resultado){

                JsonObject estado = Json.createObjectBuilder().add("id",obj.get_id())
                    .add("nombre",obj.get_nombre())
                    .add("pais_id",obj.get_pais().get_id()).build();

                estados.add(estado);
            }
        }
        catch (NullPointerException ex){
            throw new EmpresaException("C-ED01-E-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }

    }

    @Override
    public JsonObject getResult() throws EmpresaException {
        try{
            ResponseDto responseDto =Fabrica.crear(ResponseDto.class);
            responseDto.mensaje="Todos los estados";
            responseDto.estado="success";
            responseDto.objeto=this.estados;


            JsonObject data= Json.createObjectBuilder().add("mensaje","Todos los estados")
                .add("estado","success")
                .add("estados",estados).build();

            return data;
        }
        catch (NullPointerException ex){
            throw new EmpresaException("C-ED01-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }
}
