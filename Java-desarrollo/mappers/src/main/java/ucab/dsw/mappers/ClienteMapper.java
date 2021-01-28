package ucab.dsw.mappers;

import ucab.dsw.dtos.ClienteDto;
import ucab.dsw.entidades.Cliente;
import ucab.dsw.entidades.Usuario;
import ucab.dsw.excepciones.PruebaExcepcion;

public class ClienteMapper {

    public static Cliente mapDtoToEntityInsert(ClienteDto clienteDto, Usuario usuario){
        Cliente entity = new Cliente();

        entity.set_rif(clienteDto.getRif());
        entity.set_razon_social(clienteDto.getRazon_social());
        entity.set_nombre_empresa(clienteDto.getNombre_empresa());
        entity.set_usuario_cliente(usuario);
        entity.set_estado("activo");

        return entity;
    }

    public static Cliente mapDtoToEntityUpdate(ClienteDto clienteDto, Usuario usuario){
        Cliente entity = new Cliente();

        entity.set_rif(clienteDto.getRif());
        entity.set_razon_social(clienteDto.getRazon_social());
        entity.set_nombre_empresa(clienteDto.getNombre_empresa());
        entity.set_usuario_cliente(usuario);

        return entity;
    }


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
