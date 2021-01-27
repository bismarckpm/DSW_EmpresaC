package ucab.dsw.mappers;

import ucab.dsw.dtos.PresentacionDto;
import ucab.dsw.dtos.TipoDto;
import ucab.dsw.entidades.Presentacion;
import ucab.dsw.entidades.Tipo;
import ucab.dsw.excepciones.PruebaExcepcion;

public class TipoMapper {

    public static TipoDto mapEntityToDto(Tipo entity ) throws PruebaExcepcion {
        TipoDto dto = new TipoDto();

        dto.setId(entity.get_id());
        dto.setNombre( entity.get_nombre());
        dto.setEstado(entity.get_estado());


        return dto;
    }
}
