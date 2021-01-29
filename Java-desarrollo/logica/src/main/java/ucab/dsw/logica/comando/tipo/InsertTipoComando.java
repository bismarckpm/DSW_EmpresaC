package ucab.dsw.logica.comando.tipo;

import ucab.dsw.accesodatos.DaoSubcategoria;
import ucab.dsw.accesodatos.DaoTipo;
import ucab.dsw.dtos.SubcategoriaDto;
import ucab.dsw.dtos.TipoDto;
import ucab.dsw.entidades.Subcategoria;
import ucab.dsw.entidades.Tipo;
import ucab.dsw.excepciones.PruebaExcepcion;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import ucab.dsw.mappers.SubcategoriaMapper;
import ucab.dsw.mappers.TipoMapper;

import javax.json.Json;
import javax.json.JsonObject;

public class InsertTipoComando extends BaseComando {
    public TipoDto tipoDto;

    public InsertTipoComando(TipoDto tipoDto) {
        this.tipoDto = tipoDto;
    }

    @Override
    public void execute() {
        try {

            DaoTipo daoTipo = Fabrica.crear(DaoTipo.class);
            Tipo tipo = TipoMapper.mapDtoToEntityInsert(tipoDto);

            Tipo resul= daoTipo.insert(tipo);
            tipoDto= TipoMapper.mapEntityToDto(resul);

        } catch (PruebaExcepcion pruebaExcepcion) {
            pruebaExcepcion.printStackTrace();
        }

    }

    @Override
    public JsonObject getResult() {

        JsonObject data= Json.createObjectBuilder()
                .add("estado","success")
                .add("mensaje","Tipo agregada exitosamente")
                .add("tipo_id",tipoDto.getId()).build();

        return data;
    }
}
