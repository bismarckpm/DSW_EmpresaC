package ucab.dsw.mappers;

import ucab.dsw.dtos.HijoDto;
import ucab.dsw.entidades.Encuestado;
import ucab.dsw.entidades.Hijo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class HijoMapper {
    public static Hijo mapDtoToEntityInsert(HijoDto hijoDto, Encuestado encuestado) {
        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Hijo entity = new Hijo();

        entity.set_nombre( hijoDto.getNombre() );
        entity.set_apellido( hijoDto.getApellido() );
        entity.set_genero( hijoDto.getGenero() );
        entity.set_estado( "activo" );
        entity.set_encuestado_hijo( encuestado );
        try {
            entity.set_fecha_nacimiento( formato.parse(hijoDto.getFecha_nacimiento()) );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return entity;
    }
}
