package ucab.dsw.logica.comando.admin;

import ucab.dsw.accesodatos.DaoSolicitudEstudio;
import ucab.dsw.dtos.ResponseDto;
import ucab.dsw.dtos.SolicitudEstudioDto;
import ucab.dsw.entidades.SolicitudEstudio;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.excepciones.PruebaExcepcion;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import ucab.dsw.mappers.SolicitudMapper;

import javax.json.Json;
import javax.json.JsonObject;
public class EliminarEstudioComando extends BaseComando {
    public long _id;
    public SolicitudEstudioDto solicitudEstudioDto;

    public EliminarEstudioComando(long _id) {
        this._id = _id;
    }

    @Override
    public void execute() throws EmpresaException{
        try {
            DaoSolicitudEstudio dao = Fabrica.crear(DaoSolicitudEstudio.class);
            SolicitudEstudio solicitudEstudio = dao.find(this._id, SolicitudEstudio.class);
            solicitudEstudio.set_estado("inactivo");
            SolicitudEstudio resul = dao.update(solicitudEstudio);
            this.solicitudEstudioDto = SolicitudMapper.mapEntityToDto(resul);

        } catch (PruebaExcepcion ex) {
            ex.printStackTrace();
            throw new EmpresaException("C-ADM03-ZERO-ID", ex.getMessage(), "Intento asignar el valor de 0 a un ID");
        }

    }

    @Override
    public JsonObject getResult() throws EmpresaException{
        try {
            JsonObject data = Json.createObjectBuilder()
                    .add("estado", "success")
                    .add("mensaje", "Estudio inhabilitada")
                    .add("estudio_estado", this.solicitudEstudioDto.getEstado()).build();

            return data;
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            throw new EmpresaException("C-ADM03-G-NULL", "Ha ocurrido un error en los JsonObject - Cause: Null key/pair", "Error. Intente mas tarde.");
        }
    }
}