package ucab.dsw.logica.comando.cliente;

import ucab.dsw.accesodatos.DaoSolicitudEstudio;
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
    public void execute() {

        DaoSolicitudEstudio daoSolicitudEstudio = new DaoSolicitudEstudio();
        SolicitudEstudio solicitudEstudio = daoSolicitudEstudio.find(_id, SolicitudEstudio.class);

        if (solicitudEstudio.get_resultadoanalista() != null) {
            JsonObject p = Json.createObjectBuilder().add("resultado", solicitudEstudio.get_resultadoanalista())
                    .build();
            respuesta.add(p);
        }


    }

    @Override
    public JsonObject getResult() {
        ResponseDto responseDto = Fabrica.crear(ResponseDto.class);
        responseDto.mensaje="Respuesta consultada";
        responseDto.estado="success";
        responseDto.objeto=this.respuesta;

        JsonObject data= Json.createObjectBuilder()
                .add("estado","success")
                .add("mensaje","Respuesta consultada")
                .add("respuesta",respuesta).build();

        return data;
    }
}
