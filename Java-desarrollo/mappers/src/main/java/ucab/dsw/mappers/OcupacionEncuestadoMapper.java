package ucab.dsw.mappers;

import ucab.dsw.entidades.Encuestado;
import ucab.dsw.entidades.Ocupacion;
import ucab.dsw.entidades.OcupacionEncuestado;

public class OcupacionEncuestadoMapper {
    public static OcupacionEncuestado mapDtoToEntityInsert(Ocupacion ocupacion,Encuestado encuestado){
        OcupacionEncuestado entity = new OcupacionEncuestado();

        entity.set_ocupacion( ocupacion );
        entity.set_encuestado_ocupacion( encuestado );

        return entity;
    }
}
