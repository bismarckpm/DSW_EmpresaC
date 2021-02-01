package ucab.dsw.logica.comando.encuestado;

import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.ResponseDto;
import ucab.dsw.entidades.*;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.List;

public class PreguntaEstudioComando extends BaseComando {
    public JsonArrayBuilder opciones =Json.createArrayBuilder();
    public JsonArrayBuilder pregunta_estudio= Json.createArrayBuilder();
    public long _id;
    public long _id2;

    public PreguntaEstudioComando(long _id,long _id2){
        this._id=_id;
        this._id2=_id2;
    }

    @Override
    public void execute() {
        DaoPreguntaEncuesta dao = new DaoPreguntaEncuesta();
        DaoSolicitudEstudio dao2 = new DaoSolicitudEstudio();
        DaoOpcionSimpleMultiplePregunta dao3 = new DaoOpcionSimpleMultiplePregunta();
        DaoParticipacion daoParticipacion = new DaoParticipacion();
        DaoRespuesta daoRespuesta = new DaoRespuesta();
        DaoPregunta daoPregunta = new DaoPregunta();

        SolicitudEstudio solicitudEstudio = new SolicitudEstudio();
        solicitudEstudio = dao2.find(_id,SolicitudEstudio.class);

        List<PreguntaEncuesta> resultado = null;
        List<OpcionSimpleMultiplePregunta> resultado2 = null;

        Class<PreguntaEncuesta> type = PreguntaEncuesta.class;
        resultado = dao.findAll(type);

        Class<OpcionSimpleMultiplePregunta> type2 = OpcionSimpleMultiplePregunta.class;
        resultado2 = dao3.findAll(type2);

        List<Participacion> participacion = null;
        Class<Participacion> type3 = Participacion.class;
        participacion = daoParticipacion.findAll(type3);

        List<Respuesta> respuesta = null;
        Class<Respuesta> type4 = Respuesta.class;
        respuesta = daoRespuesta.findAll(type4);
        Participacion participacion1 = new Participacion();

        for (Participacion obj : participacion) {
            if (obj.get_solicitudestudio().get_id()==_id && obj.get_encuestado().get_id()==_id2) {
                participacion1 = daoParticipacion.find(obj.get_id(), Participacion.class);
            }
        }

        for (PreguntaEncuesta obj : resultado) {
            PreguntaEncuesta preguntaEncuesta = dao.find(obj.get_id(), PreguntaEncuesta.class);
            Pregunta pregunta1 = daoPregunta.find(preguntaEncuesta.get_pregunta().get_id(),Pregunta.class);
            int pregunta = 0;
            if (preguntaEncuesta.get_encuesta().get_id() == solicitudEstudio.get_encuesta().get_id()) {
                for (Respuesta obj3 : respuesta) {

                    if (obj3.get_preguntaencuesta().get_id() == preguntaEncuesta.get_id() && obj3.get_participacion().get_id()==participacion1.get_id()) {
                        pregunta=1;
                    }
                }
                if(pregunta==0) {
                    if (preguntaEncuesta.get_pregunta().get_tipopregunta().equals("Opcion simple") || preguntaEncuesta.get_pregunta().get_tipopregunta().equals("Opcion multiple")) {

                        for (OpcionSimpleMultiplePregunta obj2 : resultado2) {
                            OpcionSimpleMultiplePregunta opcion = dao3.find(obj2.get_id(), OpcionSimpleMultiplePregunta.class);
                            if (opcion.get_pregunta().get_id() == preguntaEncuesta.get_pregunta().get_id()) {

                                opciones.add(Json.createObjectBuilder().add("id", opcion.get_id())
                                        .add("opcion", opcion.get_opcionsimplemultiple().get_opcion()));

                            }
                        }
                        JsonObject preguntas = Json.createObjectBuilder().add("id", preguntaEncuesta.get_id())
                                .add("descripcion", pregunta1.get_descripcion())
                                .add("tipopregunta", pregunta1.get_tipopregunta())
                                .add("opciones", opciones)
                                .build();

                        pregunta_estudio.add(preguntas);
                    } else {

                        if (preguntaEncuesta.get_pregunta().get_valormax() != 0) {
                            JsonObject preguntas = Json.createObjectBuilder().add("id", preguntaEncuesta.get_id())
                                    .add("descripcion", pregunta1.get_descripcion())
                                    .add("tipopregunta", pregunta1.get_tipopregunta())
                                    .add("minimo", pregunta1.get_valormin())
                                    .add("maximo", pregunta1.get_valormax()).build();
                            pregunta_estudio.add(preguntas);

                        } else {
                            JsonObject preguntas = Json.createObjectBuilder().add("id", preguntaEncuesta.get_id())
                                    .add("descripcion", pregunta1.get_descripcion())
                                    .add("tipopregunta", pregunta1.get_tipopregunta())
                                    .build();

                            pregunta_estudio.add(preguntas);
                        }
                    }
                }
            }
        }

    }

    @Override
    public JsonObject getResult() {
        ResponseDto responseDto = Fabrica.crear(ResponseDto.class);
        responseDto.mensaje="Preguntas del Estudio";
        responseDto.estado="success";
        responseDto.objeto=this.pregunta_estudio;


        JsonObject data= Json.createObjectBuilder().add("mensaje","Preguntas del Estudio")
                .add("estado","success")
                .add("Preguntas",pregunta_estudio).build();

        return data;
    }

}
