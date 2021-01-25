package ucab.dsw.logica.comando.subcategoria;

import ucab.dsw.accesodatos.DaoMarca;
import ucab.dsw.accesodatos.DaoSubcategoria;
import ucab.dsw.dtos.SubcategoriaDto;
import ucab.dsw.entidades.Marca;
import ucab.dsw.entidades.Subcategoria;
import ucab.dsw.excepciones.PruebaExcepcion;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import ucab.dsw.mappers.SubcategoriaMapper;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.List;

public class ActivateSubcategoriaComando extends BaseComando {

    public long _id;
    public SubcategoriaDto subcategoriaDto;

    public ActivateSubcategoriaComando(long _id) {
        this._id = _id;
    }
    @Override
    public void execute() {
        try {
            DaoSubcategoria dao = Fabrica.crear(DaoSubcategoria.class);
            DaoMarca daoMarca = Fabrica.crear(DaoMarca.class);

            Subcategoria subcategoria = dao.find(_id,Subcategoria.class);
            subcategoria.set_estado("activo");

            Subcategoria resul = dao.update(subcategoria);

            List<Marca> marcas =daoMarca.findAll(Marca.class);

            for(Marca obj: marcas) {

                if (obj.get_subcategoria().get_id() == resul.get_id()){
                    Marca marca = daoMarca.find(obj.get_id(),Marca.class);
                    marca.set_estado("activo");

                    Marca marcaActualizada = daoMarca.update(marca);
                }
            }

            subcategoriaDto= SubcategoriaMapper.mapEntityToDto(resul);
        } catch (PruebaExcepcion pruebaExcepcion) {
            pruebaExcepcion.printStackTrace();
        }


    }

    @Override
    public JsonObject getResult() {
        JsonObject data= Json.createObjectBuilder()
                .add("estado","success")
                .add("mensaje","Subcategoria habilitada correctamente")
                .add("estado_subcategoria", subcategoriaDto.getEstado()).build();

        return data;
    }
}
