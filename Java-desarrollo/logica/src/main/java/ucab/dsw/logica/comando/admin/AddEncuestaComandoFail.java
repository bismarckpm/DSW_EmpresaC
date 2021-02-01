package ucab.dsw.logica.comando.admin;

import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.*;
import ucab.dsw.entidades.*;
import ucab.dsw.excepciones.PruebaExcepcion;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import ucab.dsw.mappers.EncuestaMapper;
import ucab.dsw.mappers.ParticipacionMapper;
import ucab.dsw.mappers.PreguntaEncuestaMapper;
import ucab.dsw.mappers.SolicitudMapper;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.List;

public class AddEncuestaComandoFail extends BaseComando {
    public long _id;
    public long _id2;
    public EncuestaDto encuestaDto;
    public ParticipacionDto participacionDto;
    public PreguntaEncuestaDto preguntaEncuestaDto;
    public SolicitudEstudioDto solicitudEstudioDto;


    public AddEncuestaComandoFail(long _id, long _id2, EncuestaDto encuestaDto) {
        this._id = _id;
        this._id2 = _id2;
        this.encuestaDto = encuestaDto;
    }

    @Override
    public void execute() {
        try{

            DaoEncuesta dao = new DaoEncuesta();
            DaoPreguntaEncuesta dao2 = new DaoPreguntaEncuesta();
            DaoSolicitudEstudio dao3 = new DaoSolicitudEstudio();
            DaoEncuestado dao4 = new DaoEncuestado();
            DaoParticipacion dao5 = new DaoParticipacion();
            DaoPregunta dao6 = new DaoPregunta();
            DaoUsuario daoUsuario = new DaoUsuario();

            Encuesta encuesta = new Encuesta();
            encuesta.set_nombre(encuestaDto.getNombre());
            encuesta.set_estado("activo");

            Marca marca = new Marca(_id);
            encuesta.set_marca(marca);

            Encuesta resul = dao.insert(encuesta);
            this.encuestaDto= EncuestaMapper.mapEntityToDto(resul);

            SolicitudEstudio solicitudEstudio = dao3.find(_id2, SolicitudEstudio.class);
            //PARA PRODUCCIÓN
            /*List<Usuario> analista= daoUsuario.getAnalistas();
            analista_random=(int)(Math.random()* analista.size());
            System.out.println("analista random");
            System.out.println(analista_random);
            analista_elegido=analista.get(analista_random);*/
            //PARA DESARROLLO

            Usuario analista = new Usuario(15);
            analista = daoUsuario.find(analista.get_id(), Usuario.class);

            solicitudEstudio.set_usuario(analista);
            solicitudEstudio.set_estado("pendiente");
            solicitudEstudio.set_encuesta(resul);

            SolicitudEstudio resul3 = dao3.update(solicitudEstudio);
            this.solicitudEstudioDto= SolicitudMapper.mapEntityToDto(resul3);

            if (encuestaDto.getPreguntas() != null) {

                List<PreguntaDto> pregunta = encuestaDto.getPreguntas();

                for (PreguntaDto obj : pregunta) {

                    PreguntaEncuesta preguntaEncuesta = new PreguntaEncuesta();
                    preguntaEncuesta.set_encuesta(resul);
                    Pregunta pregunta1 = new Pregunta();

                    pregunta1 = dao6.find(obj.getId(), Pregunta.class);

                    preguntaEncuesta.set_pregunta(pregunta1);

                    PreguntaEncuesta resul2 = dao2.insert(preguntaEncuesta);
                    this.preguntaEncuestaDto= PreguntaEncuestaMapper.mapEntityToDto(resul2);


                }
            }

            if (encuestaDto.getEncuestado() != null) {

                List<EncuestadoDto> encuestado = encuestaDto.getEncuestado();

                for (EncuestadoDto obj : encuestado) {


                    Participacion participacion = new Participacion();
                    participacion.set_solicitudestudio(solicitudEstudio);
                    participacion.set_estado("activo");
                    Encuestado encuestado1 = new Encuestado();
                    encuestado1 = dao4.find(obj.getId(), Encuestado.class);

                    participacion.set_encuestado(encuestado1);

                    Participacion resul2 = dao5.insert(participacion);
                    this.participacionDto= ParticipacionMapper.mapEntityToDto(resul2);
                }
            }
        }catch (PruebaExcepcion pruebaExcepcion) {
            pruebaExcepcion.printStackTrace();
        }

    }

    @Override
    public JsonObject getResult() {
        ResponseDto responseDto = Fabrica.crear(ResponseDto.class);
        responseDto.mensaje="Encuesta Agregada";
        responseDto.estado="success";
        responseDto.objeto=this.encuestaDto.getId();

        JsonObject data= Json.createObjectBuilder()
                .add("estado","success")
                .add("mensaje","Encuesta Agregada")
                .add("encuesta",this.encuestaDto.getId()).build();

        return data;
    }
}
