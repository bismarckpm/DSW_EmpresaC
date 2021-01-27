package ucab.dsw.logica.comando.encuestado;

import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.*;
import ucab.dsw.entidades.*;
import ucab.dsw.excepciones.PruebaExcepcion;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;

import ucab.dsw.mappers.*;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.List;

public class AddRespuestaComando extends BaseComando {
    public long _id;
    public long _id2;
    public long _id3;
    public RespuestaDto respuestaDto;
    public Respuesta_Opcion respuesta_opcion;

    public AddRespuestaComando(long _id,long _id2,long _id3,RespuestaDto respuestaDto) {
        this._id = _id;
        this._id2 = _id2;
        this._id3 = _id3;
        this.respuestaDto = respuestaDto;
    }

    @Override
    public void execute() {
        try{
            DaoRespuesta daoRespuesta = new DaoRespuesta();
            DaoPreguntaEncuesta daoPreguntaEncuesta= new DaoPreguntaEncuesta();
            DaoParticipacion daoParticipacion = new DaoParticipacion();
            DaoOpcionSimpleMultiplePregunta daoOpcion_simple_multiple_pregunta= new DaoOpcionSimpleMultiplePregunta();
            DaoRespuestaOpcion daoRespuestaOpcion= new DaoRespuestaOpcion();

            List<Participacion> participacion = null;
            Class<Participacion> type = Participacion.class;
            participacion = daoParticipacion.findAll(type);

            Respuesta respuesta = new Respuesta();
            PreguntaEncuesta pregunta = daoPreguntaEncuesta.find(_id,PreguntaEncuesta.class);
            respuesta.set_preguntaencuesta(pregunta);

            for (Participacion obj : participacion) {
                if (obj.get_solicitudestudio().get_id()==_id2 && obj.get_encuestado().get_id()==_id3) {
                    Participacion participacion1 = daoParticipacion.find(obj.get_id(), Participacion.class);
                    respuesta.set_participacion(participacion1);
                }
            }

            respuesta.set_respuestarango(respuestaDto.getRespuestarango());
            respuesta.set_respuestadesarrollo(respuestaDto.getRespuestadesarrollo());
            respuesta.set_respuestaboolean(respuestaDto.getRespuestaboolean());



            Respuesta resul = daoRespuesta.insert( respuesta);
            this.respuestaDto= RespuestaMapper.mapEntityToDto(resul);

            if (respuestaDto.getOpciones()!=null) {
                List<OpcionSimpleMultiplePreguntaDto> opciones = respuestaDto.getOpciones();
                for (OpcionSimpleMultiplePreguntaDto obj : opciones) {

                    RespuestaOpcion respuestaOpcion = new RespuestaOpcion();
                    respuestaOpcion.set_respuesta(resul);
                    OpcionSimpleMultiplePregunta opcion =daoOpcion_simple_multiple_pregunta.find(obj.getId(), OpcionSimpleMultiplePregunta.class);
                    respuestaOpcion.set_opcionsimplemultiple(opcion);

                    RespuestaOpcion resul2 = daoRespuestaOpcion.insert(respuestaOpcion);
                    this.respuesta_opcion= RespuestaOpcionMapper.mapEntityToDto(resul2);


                }
            }
        }catch (PruebaExcepcion pruebaExcepcion) {
            pruebaExcepcion.printStackTrace();
        }

    }

    @Override
    public JsonObject getResult() {
        ResponseDto responseDto = Fabrica.crear(ResponseDto.class);
        responseDto.mensaje="Pregunta Respondida";
        responseDto.estado="success";
        responseDto.objeto=this.respuestaDto.getId();

        JsonObject data= Json.createObjectBuilder()
                .add("estado","success")
                .add("mensaje","Pregunta Respondida")
                .add("respuesta",this.respuestaDto.getId()).build();

        return data;
    }
}