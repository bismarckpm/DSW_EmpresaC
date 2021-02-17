package ucab.dsw.logica.comando.analista;

import ucab.dsw.accesodatos.DaoSolicitudEstudio;
import ucab.dsw.dtos.RespuestaAnalistaDto;
import ucab.dsw.entidades.SolicitudEstudio;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.Date;

public class ResponderEstudioComando extends BaseComando {

    public RespuestaAnalistaDto respuestaAnalistaDto;
    public String resultado;

    public ResponderEstudioComando(RespuestaAnalistaDto respuestaAnalistaDto) {
        this.respuestaAnalistaDto = respuestaAnalistaDto;
    }

    @Override
    public void execute() {
        DaoSolicitudEstudio dao = Fabrica.crear(DaoSolicitudEstudio.class);
        Date fecha=Fabrica.crear(Date.class);

        SolicitudEstudio solicitudEstudio = dao.find(respuestaAnalistaDto.getSolicituEstudioDto().getId(), SolicitudEstudio.class);

        solicitudEstudio.set_resultadoanalista(respuestaAnalistaDto.getRespueta());
        solicitudEstudio.set_fecha_fin(fecha);

        SolicitudEstudio resul = dao.update(solicitudEstudio);
        resultado=resul.get_resultadoanalista();

    }

    @Override
    public JsonObject getResult() throws EmpresaException {
        try {
            JsonObject data = Json.createObjectBuilder()
                    .add("estado", "success")
                    .add("mensaje", "Respuesta registrada")
                    .add("resultado", resultado).build();


            return data;
        }
        catch (NullPointerException ex){
            throw new EmpresaException("C-AN05-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }
}
