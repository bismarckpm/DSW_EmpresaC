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

public class RespuestasPorcentajeComando extends BaseComando {

    public long _id;
    public JsonArrayBuilder builder = Json.createArrayBuilder();

    public RespuestasPorcentajeComando(long _id) {
        this._id = _id;
    }

    @Override
    public void execute() throws EmpresaException {

        try {
            JsonArrayBuilder respuesta_opcion = Json.createArrayBuilder();
            List<Respuesta> resultado;
            List<RespuestaOpcion> resultado2;
            List<PreguntaEncuesta> resultado3;
            List<OpcionSimpleMultiplePregunta> resultado4;

            DaoRespuesta dao = Fabrica.crear(DaoRespuesta.class);
            DaoRespuestaOpcion dao2 = Fabrica.crear(DaoRespuestaOpcion.class);
            DaoSolicitudEstudio dao3 = Fabrica.crear(DaoSolicitudEstudio.class);
            DaoPreguntaEncuesta dao4 = Fabrica.crear(DaoPreguntaEncuesta.class);
            DaoOpcionSimpleMultiplePregunta dao5 = Fabrica.crear(DaoOpcionSimpleMultiplePregunta.class);

            SolicitudEstudio solicitudEstudio = dao3.find(_id, SolicitudEstudio.class);
            Class<Respuesta> type = Respuesta.class;
            Class<RespuestaOpcion> type2 = RespuestaOpcion.class;
            Class<PreguntaEncuesta> type3 = PreguntaEncuesta.class;
            Class<OpcionSimpleMultiplePregunta> type4 = OpcionSimpleMultiplePregunta.class;

            resultado = dao.findAll(type);
            resultado2 = dao2.findAll(type2);
            resultado3 = dao4.findAll(type3);
            resultado4 = dao5.findAll(type4);

            for (PreguntaEncuesta obj : resultado3) {
                PreguntaEncuesta preguntaEncuesta = dao4.find(obj.get_id(), PreguntaEncuesta.class);

                if (solicitudEstudio.get_encuesta().get_id() == preguntaEncuesta.get_encuesta().get_id()) {


                    if (preguntaEncuesta.get_pregunta().get_tipopregunta().equals("booleana")) {
                        int si = 0;
                        int no = 0;
                        int cont = 0;
                        for (Respuesta obj2 : resultado) {
                            Respuesta respuesta = dao.find(obj2.get_id(), Respuesta.class);
                            if (respuesta.get_preguntaencuesta().get_id() == preguntaEncuesta.get_id() && respuesta.get_participacion().get_solicitudestudio().get_id() == solicitudEstudio.get_id()) {
                                if (respuesta.get_respuestaboolean() == 0) {
                                    no = no + 1;
                                } else {
                                    si = si + 1;
                                }
                                cont = cont + 1;
                            }


                        }

                        respuesta_opcion.add(Json.createArrayBuilder().add("si").add(si));
                        respuesta_opcion.add(Json.createArrayBuilder().add("no").add(no));

                        JsonObject p = Json.createObjectBuilder()
                                .add("pregunta", preguntaEncuesta.get_pregunta().get_descripcion())
                                .add("tipo_pregunta", preguntaEncuesta.get_pregunta().get_tipopregunta())
                                .add("resultado", respuesta_opcion).build();
                        builder.add(p);
                    }

                    if (preguntaEncuesta.get_pregunta().get_tipopregunta().equals("Rango")) {
                        int cont = 0;
                        int valor = preguntaEncuesta.get_pregunta().get_valormax();


                        for (Respuesta obj2 : resultado) {
                            Respuesta respuesta = dao.find(obj2.get_id(), Respuesta.class);
                            if (respuesta.get_preguntaencuesta().get_id() == preguntaEncuesta.get_id() && respuesta.get_participacion().get_solicitudestudio().get_id() == solicitudEstudio.get_id()) {
                                cont = cont + 1;
                            }
                        }
                        if (cont != 0) {
                            while (valor > 0) {
                                int rango = 0;
                                for (Respuesta obj2 : resultado) {
                                    Respuesta respuesta = dao.find(obj2.get_id(), Respuesta.class);
                                    if (respuesta.get_preguntaencuesta().get_id() == preguntaEncuesta.get_id() && respuesta.get_participacion().get_solicitudestudio().get_id() == solicitudEstudio.get_id()) {
                                        if (respuesta.get_respuestarango() == valor) {
                                            rango = rango + 1;
                                        }
                                    }
                                }
                                respuesta_opcion.add(Json.createArrayBuilder().add("valor" + valor).add(rango));
                                valor = valor - 1;
                            }
                        }

                        JsonObject p = Json.createObjectBuilder()
                                .add("pregunta", preguntaEncuesta.get_pregunta().get_descripcion())
                                .add("tipo_pregunta", preguntaEncuesta.get_pregunta().get_tipopregunta())
                                .add("resultado", respuesta_opcion).build();
                        builder.add(p);
                    }

                    if (preguntaEncuesta.get_pregunta().get_tipopregunta().equals("Opcion simple") || preguntaEncuesta.get_pregunta().get_tipopregunta().equals("Opcion multiple")) {
                        int cont = 0;
                        int cont2 = 0;
                        for (RespuestaOpcion obj4 : resultado2) {
                            RespuestaOpcion respuestaOpcion = dao2.find(obj4.get_id(), RespuestaOpcion.class);
                            if (respuestaOpcion.get_respuesta().get_preguntaencuesta().get_id() == preguntaEncuesta.get_id() && respuestaOpcion.get_respuesta().get_participacion().get_solicitudestudio().get_id() == solicitudEstudio.get_id()) {
                                cont = cont + 1;
                            }
                        }
                        if (cont != 0) {
                            for (OpcionSimpleMultiplePregunta obj3 : resultado4) {
                                int opcion = 0;
                                OpcionSimpleMultiplePregunta opcion_simple_multiple_pregunta = dao5.find(obj3.get_id(), OpcionSimpleMultiplePregunta.class);
                                if (opcion_simple_multiple_pregunta.get_pregunta().get_id() == preguntaEncuesta.get_pregunta().get_id()) {

                                    for (RespuestaOpcion obj4 : resultado2) {
                                        RespuestaOpcion respuestaOpcion = dao2.find(obj4.get_id(), RespuestaOpcion.class);
                                        if (respuestaOpcion.get_opcionsimplemultiple().get_id() == opcion_simple_multiple_pregunta.get_id() && respuestaOpcion.get_respuesta().get_participacion().get_solicitudestudio().get_id() == solicitudEstudio.get_id()) {
                                            opcion = opcion + 1;
                                        }
                                    }

                                    cont2 = cont2 + 1;
                                    respuesta_opcion.add(Json.createArrayBuilder().add(opcion_simple_multiple_pregunta.get_opcionsimplemultiple().get_opcion()).add(opcion));

                                }
                            }

                        }


                        JsonObject p = Json.createObjectBuilder()
                                .add("pregunta", preguntaEncuesta.get_pregunta().get_descripcion())
                                .add("tipo_pregunta", preguntaEncuesta.get_pregunta().get_tipopregunta())
                                .add("resultado", respuesta_opcion).build();
                        builder.add(p);
                    }
                }
            }

        }
        catch (NullPointerException ex){
            throw new EmpresaException("C-AN07-E-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }

    }

    @Override
    public JsonObject getResult() throws EmpresaException {

        try {
            JsonObject data = Json.createObjectBuilder()
                    .add("estado", "success")
                    .add("mensaje", "Porcentaje de cada pregunta del estudio")
                    .add("Preguntas", builder).build();


            return data;
        }
        catch (NullPointerException ex){
            throw new EmpresaException("C-AN07-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }
}
