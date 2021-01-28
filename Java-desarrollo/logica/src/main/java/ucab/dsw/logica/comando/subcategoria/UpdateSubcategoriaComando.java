package ucab.dsw.logica.comando.subcategoria;

import ucab.dsw.accesodatos.DaoCategoria;
import ucab.dsw.accesodatos.DaoSubcategoria;
import ucab.dsw.dtos.SubcategoriaDto;
import ucab.dsw.entidades.Categoria;
import ucab.dsw.entidades.Subcategoria;
import ucab.dsw.excepciones.PruebaExcepcion;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import ucab.dsw.mappers.SubcategoriaMapper;

import javax.json.Json;
import javax.json.JsonObject;

public class UpdateSubcategoriaComando extends BaseComando {

    public long _id;
    public SubcategoriaDto subcategoriaDto;

    public UpdateSubcategoriaComando(long _id, SubcategoriaDto subcategoriaDto) {
        this._id = _id;
        this.subcategoriaDto = subcategoriaDto;
    }

    @Override
    public void execute() {
        try {
            DaoSubcategoria dao = Fabrica.crear(DaoSubcategoria.class);
            Subcategoria subcategoria= SubcategoriaMapper.mapDtoToEntityUpdate(_id,subcategoriaDto);

            Subcategoria resul = dao.update(subcategoria);
            subcategoriaDto=SubcategoriaMapper.mapEntityToDto(resul);

        } catch (PruebaExcepcion pruebaExcepcion) {
            pruebaExcepcion.printStackTrace();
        }
    }

    @Override
    public JsonObject getResult() {
        JsonObject data= Json.createObjectBuilder()
                                .add("estado","success")
                                .add("mensaje","Subcategoria actualizada correctamente")
                                .add("nombre_subcategoria",subcategoriaDto.getNombre()).build();
        return data;
    }
}
