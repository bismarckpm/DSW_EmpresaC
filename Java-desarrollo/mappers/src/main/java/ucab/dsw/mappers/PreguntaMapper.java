package ucab.dsw.mappers;

import ucab.dsw.dtos.PreguntaDto;
import ucab.dsw.entidades.Pregunta;
import ucab.dsw.excepciones.PruebaExcepcion;

public class PreguntaMapper {

    public static PreguntaDto mapEntityToDto(Pregunta entity ) throws PruebaExcepcion {
        PreguntaDto dto = new PreguntaDto();

        dto.setId(entity.get_id());
        dto.setDescripcion( entity.get_descripcion());
        dto.setTipopregunta( entity.get_tipopregunta());
        dto.setEstado(entity.get_estado());
        dto.setValormin( entity.get_valormin());
        dto.setValormax( entity.get_valormax());

        return dto;
    }
}
