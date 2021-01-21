package ucab.dsw.mappers;

import ucab.dsw.dtos.OpcionSimpleMultiplePreguntaDto;
import ucab.dsw.entidades.OpcionSimpleMultiplePregunta;
import ucab.dsw.excepciones.PruebaExcepcion;

public class OpcionSimpleMultiplePreguntaMapper {

    public static OpcionSimpleMultiplePreguntaDto mapEntityToDto(OpcionSimpleMultiplePregunta entity ) throws PruebaExcepcion {
        OpcionSimpleMultiplePreguntaDto dto = new OpcionSimpleMultiplePreguntaDto();

        dto.setId(entity.get_id());
        return dto;
    }


}
