package ucab.dsw.logica.comando.analista;

import ucab.dsw.accesodatos.DaoSolicitudEstudio;
import ucab.dsw.dtos.SolicitudEstudioDto;
import ucab.dsw.entidades.SolicitudEstudio;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;

import javax.json.Json;
import javax.json.JsonObject;

public class EmpezarEstudioComando extends BaseComando {

    public long _id;
    public String estado;

    public EmpezarEstudioComando(long _id) {
        this._id = _id;
    }

    @Override
    public void execute() {
        DaoSolicitudEstudio dao = Fabrica.crear(DaoSolicitudEstudio.class);
        SolicitudEstudio solicitudEstudio = dao.find(_id,SolicitudEstudio.class);
        solicitudEstudio.set_estado( "en progreso" );
        SolicitudEstudio resul = dao.update(solicitudEstudio);
        estado=resul.get_estado();
    }

    @Override
    public JsonObject getResult() throws EmpresaException {

        try {
            JsonObject data = Json.createObjectBuilder()
                    .add("estado", "success")
                    .add("mensaje", "El estudio ha empezado")
                    .add("estado_estudio", estado).build();

            return data;
        }
        catch (NullPointerException ex){
            throw new EmpresaException("C-AN02-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }
}
