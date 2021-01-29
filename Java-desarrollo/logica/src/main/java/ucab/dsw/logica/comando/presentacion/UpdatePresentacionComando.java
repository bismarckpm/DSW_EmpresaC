package ucab.dsw.logica.comando.presentacion;

import ucab.dsw.accesodatos.DaoPresentacion;
import ucab.dsw.accesodatos.DaoSubcategoria;
import ucab.dsw.dtos.PresentacionDto;
import ucab.dsw.dtos.SubcategoriaDto;
import ucab.dsw.entidades.Presentacion;
import ucab.dsw.entidades.Subcategoria;
import ucab.dsw.excepciones.PruebaExcepcion;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import ucab.dsw.mappers.PresentacionMapper;
import ucab.dsw.mappers.SubcategoriaMapper;

import javax.json.Json;
import javax.json.JsonObject;

public class UpdatePresentacionComando extends BaseComando {
    public long _id;
    public PresentacionDto presentacionDto;


    public UpdatePresentacionComando(long _id, PresentacionDto presentacionDto) {
        this._id = _id;
        this.presentacionDto = presentacionDto;
    }

    @Override
    public void execute() {
        try {
            DaoPresentacion dao = Fabrica.crear(DaoPresentacion.class);
            Presentacion presentacion= PresentacionMapper.mapDtoToEntityUpdate(_id,presentacionDto);

            Presentacion resul= dao.update(presentacion);

            presentacionDto= PresentacionMapper.mapEntityToDto(resul);

        } catch (PruebaExcepcion pruebaExcepcion) {
            pruebaExcepcion.printStackTrace();
        }
    }

    @Override
    public JsonObject getResult() {
        JsonObject data= Json.createObjectBuilder()
                .add("estado","success")
                .add("mensaje","Presentacion actualizada correctamente")
                .add("nombre_presentacion",presentacionDto.getNombre()).build();
        return data;
    }
}
