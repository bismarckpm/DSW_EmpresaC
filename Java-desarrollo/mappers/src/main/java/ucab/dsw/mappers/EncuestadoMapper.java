package ucab.dsw.mappers;

import ucab.dsw.dtos.NuevoEncuestadoDto;
import ucab.dsw.entidades.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EncuestadoMapper {
    public static Encuestado mapDtoToEntityInsert(NuevoEncuestadoDto nuevoEncuestadoDto, NivelAcademico nivel_academico, Usuario usuario, Parroquia parroquia){
        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Encuestado entity = new Encuestado();

        entity.set_doc_id( nuevoEncuestadoDto.getEncuestado().getDoc_id() );
        entity.set_nombre( nuevoEncuestadoDto.getEncuestado().getNombre() );
        entity.set_apellido( nuevoEncuestadoDto.getEncuestado().getApellido() );
        entity.set_correo( nuevoEncuestadoDto.getEncuestado().getCorreo() );
        entity.set_cant_personas_vivienda( nuevoEncuestadoDto.getEncuestado().getCant_personas_vivienda() );
        entity.set_genero( nuevoEncuestadoDto.getEncuestado().getGenero() );
        entity.set_estado( "activo" );
        entity.set_nivel_academico_encuestado( nivel_academico );
        entity.set_usuario_encuestado( usuario );
        entity.set_Parroquia_encuestado( parroquia );
        try {
            entity.set_fecha_nacimiento( formato.parse(nuevoEncuestadoDto.getEncuestado().getFecha_nacimiento()) );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return entity;
    }

}
