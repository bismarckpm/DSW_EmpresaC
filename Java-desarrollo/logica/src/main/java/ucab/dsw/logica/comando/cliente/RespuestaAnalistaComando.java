package ucab.dsw.logica.comando.cliente;

import ucab.dsw.accesodatos.DaoSolicitudEstudio;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.excepciones.PruebaExcepcion;
import ucab.dsw.dtos.ResponseDto;
import ucab.dsw.entidades.SolicitudEstudio;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

public class RespuestaAnalistaComando extends BaseComando {

    public JsonArrayBuilder respuesta= Json.createArrayBuilder();
    public long _id;

    public RespuestaAnalistaComando(long _id){
        this._id=_id;
    }

    @Override
    public void execute() throws EmpresaException{
        try{
            DaoSolicitudEstudio daoSolicitudEstudio = new DaoSolicitudEstudio();
            SolicitudEstudio solicitudEstudio = daoSolicitudEstudio.find(_id, SolicitudEstudio.class);

            if (solicitudEstudio.get_resultadoanalista() != null) {
                JsonObject p = Json.createObjectBuilder().add("resultado", solicitudEstudio.get_resultadoanalista())
                        .build();
                respuesta.add(p);
            }
    }
        catch (NullPointerException ex){
        throw new EmpresaException("C-CLI03-E-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
    }

}
    @Override
    public JsonObject getResult() throws EmpresaException{
        try{
            JsonObject data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("mensaje","Respuesta consultada")
                    .add("Preguntas",respuesta).build();

            return data;
        }
        catch (NullPointerException ex){
            throw new EmpresaException("C-CLI03-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }

}