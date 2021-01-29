package ucab.dsw.logica.comando.presentacion;

import ucab.dsw.accesodatos.DaoMarca;
import ucab.dsw.accesodatos.DaoPresentacion;
import ucab.dsw.accesodatos.DaoSubcategoria;
import ucab.dsw.dtos.PresentacionDto;
import ucab.dsw.dtos.SubcategoriaDto;
import ucab.dsw.entidades.Marca;
import ucab.dsw.entidades.Presentacion;
import ucab.dsw.entidades.Subcategoria;
import ucab.dsw.excepciones.PruebaExcepcion;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import ucab.dsw.mappers.PresentacionMapper;
import ucab.dsw.mappers.SubcategoriaMapper;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.List;

public class DeletePresentacionComando extends BaseComando {
    public long _id;
    public PresentacionDto presentacionDto;


    public DeletePresentacionComando(long _id) {
        this._id = _id;
    }

    @Override
    public void execute() {
        try {


            DaoPresentacion dao = Fabrica.crear(DaoPresentacion.class);
            Presentacion presentacion = dao.find(_id,Presentacion.class);

            presentacion.set_estado("inactivo");
            Presentacion resul = dao.update(presentacion);


            presentacionDto= PresentacionMapper.mapEntityToDto(resul);
        } catch (PruebaExcepcion pruebaExcepcion) {
            pruebaExcepcion.printStackTrace();
        }


    }

    @Override
    public JsonObject getResult() {
        JsonObject data= Json.createObjectBuilder()
                .add("estado","success")
                .add("mensaje","Presentacion inhabilitada correctamente")
                .add("estado_presentacion", presentacionDto.getEstado()).build();

        return data;
    }
}
