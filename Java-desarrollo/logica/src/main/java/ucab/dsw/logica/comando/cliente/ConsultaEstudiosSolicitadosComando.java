package ucab.dsw.logica.comando.cliente;

import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.ClienteDto;
import ucab.dsw.dtos.ResponseDto;
import ucab.dsw.entidades.*;
import ucab.dsw.excepciones.PruebaExcepcion;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import ucab.dsw.mappers.ClienteMapper;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.List;

public class ConsultaEstudiosSolicitadosComando extends BaseComando {
    public JsonArrayBuilder estudio = Json.createArrayBuilder();
    public JsonArrayBuilder Encuestado =Json.createArrayBuilder();
    public JsonObject caracteristicaDemografica;
    public long _id;

    public ConsultaEstudiosSolicitadosComando(long _id){
        this._id=_id;
    }

    @Override
    public void execute() {

        DaoSolicitudEstudio dao = new DaoSolicitudEstudio();
        DaoMarca daoMarca = new DaoMarca();
        DaoSubcategoria daoSubcategoria = new DaoSubcategoria ();
        DaoCategoria daoCategoria = new DaoCategoria();
        DaoParticipacion daoParticipacion=new DaoParticipacion();
        DaoEncuestado daoEncuestado = new DaoEncuestado();
        DaoUsuario daoUsuario = new DaoUsuario();
        DaoCaracteristicaDemografica daoCaracteristica_demografica = new DaoCaracteristicaDemografica();

        List<SolicitudEstudio> resultado = dao.getEstudiosByClienteId(_id);

        for (SolicitudEstudio obj : resultado) {

            CaracteristicaDemografica caracteristicas= daoCaracteristica_demografica.find(obj.get_caracteristicademografica().get_id(), CaracteristicaDemografica.class);

            caracteristicaDemografica= Json.createObjectBuilder().add("edad_min",caracteristicas.get_edad_min())
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


            List<Participacion> participacion= daoParticipacion.getParticipacionByEstudio(obj.get_id());

            if(participacion!=null){

                for(Participacion j:participacion){

                    Participacion participacion1 = daoParticipacion.find(j.get_id(), Participacion.class);
                    Encuestado encuestado1 = daoEncuestado.find(j.get_encuestado().get_id(),Encuestado.class);
                    Usuario usuario1 = daoUsuario.find(encuestado1.get_usuario_encuestado().get_id(),Usuario.class);

                    Encuestado.add(Json.createObjectBuilder().add("participacion_id", participacion1.get_id())
                            .add("doc_id", encuestado1.get_doc_id())
                            .add("usuario",usuario1.get_usuario())
                            .add("correo",encuestado1.get_correo())
                            .add("nombre",encuestado1.get_nombre())
                            .add("apellido",encuestado1.get_apellido())
                            .add("estado",participacion1.get_estado()));
                }

            }

            SolicitudEstudio solicitudEstudio = dao.find(obj.get_id(),SolicitudEstudio.class);
            Marca marca = daoMarca.find(solicitudEstudio.get_marca().get_id(), Marca.class);
            Subcategoria subcategoria = daoSubcategoria.find(marca.get_subcategoria().get_id(),Subcategoria.class);
            Categoria categoria = daoCategoria.find(subcategoria.get_categoria().get_id(),Categoria.class);

            String nombre_encuesta = "";
            if (solicitudEstudio.get_encuesta()==null){
                nombre_encuesta = "Encuesta sin nombre";
            }else{
                nombre_encuesta = solicitudEstudio.get_encuesta().get_nombre();
            }

            estudio.add(Json.createObjectBuilder().add("id", solicitudEstudio.get_id())
                    .add("fecha", solicitudEstudio.get_fecha_inicio().toString())
                    .add("modo_encuesta",solicitudEstudio.get_modoencuesta())
                    .add("caracteristica_demografica",caracteristicaDemografica)
                    .add("marca",marca.get_nombre())
                    .add("subcategoria",subcategoria.get_nombre())
                    .add("categoria",categoria.get_nombre())
                    .add("participacion",Encuestado)
                    .add("nombre_encuesta", nombre_encuesta)
                    .add("estado", solicitudEstudio.get_estado()));
        }
    }

    @Override
    public JsonObject getResult() {
        ResponseDto responseDto = Fabrica.crear(ResponseDto.class);
        responseDto.mensaje="Estudio solicitado";
        responseDto.estado="success";
        responseDto.objeto=this.estudio;

        JsonObject data= Json.createObjectBuilder()
                .add("estado","success")
                .add("mensaje","Estudio solicitado")
                .add("estudios",estudio).build();

        return data;
    }
}
