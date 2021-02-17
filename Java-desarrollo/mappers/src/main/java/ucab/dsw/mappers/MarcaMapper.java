package ucab.dsw.mappers;

import ucab.dsw.accesodatos.DaoMarca;
import ucab.dsw.accesodatos.DaoSubcategoria;
import ucab.dsw.dtos.MarcaDto;
import ucab.dsw.entidades.Marca;
import ucab.dsw.entidades.Subcategoria;
import ucab.dsw.excepciones.PruebaExcepcion;

public class MarcaMapper {
    public static Marca mapDtoToEntityInsert(MarcaDto dto )
    {
        Marca entity= new Marca();
        DaoSubcategoria dao= new DaoSubcategoria();
        Subcategoria subcategoria = dao.find(dto.getSubcategoriaDto().getId(),Subcategoria.class);


        entity.set_nombre(dto.getNombre());
        entity.set_subcategoria(subcategoria);
        entity.set_estado("activo");

        return entity;
    }

    public static Marca mapDtoToEntityUpdate(long _id,MarcaDto dto )
    {
        DaoMarca dao = new DaoMarca();
        DaoSubcategoria daoSubcategoria=new DaoSubcategoria();
        Subcategoria subcategoria = daoSubcategoria.find(dto.getSubcategoriaDto().getId(),Subcategoria.class);
        Marca entity = dao.find(_id,Marca.class);

        entity.set_subcategoria(subcategoria);
        entity.set_nombre(dto.getNombre());

        return entity;
    }

    public static MarcaDto mapEntityToDto(  Marca entity ) throws PruebaExcepcion {
        MarcaDto dto = new MarcaDto();
        DaoSubcategoria dao = new DaoSubcategoria();


        dto.setId(entity.get_id());
        dto.setNombre( entity.get_nombre());
        dto.setEstado(entity.get_estado());
        dto.setSubcategoriaDto(SubcategoriaMapper.mapEntityToDto(dao.find(entity.get_subcategoria().get_id(),Subcategoria.class)));

        return dto;
    }
}
