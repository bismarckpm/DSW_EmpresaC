package ucab.dsw.mappers;

import ucab.dsw.accesodatos.DaoCategoria;
import ucab.dsw.accesodatos.DaoSubcategoria;
import ucab.dsw.accesodatos.DaoTipo;
import ucab.dsw.dtos.PresentacionDto;
import ucab.dsw.dtos.SubcategoriaDto;
import ucab.dsw.dtos.TipoDto;
import ucab.dsw.entidades.Categoria;
import ucab.dsw.entidades.Presentacion;
import ucab.dsw.entidades.Subcategoria;
import ucab.dsw.entidades.Tipo;
import ucab.dsw.excepciones.PruebaExcepcion;

public class TipoMapper {

    public static Tipo mapDtoToEntityInsert(TipoDto dto )
    {
        Tipo entity= new Tipo();

        entity.set_nombre(dto.getNombre());
        entity.set_estado("activo");
        return entity;
    }

    public static Tipo mapDtoToEntityUpdate(long _id,TipoDto dto )
    {
        DaoTipo daoTipo=new DaoTipo();

        Tipo entity = daoTipo.find(_id,Tipo.class);

        entity.set_nombre(dto.getNombre());
        return entity;
    }

    public static TipoDto mapEntityToDto(Tipo entity ) throws PruebaExcepcion {
        TipoDto dto = new TipoDto();

        dto.setId(entity.get_id());
        dto.setNombre( entity.get_nombre());
        dto.setEstado(entity.get_estado());


        return dto;
    }
}
