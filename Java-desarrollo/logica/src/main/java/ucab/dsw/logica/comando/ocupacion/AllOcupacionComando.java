package ucab.dsw.logica.comando.ocupacion;

import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.ResponseDto;
import ucab.dsw.entidades.*;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.List;

public class AllOcupacionComando extends BaseComando {
    public JsonArrayBuilder ocupaciones= Json.createArrayBuilder();

    @Override
    public void execute() throws EmpresaException {

        try{

            DaoOcupacion dao= Fabrica.crear(DaoOcupacion.class);
            List<Ocupacion> resultado = dao.findAll(Ocupacion.class);

            for (Ocupacion obj : resultado) {

                JsonObject ocupacion = Json.createObjectBuilder().add("id", obj.get_id())
                        .add("nombre", obj.get_nombre()).build();

                ocupaciones.add(ocupacion);
            }
        }
        catch (NullPointerException ex){
            throw new EmpresaException("C-OC01-E-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }

    }

    @Override
    public JsonObject getResult() throws EmpresaException {

        try{
            ResponseDto responseDto =Fabrica.crear(ResponseDto.class);
            responseDto.mensaje="Todas las ocupaciones";
            responseDto.estado="success";
            responseDto.objeto=this.ocupaciones;


            JsonObject data= Json.createObjectBuilder().add("mensaje","Todas las ocupaciones")
                    .add("estado","success")
                    .add("ocupaciones",ocupaciones).build();

            return data;
        }
        catch (NullPointerException ex){
            throw new EmpresaException("C-OC01-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }

    }

}
