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

public class InsertSubcategoriaComando extends BaseComando {

    public SubcategoriaDto subcategoriaDto;

    public InsertSubcategoriaComando(SubcategoriaDto subcategoriaDto) {
        this.subcategoriaDto = subcategoriaDto;
    }

    @Override
    public void execute() {
        try {
            DaoSubcategoria daoSubcategoria = Fabrica.crear(DaoSubcategoria.class);
            Subcategoria subcategoria = SubcategoriaMapper.mapDtoToEntityInsert(subcategoriaDto);

            Subcategoria resul = daoSubcategoria.insert(subcategoria);

            subcategoriaDto=SubcategoriaMapper.mapEntityToDto(resul);

        } catch (PruebaExcepcion pruebaExcepcion) {
            pruebaExcepcion.printStackTrace();
        }

    }

    @Override
    public JsonObject getResult() {

        JsonObject data= Json.createObjectBuilder()
                .add("estado","success")
                .add("mensaje","Subcategoria agregada exitosamente")
                .add("subcategoria_id",subcategoriaDto.getId()).build();

        return data;
    }
}
