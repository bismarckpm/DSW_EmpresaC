package ucab.dsw.logica.comando.pais;

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

public class AllPaisComando extends BaseComando {

    public JsonArrayBuilder paises= Json.createArrayBuilder();

    @Override
    public void execute() throws EmpresaException {
        try{
            DaoPais dao= Fabrica.crear(DaoPais.class);
            List<Pais> resultado= dao.findAll(Pais.class);

            for(Pais obj: resultado){

                JsonObject pais = Json.createObjectBuilder().add("id",obj.get_id())
                        .add("nombre",obj.get_nombre()).build();

                paises.add(pais);

            }
        }
        catch (NullPointerException ex){
            throw new EmpresaException("C-PA01-E-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }

    }

    @Override
    public JsonObject getResult() throws EmpresaException {
        try{
            ResponseDto responseDto =Fabrica.crear(ResponseDto.class);
            responseDto.mensaje="Todos los paises";
            responseDto.estado="success";
            responseDto.objeto=this.paises;


            JsonObject data= Json.createObjectBuilder().add("mensaje","Todos los paises")
                    .add("estado","success")
                    .add("paises",paises).build();

        return data;
        }
        catch (NullPointerException ex){
            throw new EmpresaException("C-PA01-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }
}
