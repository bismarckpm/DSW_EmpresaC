package ucab.dsw.mappers;

import ucab.dsw.accesodatos.DaoCaracteristicaDemografica;
import ucab.dsw.accesodatos.DaoNivelAcademico;
import ucab.dsw.accesodatos.DaoParroquia;
import ucab.dsw.dtos.CaracteristicaDemograficaDto;
import ucab.dsw.entidades.CaracteristicaDemografica;
import ucab.dsw.entidades.NivelAcademico;
import ucab.dsw.entidades.Parroquia;

public class CaracteristicasMapper {
    public static CaracteristicaDemografica mapDtoToEntity(CaracteristicaDemograficaDto dto )
    {
        CaracteristicaDemografica entity=new CaracteristicaDemografica();
        DaoNivelAcademico daoNivelAcademico=new DaoNivelAcademico();
        DaoParroquia daoParroquia= new DaoParroquia();
        
        entity.set_edad_min(dto.getEdad_min());
        entity.set_edad_max(dto.getEdad_max());
        entity.set_nacionalidad(dto.getNacionalidad());
        entity.set_cantidad_hijos(dto.getCantidad_hijos());
        entity.set_genero(dto.getGenero());
        entity.set_nivel_socioeconomico(dto.getNivel_socioeconomico());
        NivelAcademico nivel_academico= daoNivelAcademico.find(dto.getNivel_AcademicoDto().getId(), NivelAcademico.class);
        Parroquia parroquia= daoParroquia.find(dto.getParroquiaDto().getId(),Parroquia.class);
        entity.set_nivel_academico_demografia(nivel_academico);
        entity.set_Parroquia_demografia(parroquia);

        return entity;
    }
    

}
