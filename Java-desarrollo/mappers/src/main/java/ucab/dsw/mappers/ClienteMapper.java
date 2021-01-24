package ucab.dsw.mappers;

import ucab.dsw.dtos.ClienteDto;
import ucab.dsw.entidades.Cliente;
import ucab.dsw.excepciones.PruebaExcepcion;

public class ClienteMapper {
    public static ClienteDto mapEntityToDto(Cliente entity ) throws PruebaExcepcion {
        ClienteDto dto = new ClienteDto();

        dto.setId(entity.get_id());
        dto.setRif( entity.get_rif());
        dto.setRazon_social( entity.get_razon_social());
        dto.setNombre_empresa( entity.get_nombre_empresa());
        dto.setEstado(entity.get_estado());

        return dto;
    }
}
