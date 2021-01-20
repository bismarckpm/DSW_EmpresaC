package ucab.dsw.logica.comando.admin;
import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.ResponseDto;
import ucab.dsw.entidades.*;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

public class ConsultaEstudios_asignadosComando extends BaseComando{

    public JsonArrayBuilder estudios= Json.createArrayBuilder();
    public long _id;

    public ConsultaEstudios_asignadosComando(long _id){
        this._id=_id;
    }

    @Override
    public void execute() {

        List<SolicitudEstudio> resultado = null;

        DaoSolicitudEstudio dao = new DaoSolicitudEstudio();
        DaoMarca daoMarca = new DaoMarca();
        DaoSubcategoria daoSubcategoria = new DaoSubcategoria();
        DaoCategoria daoCategoria = new DaoCategoria();
        Class<SolicitudEstudio> type = SolicitudEstudio.class;

        resultado = dao.findAll(type);
        for (SolicitudEstudio obj : resultado) {
            SolicitudEstudio solicitudEstudio = dao.find(obj.get_id(), SolicitudEstudio.class);

            if (solicitudEstudio.get_usuario2() != null) {
                if (solicitudEstudio.get_encuesta() != null && solicitudEstudio.get_usuario2().get_id() == _id && solicitudEstudio.get_estado().equals("en progreso")) {
                    Marca marca = daoMarca.find(solicitudEstudio.get_marca().get_id(), Marca.class);
                    Subcategoria subcategoria = daoSubcategoria.find(marca.get_subcategoria().get_id(),Subcategoria.class);
                    Categoria categoria = daoCategoria.find(subcategoria.get_categoria().get_id(),Categoria.class);

                    JsonObject encuesta = Json.createObjectBuilder().add("Marca", marca.get_nombre())
                            .add("idcategoria", categoria.get_id())
                            .add("Categoria", categoria.get_nombre())
                            .add("idsubcategoria", subcategoria.get_id())
                            .add("Subcategoria", subcategoria.get_nombre()).build();

                    JsonObject tipo = Json.createObjectBuilder().add("id", solicitudEstudio.get_id())
                            .add("fecha", solicitudEstudio.get_fecha_inicio().toString())
                            .add("caracteristicas", encuesta)
                            .add("estatus",solicitudEstudio.get_estado()).build();

                    estudios.add(tipo);
                }
            }

        }

    }

    @Override
    public JsonObject getResult() {
        ResponseDto responseDto =Fabrica.crear(ResponseDto.class);
        responseDto.mensaje="Estudios asignados";
        responseDto.estado="success";
        responseDto.objeto=this.estudios;


        JsonObject data= Json.createObjectBuilder().add("mensaje","Todas las categorias")
                .add("estado","success")
                .add("estudios",estudios).build();

        return data;
    }

}
