package ucab.dsw.logica.comando.analista;

import ucab.dsw.accesodatos.*;
import ucab.dsw.entidades.*;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.List;

public class RespuestasEstudioComando extends BaseComando {


    public long _id;
    public JsonArrayBuilder builder = Json.createArrayBuilder();

    public RespuestasEstudioComando(long _id) {
        this._id = _id;
    }

    @Override
    public void execute() throws EmpresaException {

        try {
            JsonArrayBuilder respuesta_opcion = Json.createArrayBuilder();
            JsonArrayBuilder respuesta_participacion = Json.createArrayBuilder();
            List<Respuesta> resultado;
            List<RespuestaOpcion> resultado2;
            List<Participacion> resultado3;

            DaoRespuesta dao = Fabrica.crear(DaoRespuesta.class);
            DaoRespuestaOpcion dao2 = Fabrica.crear(DaoRespuestaOpcion.class);
            DaoSolicitudEstudio dao3 = Fabrica.crear(DaoSolicitudEstudio.class);
            DaoParticipacion dao4 = Fabrica.crear(DaoParticipacion.class);
            DaoEncuestado dao5 = Fabrica.crear(DaoEncuestado.class);
            DaoUsuario dao6 = Fabrica.crear(DaoUsuario.class);

            SolicitudEstudio solicitudEstudio = dao3.find(_id, SolicitudEstudio.class);

            Class<Respuesta> type = Respuesta.class;
            Class<RespuestaOpcion> type2 = RespuestaOpcion.class;
            Class<Participacion> type3 = Participacion.class;

            resultado = dao.findAll(type);
            resultado2 = dao2.findAll(type2);
            resultado3 = dao4.findAll(type3);

            for (Participacion obj3 : resultado3) {
                Participacion participacion = dao4.find(obj3.get_id(), Participacion.class);
                if (participacion.get_solicitudestudio().get_id() == solicitudEstudio.get_id() && participacion.get_estado().equals("inactivo")) {
                    for (Respuesta obj : resultado) {
                        Respuesta respuesta = dao.find(obj.get_id(), Respuesta.class);
                        if (respuesta.get_participacion().get_id() == participacion.get_id()) {

                            if (respuesta.get_preguntaencuesta().get_pregunta().get_tipopregunta().equals("desarrollo")) {
                                respuesta_participacion.add(Json.createObjectBuilder()
                                        .add("pregunta", respuesta.get_preguntaencuesta().get_pregunta().get_descripcion())
                                        .add("tipo_pregunta", respuesta.get_preguntaencuesta().get_pregunta().get_tipopregunta())
                                        .add("respuesta", respuesta.get_respuestadesarrollo()));
                            }
                            if (respuesta.get_preguntaencuesta().get_pregunta().get_tipopregunta().equals("booleana")) {

                                String respuestaBooleana;
                                if (respuesta.get_respuestaboolean() == 1) {
                                    respuestaBooleana = "verdadero";
                                } else {
                                    respuestaBooleana = "falso";
                                }
                                respuesta_participacion.add(Json.createObjectBuilder()
                                        .add("pregunta", respuesta.get_preguntaencuesta().get_pregunta().get_descripcion())
                                        .add("tipo_pregunta", respuesta.get_preguntaencuesta().get_pregunta().get_tipopregunta())
                                        .add("respuesta", respuestaBooleana));
                            }
                            if (respuesta.get_preguntaencuesta().get_pregunta().get_tipopregunta().equals("Rango")) {
                                respuesta_participacion.add(Json.createObjectBuilder()
                                        .add("pregunta", respuesta.get_preguntaencuesta().get_pregunta().get_descripcion())
                                        .add("tipo_pregunta", respuesta.get_preguntaencuesta().get_pregunta().get_tipopregunta())
                                        .add("respuesta", respuesta.get_respuestarango()));
                            }

                            if (respuesta.get_preguntaencuesta().get_pregunta().get_tipopregunta().equals("Opcion simple") || respuesta.get_preguntaencuesta().get_pregunta().get_tipopregunta().equals("Opcion multiple")) {
                                for (RespuestaOpcion obj2 : resultado2) {
                                    RespuestaOpcion respuesta_Opcion = dao2.find(obj2.get_id(), RespuestaOpcion.class);
                                    if (respuesta.get_id() == respuesta_Opcion.get_respuesta().get_id()) {
                                        respuesta_opcion.add(Json.createObjectBuilder().add("respuestaOpcion", respuesta_Opcion.get_opcionsimplemultiple().get_opcionsimplemultiple().get_opcion()));
                                    }
                                }
                                respuesta_participacion.add(Json.createObjectBuilder()
                                        .add("pregunta", respuesta.get_preguntaencuesta().get_pregunta().get_descripcion())
                                        .add("tipo_pregunta", respuesta.get_preguntaencuesta().get_pregunta().get_tipopregunta())
                                        .add("respuesta", respuesta_opcion));
                            }
                        }


                    }


                    Encuestado encuestado = dao5.find(participacion.get_encuestado().get_id(), Encuestado.class);
                    Usuario usuario = dao6.find(encuestado.get_usuario_encuestado().get_id(), Usuario.class);

                    JsonObject p = Json.createObjectBuilder()
                            .add("participacion_id", participacion.get_id())
                            .add("usuario", usuario.get_usuario())
                            .add("encuestado", encuestado.get_nombre())
                            .add("respuestas", respuesta_participacion).build();

                    builder.add(p);

                }
            }
        }
        catch (NullPointerException ex){
            throw new EmpresaException("C-AN06-E-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }

    @Override
    public JsonObject getResult() throws EmpresaException {

        try {
            JsonObject data = Json.createObjectBuilder()
                    .add("estado", "success")
                    .add("mensaje", "Participaciones con respuestas de un estudio")
                    .add("participaciones", builder).build();


            return data;
        }
        catch (NullPointerException ex){
            throw new EmpresaException("C-AN06-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }
}
