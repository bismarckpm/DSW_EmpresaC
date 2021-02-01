package ucab.dsw.logica.comando.marca;

import ucab.dsw.accesodatos.DaoMarca;
import ucab.dsw.accesodatos.DaoMarcaTipo;
import ucab.dsw.accesodatos.DaoPresentacion;
import ucab.dsw.accesodatos.DaoSubcategoria;
import ucab.dsw.entidades.Marca;
import ucab.dsw.entidades.MarcaTipo;
import ucab.dsw.entidades.Presentacion;
import ucab.dsw.entidades.Subcategoria;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.List;

public class AllMarcaComando extends BaseComando {

    public JsonArrayBuilder marcas = Json.createArrayBuilder();

    @Override
    public void execute() {

        DaoMarca dao= Fabrica.crear(DaoMarca.class);
        DaoSubcategoria daoSubcategoria= Fabrica.crear(DaoSubcategoria.class);
        DaoPresentacion daoPresentacion=Fabrica.crear(DaoPresentacion.class);
        DaoMarcaTipo daoMarca_tipo=Fabrica.crear(DaoMarcaTipo.class);

        List<Marca> resultado= dao.findAll(Marca.class);

        for(Marca obj: resultado){

            List<MarcaTipo> marca_tipos=daoMarca_tipo.getAllMarcaTiposByMarca(obj.get_id());

            JsonArrayBuilder tipoArrayJson= Json.createArrayBuilder();
            JsonArrayBuilder presentacionesArrayJson= Json.createArrayBuilder();

            for(MarcaTipo obj2: marca_tipos){

                List<Presentacion> presentaciones=daoPresentacion.getAllPresentacionesByTipo(obj2.get_tipo().get_id());

                for(Presentacion obj3: presentaciones){
                    JsonObject presentacion = Json.createObjectBuilder().add("presentacion_id",obj3.get_id())
                            .add("nombre",obj3.get_nombre()).build();
                    presentacionesArrayJson.add(presentacion);

                }

                JsonObject tipo = Json.createObjectBuilder().add("marca_tipo_id",obj2.get_id())
                        .add("tipo",obj2.get_tipo().get_nombre())
                        .add("tipo_id",obj2.get_tipo().get_id())
                        .add("presentaciones",presentacionesArrayJson).build();

                tipoArrayJson.add(tipo);
            }

            Subcategoria subcategoria= daoSubcategoria.find(obj.get_subcategoria().get_id(),Subcategoria.class);
            JsonObject marca = Json.createObjectBuilder().add("id",obj.get_id())
                    .add("nombre",obj.get_nombre())
                    .add("subcategoria_id",subcategoria.get_id())
                    .add("subcategoria",subcategoria.get_nombre())
                    .add("estado",obj.get_estado())
                    .add("tipos",tipoArrayJson).build();

            marcas.add(marca);

        }
    }

    @Override
    public JsonObject getResult() {
        JsonObject data= Json.createObjectBuilder()
                            .add("estado","success")
                            .add("mensaje","Todas las marcas")
                            .add("marcas",marcas).build();

        return data;
    }
}
