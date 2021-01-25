package ucab.dsw.logica.comando.admin;

import ucab.dsw.accesodatos.DaoSolicitudEstudio;
import ucab.dsw.dtos.ResponseDto;
import ucab.dsw.dtos.SolicitudEstudioDto;
import ucab.dsw.entidades.SolicitudEstudio;
import ucab.dsw.excepciones.PruebaExcepcion;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import ucab.dsw.mappers.SolicitudMapper;

import javax.json.Json;
import javax.json.JsonObject;
public class EliminarEstudioComando extends BaseComando{
    public long _id;
    public SolicitudEstudioDto solicitudEstudioDto;

    public EliminarEstudioComando(long _id) {
        this._id = _id;
    }

    @Override
    public void execute() {
        try{
            DaoSolicitudEstudio dao = Fabrica.crear(DaoSolicitudEstudio.class);
            SolicitudEstudio solicitudEstudio = dao.find(this._id,SolicitudEstudio.class);
            solicitudEstudio.set_estado("inactivo");
            SolicitudEstudio resul = dao.update(solicitudEstudio);
            this.solicitudEstudioDto= SolicitudMapper.mapEntityToDto(resul);

        }catch (PruebaExcepcion pruebaExcepcion) {
            pruebaExcepcion.printStackTrace();
        }

    }

    @Override
    public JsonObject getResult() {
        ResponseDto responseDto =Fabrica.crear(ResponseDto.class);
        responseDto.mensaje="Estudio inhabilitada";
        responseDto.estado="success";
        responseDto.objeto=this.solicitudEstudioDto.getEstado();

        JsonObject data= Json.createObjectBuilder()
                .add("estado","success")
                .add("mensaje","Estudio inhabilitada")
                .add("estudio_estado",this.solicitudEstudioDto.getEstado()).build();

        return data;
    }
}
