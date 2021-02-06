package ucab.dsw.logica.comando.presentacion;

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

public class AllPresentacionComando extends BaseComando {
    public JsonArrayBuilder presentaciones= Json.createArrayBuilder();

    @Override
    public void execute() throws EmpresaException {

        try{

            DaoPresentacion dao= Fabrica.crear(DaoPresentacion.class);
            DaoTipo daoTipo= Fabrica.crear(DaoTipo.class);

            List<Presentacion> resultado= dao.findAll(Presentacion.class);

            for(Presentacion obj: resultado) {

                Tipo tipo=daoTipo.find(obj.get_tipo().get_id(),Tipo.class);

                JsonObject p = Json.createObjectBuilder().add("id",obj.get_id())
                        .add("nombre",obj.get_nombre())
                        .add("tipo",tipo.get_nombre())
                        .add("tipo_id",tipo.get_id())
                        .add("estado",obj.get_estado()).build();

                presentaciones.add(p);

            }
        }
        catch (NullPointerException ex){
            throw new EmpresaException("C-PRE02-E-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }

    }

    @Override
    public JsonObject getResult() throws EmpresaException {

        try{
            ResponseDto responseDto =Fabrica.crear(ResponseDto.class);
            responseDto.mensaje="Todas las presentaciones";
            responseDto.estado="success";
            responseDto.objeto=this.presentaciones;


            JsonObject data= Json.createObjectBuilder().add("mensaje","Todas las presentaciones")
                    .add("estado","success")
                    .add("presentaciones",presentaciones).build();

            return data;
        }
        catch (NullPointerException ex){
            throw new EmpresaException("C-PRE02-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }
}
