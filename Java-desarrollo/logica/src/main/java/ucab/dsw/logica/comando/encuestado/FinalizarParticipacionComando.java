package ucab.dsw.logica.comando.encuestado;

import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.ParticipacionDto;
import ucab.dsw.dtos.SolicitudEstudioDto;
import ucab.dsw.entidades.*;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.excepciones.PruebaExcepcion;
import ucab.dsw.logica.comando.BaseComando;
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
    public void execute() throws EmpresaException{
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
        } catch (PruebaExcepcion ex) {
            ex.printStackTrace();
            throw new EmpresaException("C-ENC05-ZERO-ID", ex.getMessage(), "Intento asignar el valor de 0 a un ID");
        }
    }
    @Override
    public JsonObject getResult() throws EmpresaException{
        try{
            JsonObject data= Json.createObjectBuilder().add("mensaje","Finalizado")
                    .add("estado","success")
                    .add("participacion_estado",participacionDto.getEstado()).build();

            return data;
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            throw new EmpresaException("C-ENC05-G-NULL", "Ha ocurrido un error en los JsonObject - Cause: Null key/pair", "Error. Intente mas tarde.");
        }
    }
}