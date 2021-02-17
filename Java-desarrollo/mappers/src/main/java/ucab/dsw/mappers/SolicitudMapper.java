package ucab.dsw.mappers;

import ucab.dsw.dtos.SolicitudEstudioDto;
import ucab.dsw.entidades.SolicitudEstudio;
import ucab.dsw.excepciones.PruebaExcepcion;

public class SolicitudMapper {

    public static SolicitudEstudioDto mapEntityToDto(  SolicitudEstudio entity ) throws PruebaExcepcion {
        SolicitudEstudioDto dto = new SolicitudEstudioDto();

        dto.setId(entity.get_id());
        dto.setFecha_inicio(entity.get_fecha_inicio());
        dto.setFecha_fin(entity.get_fecha_fin());
        dto.setEstado(entity.get_estado());
        dto.setResultadoanalista(entity.get_resultadoanalista());
        dto.setModoencuesta(entity.get_modoencuesta());
        return dto;
    }
}
