package ucab.dsw.mappers;

import ucab.dsw.dtos.TelefonoDto;
import ucab.dsw.entidades.Encuestado;
import ucab.dsw.entidades.Telefono;

public class TelefonoMapper {

    public static Telefono mapDtoToEntityInsert(TelefonoDto telefonoDto, Encuestado encuestado) {
        Telefono entity = new Telefono();

        entity.set_numero( telefonoDto.getNumero() );
        entity.set_codigo_area( telefonoDto.getCodigo_area() );
        entity.set_estado( "activo" );
        entity.set_encuestado_telefono( encuestado );

        return entity;
    }

}
