package ucab.dsw.logica.comando.encuestado;

import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.ResponseDto;
import ucab.dsw.entidades.*;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.List;

public class ConsultaEstudiosAsignadosComando extends BaseComando {

    public JsonArrayBuilder estudios= Json.createArrayBuilder();
    public long _id;

    public ConsultaEstudiosAsignadosComando(long _id){
        this._id=_id;
    }

    @Override
    public void execute() throws EmpresaException{
        try{
            DaoParticipacion dao = new DaoParticipacion();
            DaoMarca daoMarca = new DaoMarca();
            DaoSolicitudEstudio  daoSolicitudEstudio = new DaoSolicitudEstudio();
            DaoSubcategoria daoSubcategoria = new DaoSubcategoria ();
            DaoCategoria daoCategoria = new DaoCategoria();

            List<Participacion> resultado = null;
            Class<Participacion> type = Participacion.class;

            resultado = dao.findAll(type);
            for (Participacion obj : resultado) {
                Participacion participacion = dao.find(obj.get_id(), Participacion.class);
                Marca marca = daoMarca.find(participacion.get_solicitudestudio().get_marca().get_id(), Marca.class);
                Subcategoria subcategoria = daoSubcategoria.find(marca.get_subcategoria().get_id(),Subcategoria.class);
                Categoria categoria = daoCategoria.find(subcategoria.get_categoria().get_id(),Categoria.class);
                if (participacion.get_encuestado().get_id() == _id && participacion.get_solicitudestudio().get_estado().equals("en progreso") && participacion.get_estado().equals("activo") ) {
                    JsonObject encuesta = Json.createObjectBuilder().add("Marca", marca.get_nombre())
                            .add("idcategoria", categoria.get_id())
                            .add("Categoria", categoria.get_nombre())
                            .add("idsubcategoria", subcategoria.get_id())
                            .add("Subcategoria", subcategoria.get_nombre()).build();

                    SolicitudEstudio solicitudEstudio = daoSolicitudEstudio.find(participacion.get_solicitudestudio().get_id(), SolicitudEstudio.class);

                    String nombre_encuesta = "";
                    if (solicitudEstudio.get_encuesta()==null){
                        nombre_encuesta = "Encuesta sin nombre";
                    }else{
                        nombre_encuesta = solicitudEstudio.get_encuesta().get_nombre();
                    }
                    JsonObject tipo = Json.createObjectBuilder().add("id", participacion.get_solicitudestudio().get_id())
                            .add("fecha", solicitudEstudio.get_fecha_inicio().toString())
                            .add("caracteristicas", encuesta)
                            .add("estado",solicitudEstudio.get_estado())
                            .add("modo_encuesta", solicitudEstudio.get_modoencuesta())
                            .add("nombre_encuesta",nombre_encuesta).build();


                    estudios.add(tipo);
                    System.out.println("id" + obj.get_id());
                }
            }
        }
        catch (NullPointerException ex){
            throw new EmpresaException("C-ENC01-E-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }

    }

    @Override
    public JsonObject getResult() throws EmpresaException{
        try{
            JsonObject data= Json.createObjectBuilder().add("mensaje","Todas las categorias")
                    .add("estado","success")
                    .add("estudios",estudios).build();

            return data;
        }
        catch (NullPointerException ex){
            throw new EmpresaException("C-ENC01-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }

}
