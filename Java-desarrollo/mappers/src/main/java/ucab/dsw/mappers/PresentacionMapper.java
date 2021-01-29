package ucab.dsw.mappers;

import ucab.dsw.accesodatos.DaoCategoria;
import ucab.dsw.accesodatos.DaoPresentacion;
import ucab.dsw.accesodatos.DaoSubcategoria;
import ucab.dsw.accesodatos.DaoTipo;
import ucab.dsw.dtos.CategoriaDto;
import ucab.dsw.dtos.PresentacionDto;
import ucab.dsw.dtos.SubcategoriaDto;
import ucab.dsw.entidades.Categoria;
import ucab.dsw.entidades.Presentacion;
import ucab.dsw.entidades.Subcategoria;
import ucab.dsw.entidades.Tipo;
import ucab.dsw.excepciones.PruebaExcepcion;

public class PresentacionMapper {

    public static Presentacion mapDtoToEntityInsert(PresentacionDto dto )
    {

        Presentacion entity= new Presentacion();
        DaoTipo dao= new DaoTipo();

        Tipo tipo = dao.find(dto.getTipoDto().getId(),Tipo.class);


        entity.set_nombre(dto.getNombre());
        entity.set_tipo(tipo);
        entity.set_estado("activo");

        return entity;
    }

    public static Presentacion mapDtoToEntityUpdate(long _id, PresentacionDto dto )
    {
        DaoPresentacion daoPresentacion=new DaoPresentacion();
        DaoTipo daoTipo=new DaoTipo();

        Presentacion entity = daoPresentacion.find(_id,Presentacion.class);
        Tipo tipo=daoTipo.find(dto.getTipoDto().getId(),Tipo.class);

        entity.set_nombre(dto.getNombre());
        entity.set_tipo(tipo);


        return entity;
    }

    public static PresentacionDto mapEntityToDto(Presentacion entity ) throws PruebaExcepcion {
        PresentacionDto dto = new PresentacionDto();
        DaoTipo dao= new DaoTipo();

        dto.setId(entity.get_id());
        dto.setNombre( entity.get_nombre());
        dto.setEstado(entity.get_estado());
        dto.setTipoDto(TipoMapper.mapEntityToDto(dao.find(entity.get_tipo().get_id(), Tipo.class)));
        return dto;
    }
}
