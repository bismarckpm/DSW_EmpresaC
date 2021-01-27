package ucab.dsw.mappers;

import ucab.dsw.dtos.Respuesta_Opcion;
import ucab.dsw.entidades.RespuestaOpcion;
import ucab.dsw.excepciones.PruebaExcepcion;

public class RespuestaOpcionMapper {
    public static Respuesta_Opcion mapEntityToDto(RespuestaOpcion entity ) throws PruebaExcepcion {
        Respuesta_Opcion dto = new Respuesta_Opcion();

        dto.setId(entity.get_id());


        return dto;
    }
}
