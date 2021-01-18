package ucab.dsw.mappers;

import ucab.dsw.accesodatos.DaoCategoria;
import ucab.dsw.dtos.CategoriaDto;
import ucab.dsw.entidades.Categoria;
import ucab.dsw.excepciones.PruebaExcepcion;


public class CategoriaMapper {

    public static Categoria mapDtoToEntityInsert(CategoriaDto dto )
    {
        Categoria entity=new Categoria();

        entity.set_nombre(dto.getNombre());
        entity.set_estado("activo");

        return entity;
    }

    public static Categoria mapDtoToEntityUpdate(long _id,CategoriaDto dto )
    {
        DaoCategoria dao= new DaoCategoria();
        Categoria entity=dao.find(_id,Categoria.class);

        entity.set_nombre(dto.getNombre());

        return entity;
    }

    public static CategoriaDto mapEntityToDto(  Categoria entity ) throws PruebaExcepcion {
        CategoriaDto dto = new CategoriaDto();

        dto.setId(entity.get_id());
        dto.setNombre( entity.get_nombre());
        dto.setEstado(entity.get_estado());

        return dto;
    }
}
