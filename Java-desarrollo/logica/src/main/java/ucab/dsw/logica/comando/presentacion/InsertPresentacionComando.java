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

public class InsertPresentacionComando extends BaseComando {
    public PresentacionDto presentacionDto;

    public InsertPresentacionComando(PresentacionDto presentacionDto) {
        this.presentacionDto = presentacionDto;
    }

    @Override
    public void execute() {
        try {
            DaoPresentacion daoPresentacion = Fabrica.crear(DaoPresentacion.class);
            Presentacion presentacion= PresentacionMapper.mapDtoToEntityInsert(presentacionDto);


            Presentacion resul = daoPresentacion.insert(presentacion);
            presentacionDto= PresentacionMapper.mapEntityToDto(resul);

        } catch (PruebaExcepcion pruebaExcepcion) {
            pruebaExcepcion.printStackTrace();
        }

    }

    @Override
    public JsonObject getResult() {

        JsonObject data= Json.createObjectBuilder()
                .add("estado","success")
                .add("mensaje","Presentacion agregada exitosamente")
                .add("presentacion_id",presentacionDto.getId()).build();

        return data;
    }
}
