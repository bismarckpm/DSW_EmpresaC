package ucab.dsw.mappers;

import ucab.dsw.dtos.OpcionSimpleMultipleDto;
import ucab.dsw.entidades.OpcionSimpleMultiple;
import ucab.dsw.excepciones.PruebaExcepcion;

public class OpcionSimpleMultipleMapper {

    public static OpcionSimpleMultipleDto mapEntityToDto(OpcionSimpleMultiple entity ) throws PruebaExcepcion {
        OpcionSimpleMultipleDto dto = new OpcionSimpleMultipleDto();

        dto.setId(entity.get_id());
        dto.setOpcion( entity.get_opcion());
        dto.setEstado(entity.get_estado());
        return dto;
    }

}
