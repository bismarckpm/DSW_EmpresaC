package ucab.dsw.logica.comando.encuestado;

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

public class EncuestaEstudioComando extends BaseComando {

    public JsonArrayBuilder opciones =Json.createArrayBuilder();
    public JsonArrayBuilder encuesta= Json.createArrayBuilder();
    public long _id;

    public EncuestaEstudioComando(long _id){
        this._id=_id;
    }

    @Override
    public void execute() throws EmpresaException{
        try{
            List<PreguntaEncuesta> resultado = null;
            List<OpcionSimpleMultiplePregunta> resultado2 = null;

            DaoPreguntaEncuesta dao = new DaoPreguntaEncuesta();
            DaoSolicitudEstudio dao2 = new DaoSolicitudEstudio();
            DaoOpcionSimpleMultiplePregunta dao3 = new DaoOpcionSimpleMultiplePregunta();

            SolicitudEstudio solicitudEstudio = dao2.find(_id, SolicitudEstudio.class);

            Class<PreguntaEncuesta> type = PreguntaEncuesta.class;

            resultado = dao.findAll(type);

            Class<OpcionSimpleMultiplePregunta> type2 = OpcionSimpleMultiplePregunta.class;

            resultado2 = dao3.findAll(type2);
            for (PreguntaEncuesta obj : resultado) {
                PreguntaEncuesta preguntaEncuesta = dao.find(obj.get_id(), PreguntaEncuesta.class);

                if (preguntaEncuesta.get_encuesta().get_id() == solicitudEstudio.get_encuesta().get_id()) {


                    if (preguntaEncuesta.get_pregunta().get_tipopregunta().equals("Opcion simple") || preguntaEncuesta.get_pregunta().get_tipopregunta().equals("Opcion multiple")) {
                        for (OpcionSimpleMultiplePregunta obj2 : resultado2) {
                            OpcionSimpleMultiplePregunta opcion = dao3.find(obj2.get_id(), OpcionSimpleMultiplePregunta.class);
                            if (opcion.get_pregunta().get_id() == preguntaEncuesta.get_pregunta().get_id()) {

                                opciones.add(Json.createObjectBuilder().add("id", opcion.get_id())
                                        .add("opcion", opcion.get_opcionsimplemultiple().get_opcion()));
                            }


                        }
                        JsonObject preguntas = Json.createObjectBuilder().add("id", preguntaEncuesta.get_id())
                                .add("descripcion", preguntaEncuesta.get_pregunta().get_descripcion())
                                .add("tipopregunta", preguntaEncuesta.get_pregunta().get_tipopregunta())
                                .add("opciones", opciones)
                                .build();

                        encuesta.add(preguntas);
                    } else {

                        if (preguntaEncuesta.get_pregunta().get_valormax() != 0) {
                            JsonObject preguntas = Json.createObjectBuilder().add("id", preguntaEncuesta.get_id())
                                    .add("descripcion", preguntaEncuesta.get_pregunta().get_descripcion())
                                    .add("tipopregunta", preguntaEncuesta.get_pregunta().get_tipopregunta())
                                    .add("minimo", preguntaEncuesta.get_pregunta().get_valormin())
                                    .add("maximo", preguntaEncuesta.get_pregunta().get_valormax()).build();
                            encuesta.add(preguntas);

                        } else {
                            JsonObject preguntas = Json.createObjectBuilder().add("id", preguntaEncuesta.get_id())
                                    .add("descripcion", preguntaEncuesta.get_pregunta().get_descripcion())
                                    .add("tipopregunta", preguntaEncuesta.get_pregunta().get_tipopregunta())
                                    .build();

                            encuesta.add(preguntas);
                        }

                    }

                }
            }
        }
        catch (NullPointerException ex){
            throw new EmpresaException("C-ENC02-E-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }

    }

    @Override
    public JsonObject getResult() throws EmpresaException{
        try{
            JsonObject data= Json.createObjectBuilder().add("mensaje","Preguntas")
                    .add("estado","success")
                    .add("Preguntas",encuesta).build();

            return data;
    }
        catch (NullPointerException ex){
        throw new EmpresaException("C-ENC02-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
    }
}

}
