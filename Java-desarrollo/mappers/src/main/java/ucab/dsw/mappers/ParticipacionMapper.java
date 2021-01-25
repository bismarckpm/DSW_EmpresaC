package ucab.dsw.mappers;

import ucab.dsw.dtos.ParticipacionDto;
import ucab.dsw.entidades.Participacion;
import ucab.dsw.excepciones.PruebaExcepcion;

public class ParticipacionMapper {
    public static ParticipacionDto mapEntityToDto(Participacion entity ) throws PruebaExcepcion {
        ParticipacionDto dto = new ParticipacionDto();

        dto.setId(entity.get_id());
        dto.setEstado(entity.get_estado());
        return dto;
    }
}
