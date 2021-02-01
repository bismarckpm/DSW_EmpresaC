package ucab.dsw.logica.comando.admin;

import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.*;
import ucab.dsw.entidades.*;
import ucab.dsw.excepciones.PruebaExcepcion;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;

import ucab.dsw.mappers.EncuestaMapper;
import ucab.dsw.mappers.SolicitudMapper;
import ucab.dsw.mappers.PreguntaEncuestaMapper;
import ucab.dsw.mappers.ParticipacionMapper;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.List;

public class AddEncuestaComando extends BaseComando {
    public long _id;
    public long _id2;
    public EncuestaDto encuestaDto;
    public SolicitudEstudioDto solicitudEstudioDto;


    public AddEncuestaComando(long _id,long _id2,EncuestaDto encuestaDto) {
        this._id = _id;
        this._id2 = _id2;
        this.encuestaDto = encuestaDto;
    }

    @Override
    public void execute() {

        DaoEncuesta dao = new DaoEncuesta();
        DaoPreguntaEncuesta dao2 = new DaoPreguntaEncuesta();
        DaoParticipacion dao5 = new DaoParticipacion();

        Encuesta encuesta = new Encuesta();
        encuesta.set_nombre(encuestaDto.getNombre());
        encuesta.set_estado("activo");

        Marca marca = new Marca(_id);
        encuesta.set_marca(marca);

        Encuesta resul = dao.insert(encuesta);

        DaoSolicitudEstudio dao3 = new DaoSolicitudEstudio();
        SolicitudEstudio solicitudEstudio = dao3.find(_id2, SolicitudEstudio.class);

        DaoUsuario daoUsuario = new DaoUsuario();

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

        if (encuestaDto.getPreguntas() != null) {

            List<PreguntaDto> pregunta = encuestaDto.getPreguntas();

            for (PreguntaDto obj : pregunta) {

                PreguntaEncuestaDto resultado2 = new PreguntaEncuestaDto();
                PreguntaEncuesta preguntaEncuesta = new PreguntaEncuesta();
                preguntaEncuesta.set_encuesta(resul);

                DaoPregunta dao4 = new DaoPregunta();
                Pregunta pregunta1 = dao4.find(obj.getId(), Pregunta.class);

                preguntaEncuesta.set_pregunta(pregunta1);

                PreguntaEncuesta resul2 = dao2.insert(preguntaEncuesta);

            }
        }

        if (encuestaDto.getEncuestado() != null) {

            List<EncuestadoDto> encuestado = encuestaDto.getEncuestado();

            for (EncuestadoDto obj : encuestado) {

                ParticipacionDto resultado2 = new ParticipacionDto();
                Participacion participacion = new Participacion();
                participacion.set_solicitudestudio(solicitudEstudio);
                participacion.set_estado("activo");

                DaoEncuestado dao4 = new DaoEncuestado();
                Encuestado encuestado1 = dao4.find(obj.getId(), Encuestado.class);

                participacion.set_encuestado(encuestado1);

                Participacion resul2 = dao5.insert(participacion);
            }
        }
    }

    @Override
    public JsonObject getResult() {
        JsonObject data= Json.createObjectBuilder()
                .add("estado","success")
                .add("mensaje","Encuesta Agregada")
                .add("encuesta",this.encuestaDto.getId()).build();

        return data;
    }
}
