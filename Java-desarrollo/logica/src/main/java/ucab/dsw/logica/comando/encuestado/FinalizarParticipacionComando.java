package ucab.dsw.logica.comando.encuestado;

import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.ParticipacionDto;
import ucab.dsw.dtos.ResponseDto;
import ucab.dsw.dtos.SolicitudEstudioDto;
import ucab.dsw.entidades.*;
import ucab.dsw.excepciones.PruebaExcepcion;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import ucab.dsw.mappers.ParticipacionMapper;
import ucab.dsw.mappers.SolicitudMapper;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.List;

public class FinalizarParticipacionComando extends BaseComando {

    public JsonArrayBuilder pregunta_estudio= Json.createArrayBuilder();
    public long _id;
    public long _id2;
    public SolicitudEstudioDto solicitudEstudioDto;
    public ParticipacionDto participacionDto;

    public FinalizarParticipacionComando(long _id,long _id2){
        this._id=_id;
        this._id2=_id2;
    }

    @Override
    public void execute() {
        try{
            int restantes=0;
            DaoSolicitudEstudio daoSolicitudEstudio = new DaoSolicitudEstudio();
            DaoParticipacion dao = new DaoParticipacion();
            DaoParticipacion dao2 = new DaoParticipacion();
            List<Participacion> participacion = null;
            Class<Participacion> type = Participacion.class;
            participacion = dao.findAll(type);

            for (Participacion obj : participacion) {
                if (obj.get_solicitudestudio().get_id()==_id && obj.get_encuestado().get_id()==_id2) {
                    Participacion participacion1 = dao2.find(obj.get_id(), Participacion.class);
                    participacion1.set_estado("inactivo");

                    Participacion resul = dao.update(participacion1);
                    this.participacionDto= ParticipacionMapper.mapEntityToDto(resul);
                }
            }
            for (Participacion obj : participacion) {
                if (obj.get_solicitudestudio().get_id()==_id && obj.get_estado().equals("activo")) {
                    restantes=1;
                }
            }
            if (restantes==0){
                SolicitudEstudio solicitudEstudio = daoSolicitudEstudio.find(_id,SolicitudEstudio.class);
                solicitudEstudio.set_estado("finalizado");

                SolicitudEstudio resul = daoSolicitudEstudio.update(solicitudEstudio);
                this.solicitudEstudioDto= SolicitudMapper.mapEntityToDto(resul);
            }
        }catch (
        PruebaExcepcion pruebaExcepcion) {
            pruebaExcepcion.printStackTrace();
        }
    }
    @Override
    public JsonObject getResult() {
        ResponseDto responseDto = Fabrica.crear(ResponseDto.class);
        responseDto.mensaje="Finalizado";
        responseDto.estado="success";
        responseDto.objeto=this.participacionDto.getEstado();


        JsonObject data= Json.createObjectBuilder().add("mensaje","Finalizado")
                .add("estado","success")
                .add("participacion_estado",participacionDto.getEstado()).build();

        return data;
    }

}
