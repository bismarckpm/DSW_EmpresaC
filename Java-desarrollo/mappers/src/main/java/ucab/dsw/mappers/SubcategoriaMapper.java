package ucab.dsw.mappers;


import ucab.dsw.accesodatos.DaoCategoria;
import ucab.dsw.accesodatos.DaoSubcategoria;
import ucab.dsw.dtos.SubcategoriaDto;
import ucab.dsw.entidades.Categoria;
import ucab.dsw.entidades.Subcategoria;
import ucab.dsw.excepciones.PruebaExcepcion;

public class SubcategoriaMapper {
    public static Subcategoria mapDtoToEntityInsert(SubcategoriaDto dto )
    {
        Subcategoria entity= new Subcategoria();
        DaoCategoria dao= new DaoCategoria();
        Categoria categoria = dao.find(dto.getCategoriaDto().getId(),Categoria.class);


        entity.set_nombre(dto.getNombre());
        entity.set_categoria(categoria);
        entity.set_estado("activo");


        return entity;
    }

    public static Subcategoria mapDtoToEntityUpdate(long _id,SubcategoriaDto dto )
    {
        DaoCategoria daoCategoria=new DaoCategoria();
        DaoSubcategoria daoSubcategoria=new DaoSubcategoria();

        Subcategoria entity = daoSubcategoria.find(_id,Subcategoria.class);
        Categoria categoria=daoCategoria.find(dto.getCategoriaDto().getId(),Categoria.class);

        entity.set_nombre(dto.getNombre());
        entity.set_categoria(categoria);


        return entity;
    }

    public static SubcategoriaDto mapEntityToDto(  Subcategoria entity ) throws PruebaExcepcion {
        SubcategoriaDto dto = new SubcategoriaDto();
        DaoCategoria dao= new DaoCategoria();

        dto.setId(entity.get_id());
        dto.setNombre( entity.get_nombre());
        dto.setEstado(entity.get_estado());
        dto.setCategoriaDto(CategoriaMapper.mapEntityToDto(dao.find(entity.get_categoria().get_id(),Categoria.class)));

        return dto;
    }
}
