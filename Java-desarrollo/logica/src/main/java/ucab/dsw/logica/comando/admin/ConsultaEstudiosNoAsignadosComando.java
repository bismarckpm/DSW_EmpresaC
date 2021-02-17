package ucab.dsw.logica.comando.admin;
import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.ResponseDto;
import ucab.dsw.entidades.*;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

public class ConsultaEstudiosNoAsignadosComando extends BaseComando {

    public JsonArrayBuilder estudios= Json.createArrayBuilder();
    public long _id;

    public ConsultaEstudiosNoAsignadosComando(long _id){
        this._id=_id;
    }

    @Override
    public void execute() throws EmpresaException{
        try{
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

                    if (solicitudEstudio.get_encuesta() == null && solicitudEstudio.get_usuario2().get_id() == _id) {

                        Marca marca = daoMarca.find(solicitudEstudio.get_marca().get_id(), Marca.class);  //Este fue el dao.find que falto
                        Subcategoria subcategoria = daoSubcategoria.find(marca.get_subcategoria().get_id(),Subcategoria.class);
                        Categoria categoria = daoCategoria.find(subcategoria.get_categoria().get_id(),Categoria.class);

                        JsonObject encuesta = Json.createObjectBuilder().add("Marca", marca.get_nombre())
                                .add("idcategoria", categoria.get_id())
                                .add("Categoria", categoria.get_nombre())
                                .add("idsubcategoria", subcategoria.get_id())
                                .add("Subcategoria", subcategoria.get_nombre()).build();
                        JsonObject tipo = Json.createObjectBuilder().add("id", solicitudEstudio.get_id())
                                .add("fecha", solicitudEstudio.get_fecha_inicio().toString())
                                .add("estatus", solicitudEstudio.get_estado())
                                .add("caracteristicas", encuesta)
                                .build();

                        estudios.add(tipo);
                    }
                }
            }

        }
            catch (NullPointerException ex){
            throw new EmpresaException("C-ADM02-E-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }

    @Override
    public JsonObject getResult() throws EmpresaException{
        try{
            JsonObject data= Json.createObjectBuilder().add("mensaje","Estudios no asignados")
                    .add("estado","success")
                    .add("estudios",estudios).build();

            return data;
    }
        catch (NullPointerException ex){
        throw new EmpresaException("C-ADM02-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
    }
}

}