package ucab.dsw.mappers;

import ucab.dsw.dtos.ClienteDto;
import ucab.dsw.entidades.Cliente;
import ucab.dsw.entidades.Usuario;

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
}
