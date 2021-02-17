package ucab.dsw.logica.comando.analista;

import ucab.dsw.accesodatos.*;
import ucab.dsw.entidades.*;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.List;

public class EstudiosTelefonoComando extends BaseComando {

    public long _id;
    public JsonArrayBuilder estudios = Json.createArrayBuilder();

    public EstudiosTelefonoComando(long _id) {
        this._id = _id;
    }

    @Override
    public void execute() throws EmpresaException {

        try {
            DaoSolicitudEstudio dao = Fabrica.crear(DaoSolicitudEstudio.class);
            DaoMarca daoMarca = Fabrica.crear(DaoMarca.class);
            DaoSubcategoria daoSubcategoria = Fabrica.crear(DaoSubcategoria.class);
            DaoCategoria daoCategoria = Fabrica.crear(DaoCategoria.class);
            DaoParticipacion daoParticipacion = Fabrica.crear(DaoParticipacion.class);
            DaoCaracteristicaDemografica daoCaracteristica_demografica = Fabrica.crear(DaoCaracteristicaDemografica.class);


            List<SolicitudEstudio> resultado = dao.getEstudiosByAnalista(_id);

            for (SolicitudEstudio obj : resultado) {

                if (obj.get_modoencuesta().equals("telefono")) {

                    JsonArrayBuilder builderArrayEncuestado = Json.createArrayBuilder();
                    JsonObject builderObject;

                    CaracteristicaDemografica caracteristicas = daoCaracteristica_demografica.find(obj.get_caracteristicademografica().get_id(), CaracteristicaDemografica.class);
                    builderObject = this.caracteristicas(caracteristicas);
                    List<Participacion> participacion = daoParticipacion.getParticipacionByEstudio(obj.get_id());


                    if (participacion != null) {
                        for (Participacion j : participacion) {

                            builderArrayEncuestado.add(this.encuestado(j));
                        }
                    }

                    String resultadoAnalista = "";
                    SolicitudEstudio solicitudEstudio = dao.find(obj.get_id(), SolicitudEstudio.class);
                    Marca marca = daoMarca.find(solicitudEstudio.get_marca().get_id(), Marca.class);
                    Subcategoria subcategoria = daoSubcategoria.find(marca.get_subcategoria().get_id(), Subcategoria.class);
                    Categoria categoria = daoCategoria.find(subcategoria.get_categoria().get_id(), Categoria.class);

                    if (solicitudEstudio.get_resultadoanalista() != null) {
                        resultadoAnalista = solicitudEstudio.get_resultadoanalista();
                    } else {
                        resultadoAnalista = "";
                    }
                    String nombre_encuesta = "";
                    if (solicitudEstudio.get_encuesta() == null) {
                        nombre_encuesta = "Encuesta sin nombre";
                    } else {
                        nombre_encuesta = solicitudEstudio.get_encuesta().get_nombre();
                    }
                    estudios.add(Json.createObjectBuilder().add("id", solicitudEstudio.get_id())
                            .add("fecha", solicitudEstudio.get_fecha_inicio().toString())
                            .add("modo_encuesta", solicitudEstudio.get_modoencuesta())
                            .add("caracteristica_demografica", builderObject)
                            .add("marca", marca.get_nombre())
                            .add("subcategoria", subcategoria.get_nombre())
                            .add("categoria", categoria.get_nombre())
                            .add("participacion", builderArrayEncuestado)
                            .add("resultado", resultadoAnalista)
                            .add("nombre_encuesta", nombre_encuesta)
                            .add("estado", solicitudEstudio.get_estado()));

                }
            }
        }
        catch (NullPointerException ex){
            throw new EmpresaException("C-AN04-E-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }

    }

    @Override
    public JsonObject getResult() throws EmpresaException{

        try {
            JsonObject data = Json.createObjectBuilder().add("estado", "success")
                    .add("mensaje", "Estudios del analista: " + _id + " - modo: Telefono")
                    .add("estudios", estudios).build();
            return data;
        }
        catch (NullPointerException ex){
            throw new EmpresaException("C-AN04-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }


    public JsonObject caracteristicas(CaracteristicaDemografica caracteristicas) throws NullPointerException{
        JsonObject builderObject= Json.createObjectBuilder().add("edad_min",caracteristicas.get_edad_min())
                .add("edad_max",caracteristicas.get_edad_max())
                .add("nivel_socieconomico",caracteristicas.get_nivel_socioeconomico())
                .add("nacionalidad",caracteristicas.get_nacionalidad())
                .add("cantidad_hijos",caracteristicas.get_cantidad_hijos())
                .add("genero",caracteristicas.get_genero())
                .add("parroquia",caracteristicas.get_Parroquia_demografia().get_nombre())
                .add("estado",caracteristicas.get_Parroquia_demografia().get_ciudad().get_estado().get_nombre())
                .add("ciudad",caracteristicas.get_Parroquia_demografia().get_ciudad().get_nombre())
                .add("pais",caracteristicas.get_Parroquia_demografia().get_ciudad().get_estado().get_pais().get_nombre())
                .add("nivel_academico",caracteristicas.get_nivel_academico_demografia().get_nombre()).build();

        return builderObject;
    }

    public JsonObject encuestado(Participacion j) throws NullPointerException{

        DaoParticipacion daoParticipacion= Fabrica.crear(DaoParticipacion.class);
        DaoEncuestado daoEncuestado = Fabrica.crear(DaoEncuestado.class);
        DaoUsuario daoUsuario = Fabrica.crear(DaoUsuario.class);

        Participacion participacion = daoParticipacion.find(j.get_id(), Participacion.class);
        Encuestado encuestado = daoEncuestado.find(j.get_encuestado().get_id(),Encuestado.class);
        Usuario usuario = daoUsuario.find(encuestado.get_usuario_encuestado().get_id(),Usuario.class);

        JsonObject builderObject= Json.createObjectBuilder().add("participacion_id", participacion.get_id())
                .add("doc_id", encuestado.get_doc_id())
                .add("id_encuestado", encuestado.get_id())
                .add("usuario",usuario.get_usuario())
                .add("correo",encuestado.get_correo())
                .add("nombre",encuestado.get_nombre())
                .add("apellido",encuestado.get_apellido())
                .add("estado",participacion.get_estado()).build();

        return builderObject;
    }


}
