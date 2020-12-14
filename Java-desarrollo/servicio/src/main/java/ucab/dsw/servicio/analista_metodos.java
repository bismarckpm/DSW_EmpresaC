package ucab.dsw.servicio;
import org.eclipse.persistence.exceptions.DatabaseException;
import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.*;
import ucab.dsw.entidades.*;
import ucab.dsw.excepciones.PruebaExcepcion;
import javax.json.*;
import javax.persistence.PersistenceException;
import javax.json.JsonObject;
import javax.validation.constraints.Null;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import javax.ws.rs.core.Response;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path( "/analista" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class analista_metodos {


    @GET
    @Path("/estudios/{_id}")
    public Response consultaEstudios_asignados(@PathParam("_id") long _id) {

        JsonArrayBuilder builder = Json.createArrayBuilder();
        JsonArrayBuilder builderArrayEncuestado =Json.createArrayBuilder();
        JsonObject builderObject;
        JsonObject data;

        DaoSolicitudEstudio dao = new DaoSolicitudEstudio();
        DaoMarca daoMarca = new DaoMarca();
        DaoSubcategoria daoSubcategoria = new DaoSubcategoria ();
        DaoCategoria daoCategoria = new DaoCategoria();
        DaoParticipacion daoParticipacion=new DaoParticipacion();

        DaoCaracteristica_Demografica daoCaracteristica_demografica = new DaoCaracteristica_Demografica();

        try {
            List<SolicitudEstudio>resultado = dao.getEstudiosByAnalista(_id);

            for (SolicitudEstudio obj : resultado) {

                Caracteristica_Demografica caracteristicas= daoCaracteristica_demografica.find(obj.get_caracteristicademografica().get_id(), Caracteristica_Demografica.class);

                builderObject= Json.createObjectBuilder().add("edad_min",caracteristicas.get_edad_min())
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

                for(Participacion j:participacion){
                    builderArrayEncuestado.add(Json.createObjectBuilder().add("participacion_id", j.get_id())
                                                                        .add("doc_id", j.get_encuestado().get_doc_id())
                                                                        .add("usuario",j.get_encuestado().get_usuario_encuestado().get_usuario())
                                                                        .add("correo",j.get_encuestado().get_correo())
                                                                        .add("nombre",j.get_encuestado().get_nombre())
                                                                        .add("apellido",j.get_encuestado().get_apellido())
                                                                        .add("estado",j.get_estado()));


                }

                builder.add(Json.createObjectBuilder().add("id", obj.get_id())
                                                      .add("fecha", obj.get_fecha_inicio().toString())
                                                      .add("modo_encuesta",obj.get_modoencuesta())
                                                      .add("caracteristica_demografica",builderObject)
                                                      .add("marca",obj.get_marca().get_nombre())
                                                      .add("subcategoria",obj.get_marca().get_subcategoria().get_nombre())
                                                      .add("categoria",obj.get_marca().get_subcategoria().get_categoria().get_nombre())
                                                      .add("participacion",builderArrayEncuestado)
                                                      .add("estado", obj.get_estado()));


            }


            data= Json.createObjectBuilder().add("estado","success")
                                            .add("mensaje","Estudios del analista "+ _id)
                                            .add("codigo",500)
                                            .add("estudios",builder).build();
        }
        catch (Exception ex){
            data= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje",ex.getMessage())
                    .add("codigo",500).build();

            System.out.println(data);
            return Response.status(Response.Status.OK).entity(data).build();

        }
        System.out.println(data);
        return Response.status(Response.Status.OK).entity(data).build();
    }

    @PUT
    @Path( "/empezar-estudio/{id}" )
    public Response Empezar_estudio(@PathParam("id") long  _id )
    {
        JsonObject data;
        SolicitudEstudioDto resultado = new SolicitudEstudioDto();
        try
        {
            DaoSolicitudEstudio dao = new DaoSolicitudEstudio();
            SolicitudEstudio solicitudEstudio = dao.find(_id,SolicitudEstudio.class);

            solicitudEstudio.set_estado( "en progreso" );

            SolicitudEstudio resul = dao.update(solicitudEstudio);
            resultado.setId( resul.get_id() );

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200).build();

        }
        catch ( Exception ex )
        {
            String problema = ex.getMessage();
            data= Json.createObjectBuilder()
                    .add("estado","exception!!!")
                    .add("excepcion",ex.getMessage())
                    .add("codigo",500).build();


            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();
        }

        return Response.status(Response.Status.OK).entity(data).build();
    }
}
