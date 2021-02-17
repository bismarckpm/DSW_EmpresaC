package ucab.dsw.mappers;

import ucab.dsw.dtos.EncuestaDto;
import ucab.dsw.entidades.Encuesta;
import ucab.dsw.excepciones.PruebaExcepcion;

public class EncuestaMapper {

    public static EncuestaDto mapEntityToDto(Encuesta entity ) throws PruebaExcepcion {
        EncuestaDto dto = new EncuestaDto();

        dto.setId(entity.get_id());
        dto.setNombre( entity.get_nombre());
        dto.setEstado(entity.get_estado());

        return dto;
    }
}
