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

/**
 * Una clase que contiene un conjunto de metodos y/o funciones correspondiente al cliente
 * @version 1.0, 02/01/2021
 * @author Gabriel Romero
 */

@Path( "/cliente" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class metodos_clientes {


    /**
    * Esta funcion consiste en traer los estudios que tiene un cliente
    * @author Gabriel Romero
    * @param _id corresponde al id del cliente
    * @throws Exception si ocurre cualquier excepcion general no controlada previamente
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos) 
    *         y mensaje en caso de ocurrir alguna de las excepciones
    */
    @GET
    @Path("/estudios/{_id}")
    public Response consultaEstudios_Solicitados(@PathParam("_id") long _id) {

        JsonArrayBuilder builder = Json.createArrayBuilder();
        JsonArrayBuilder builderArrayEncuestado =Json.createArrayBuilder();
        JsonObject builderObject;
        JsonObject data;

        DaoSolicitudEstudio dao = new DaoSolicitudEstudio();
        DaoMarca daoMarca = new DaoMarca();
        DaoSubcategoria daoSubcategoria = new DaoSubcategoria ();
        DaoCategoria daoCategoria = new DaoCategoria();
        DaoParticipacion daoParticipacion=new DaoParticipacion();
        DaoEncuestado daoEncuestado = new DaoEncuestado();
        DaoUsuario daoUsuario = new DaoUsuario();

        DaoCaracteristica_Demografica daoCaracteristica_demografica = new DaoCaracteristica_Demografica();

        try {
            List<SolicitudEstudio>resultado = dao.getEstudiosByClienteId(_id);

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

                if(participacion!=null){

                    for(Participacion j:participacion){
                    
                    Participacion participacion1 = daoParticipacion.find(j.get_id(), Participacion.class);
                    Encuestado encuestado1 = daoEncuestado.find(j.get_encuestado().get_id(),Encuestado.class);
                    Usuario usuario1 = daoUsuario.find(encuestado1.get_usuario_encuestado().get_id(),Usuario.class);

                    builderArrayEncuestado.add(Json.createObjectBuilder().add("participacion_id", participacion1.get_id())
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

                builder.add(Json.createObjectBuilder().add("id", solicitudEstudio.get_id())
                        .add("fecha", solicitudEstudio.get_fecha_inicio().toString())
                        .add("modo_encuesta",solicitudEstudio.get_modoencuesta())
                        .add("caracteristica_demografica",builderObject)
                        .add("marca",marca.get_nombre())
                        .add("subcategoria",subcategoria.get_nombre())
                        .add("categoria",categoria.get_nombre())
                        .add("participacion",builderArrayEncuestado)
                        .add("nombre_encuesta", nombre_encuesta)
                        .add("estado", solicitudEstudio.get_estado()));
            }


            data= Json.createObjectBuilder().add("estado","success")
                    .add("mensaje","Estudios del cliente "+ _id)
                    .add("codigo",200)
                    .add("estudios",builder).build();
        }
        catch (Exception ex){
            ex.printStackTrace();
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

    /**
    * Esta funcion consiste en obtener el id del cliente
    * @author Gabriel Romero
    * @param _id corresponde al id del usuario
    * @throws Exception si ocurre cualquier excepcion general no controlada previamente
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado, cliente_id 
    *         y mensaje.
    */
    @GET
    @Path("/get-id/{_id}")
    public Response getClienteId(@PathParam("_id") long _id) {

        JsonObject data;

        DaoCliente dao = new DaoCliente();
        try {

            Cliente cliente= dao.getClienteId(_id);

            data= Json.createObjectBuilder().add("estado","success")
                    .add("mensaje","uid del cliente es: "+ _id)
                    .add("codigo",200)
                    .add("cliente_id",cliente.get_id()).build();
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
    /**
     * Esta funcion consiste en obtener la respuesta de un estudio en especifico
     * @author Carlos Silva
     * @param _id corresponde al id del estudio
     * @throws Exception si ocurre cualquier excepcion general no controlada previamente
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, cliente_id
     *         y mensaje.
     */
    @GET
    @Path("/respuesta-analista/{id}")
    public Response respuesta_analista(@PathParam("id") long _id) {
        JsonObject data;
        JsonArrayBuilder builder = Json.createArrayBuilder();
        try {

            DaoSolicitudEstudio daoSolicitudEstudio = new DaoSolicitudEstudio();
            SolicitudEstudio solicitudEstudio=daoSolicitudEstudio.find(_id,SolicitudEstudio.class);

                if (solicitudEstudio.get_resultadoanalista() != null) {
                    JsonObject p = Json.createObjectBuilder().add("resultado", solicitudEstudio.get_resultadoanalista())
                            .build();
                    builder.add(p);
                }

            data = Json.createObjectBuilder()
                    .add("estado", "success")
                    .add("codigo", 200)
                    .add("Preguntas", builder).build();


        } catch (Exception ex) {
            String problema = ex.getMessage();

            data = Json.createObjectBuilder()
                    .add("estado", "exception!!!")
                    .add("excepcion", ex.getMessage())
                    .add("codigo", 500).build();


            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();
        }
        //builder.build();
        System.out.println(data);
        return Response.status(Response.Status.OK).entity(data).build();
    }

}
