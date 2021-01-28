package ucab.dsw.logica.comando.analista;

import ucab.dsw.accesodatos.DaoParticipacion;
import ucab.dsw.entidades.Participacion;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;

import javax.json.Json;
import javax.json.JsonObject;

public class DeleteParticipacionComando extends BaseComando {

    public long _id;
    public String estado;

    public DeleteParticipacionComando(long _id) {
        this._id = _id;
    }

    @Override
    public void execute() {
        DaoParticipacion dao = Fabrica.crear(DaoParticipacion.class);
        Participacion participacion = dao.find(_id,Participacion.class);
        participacion.set_estado("inactivo");
        Participacion resul = dao.update(participacion);
        estado=resul.get_estado();
    }

    @Override
    public JsonObject getResult() {
       JsonObject data= Json.createObjectBuilder()
                .add("estado","success")
                .add("mensaje","Participacion finalizada en el estudio")
                .add("estado_participacion", estado).build();

       return data;
    }
}
