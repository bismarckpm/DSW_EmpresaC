package ucab.dsw.mappers;

import ucab.dsw.dtos.PreguntaEncuestaDto;
import ucab.dsw.entidades.PreguntaEncuesta;
import ucab.dsw.excepciones.PruebaExcepcion;

public class PreguntaEncuestaMapper {

    public static PreguntaEncuestaDto mapEntityToDto(PreguntaEncuesta entity ) throws PruebaExcepcion {
        PreguntaEncuestaDto dto = new PreguntaEncuestaDto();

        dto.setId(entity.get_id());
        return dto;
    }
}
