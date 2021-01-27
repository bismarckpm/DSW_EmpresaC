package ucab.dsw.mappers;

import ucab.dsw.dtos.EncuestadoDto;
import ucab.dsw.entidades.Encuestado;
import ucab.dsw.excepciones.PruebaExcepcion;

public class EncuestadoMapper {
    public static EncuestadoDto mapEntityToDto(Encuestado entity ) throws PruebaExcepcion {
        EncuestadoDto dto = new EncuestadoDto();

        dto.setId(entity.get_id());
        dto.setDoc_id(entity.get_doc_id());
        dto.setNombre(entity.get_nombre());
        dto.setApellido(entity.get_apellido());
        dto.setCorreo(entity.get_correo());
        dto.setEstado(entity.get_estado());
        dto.setCant_personas_vivienda(entity.get_cant_personas_vivienda());
        dto.setGenero(entity.get_genero());

        return dto;
    }
}
