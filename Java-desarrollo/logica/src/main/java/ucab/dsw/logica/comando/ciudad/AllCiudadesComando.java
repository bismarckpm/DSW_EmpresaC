package ucab.dsw.logica.comando.ciudad;

import ucab.dsw.dtos.ResponseDto;
import ucab.dsw.entidades.*;
import ucab.dsw.accesodatos.*;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.List;

public class AllCiudadesComando extends BaseComando {

    public JsonArrayBuilder ciudades= Json.createArrayBuilder();

    @Override
    public void execute() throws EmpresaException{

        try {

            DaoCiudad dao = Fabrica.crear(DaoCiudad.class);
            List<Ciudad> resultado = dao.findAll(Ciudad.class);


            for (Ciudad obj : resultado) {

                JsonObject ciudad = Json.createObjectBuilder().add("id", obj.get_id())
                        .add("nombre", obj.get_nombre())
                        .add("estado_id", obj.get_estado().get_id())
                        .add("pais_id", obj.get_estado().get_pais().get_id()).build();

                ciudades.add(ciudad);

            }
        }
        catch (NullPointerException ex){
            throw new EmpresaException("C-CI01-E-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }


    }

    @Override
    public JsonObject getResult() throws EmpresaException{
        try{
            ResponseDto responseDto =Fabrica.crear(ResponseDto.class);
            responseDto.mensaje="Todas las ciudades";
            responseDto.estado="success";
            responseDto.objeto=this.ciudades;


            JsonObject data= Json.createObjectBuilder().add("mensaje","Todas las ciudades")
                    .add("estado","success")
                    .add("ciudades",ciudades).build();

            return data;
        }
        catch (NullPointerException ex){
            throw new EmpresaException("C-CI01-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }
}
