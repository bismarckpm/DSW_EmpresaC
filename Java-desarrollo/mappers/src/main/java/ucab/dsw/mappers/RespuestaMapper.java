package ucab.dsw.mappers;

import ucab.dsw.dtos.RespuestaDto;
import ucab.dsw.entidades.Respuesta;
import ucab.dsw.excepciones.PruebaExcepcion;

public class RespuestaMapper {

    public static RespuestaDto mapEntityToDto(Respuesta entity ) throws PruebaExcepcion {
        RespuestaDto dto = new RespuestaDto();

        dto.setId(entity.get_id());
        dto.setRespuestaboolean( entity.get_respuestaboolean());
        dto.setRespuestadesarrollo( entity.get_respuestadesarrollo());
        dto.setRespuestarango(entity.get_respuestarango());
        dto.setEstado( entity.get_estado());

        return dto;
    }
}
