package ucab.dsw.logica.comando.admin;

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

public class ParticipacionEstudioComando extends BaseComando {

    public JsonArrayBuilder participantes= Json.createArrayBuilder();
    public long _id;

    public ParticipacionEstudioComando(long _id){
        this._id=_id;
    }

    @Override
    public void execute() throws EmpresaException{
        try{
            DaoParticipacion dao = new DaoParticipacion();
            DaoEncuestado daoEncuestado = new DaoEncuestado();
            List<Participacion> resultado = null;
            Class<Participacion> type = Participacion.class;

            resultado = dao.findAll(type);
            for (Participacion obj : resultado) {
                Participacion participacion = dao.find(obj.get_id(), Participacion.class);
                Encuestado encuestado = daoEncuestado.find(participacion.get_encuestado().get_id(),Encuestado.class);

                if (participacion.get_solicitudestudio().get_id() == _id) {

                    JsonObject p = Json.createObjectBuilder()
                            .add("id", participacion.get_id())
                            .add("Nombre", encuestado.get_nombre())
                            .add("Estado", participacion.get_estado()).build();

                    participantes.add(p);

                }
            }
        }
            catch (NullPointerException ex){
            throw new EmpresaException("C-ADM06-E-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }

    @Override
    public JsonObject getResult() throws EmpresaException{
        try{
            JsonObject data= Json.createObjectBuilder().add("mensaje","Todos los participantes")
                    .add("estado","success")
                    .add("Participantes",participantes).build();

            return data;
        }
            catch (NullPointerException ex){
            throw new EmpresaException("C-ADM06-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }

}