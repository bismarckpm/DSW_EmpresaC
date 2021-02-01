package ucab.dsw.servicio;

import org.eclipse.persistence.exceptions.DatabaseException;

import ucab.dsw.dtos.*;
import ucab.dsw.logica.comando.presentacion.*;
import ucab.dsw.logica.fabrica.Fabrica;
import javax.persistence.PersistenceException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.json.Json;
import javax.ws.rs.core.Response;
import javax.json.JsonObject;
import javax.ws.rs.*;

@Path( "/presentacion" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class PresentacionServicio {

    /**
     * Esta funcion consiste agregar una nueva presentacion
     * @author Carlos Silva
     * @param presentacionDto corresponde al objeto de la capa web que contiene los nuevos datos que se desean insertar
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */

    @POST
    @Path( "/add-presentacion" )
    public Response addPresentacion(PresentacionDto presentacionDto)
    {
        JsonObject resul;
        try
        {
            InsertPresentacionComando comando=Fabrica.crearComandoConDto(InsertPresentacionComando.class,presentacionDto);
            comando.execute();

            return Response.status(Response.Status.OK).entity(comando.getResult()).build();

        }
        catch (PersistenceException | DatabaseException ex){
            ex.printStackTrace();
            resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje_soporte",ex.getMessage())
                    .add("mensaje","La subcategoria ya se encuestra registrada").build();

            return Response.status(Response.Status.BAD_REQUEST).entity(resul).build();
        }
        catch ( Exception ex){
            ex.printStackTrace();
            resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje_soporte",ex.getMessage())
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();

        }

    }
    /**
     * Esta funcion consiste en cambiar algun elemento de una presentacion
     * @author Carlos Silva
     * @param _id corresponde al id de la presentacion
     * @param presentacionDto corresponde al objeto de la capa web que contiene los nuevos datos que se desean insertar
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @PUT
    @Path( "/channge-presentacion/{id}" )
    public Response changePresentacion(@PathParam("id")long  _id,PresentacionDto presentacionDto)
    {
        JsonObject resul;
        try
        {
            UpdatePresentacionComando comando=Fabrica.crearComandoBoth(UpdatePresentacionComando.class,_id,presentacionDto);
            comando.execute();

            return Response.status(Response.Status.OK).entity(comando.getResult()).build();

        }
        catch (PersistenceException | DatabaseException ex){
            ex.printStackTrace();
            resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje_soporte",ex.getMessage())
                    .add("mensaje","La presentacion ya se encuestra registrada").build();

            return Response.status(Response.Status.BAD_REQUEST).entity(resul).build();
        }
        catch ( Exception ex){
            ex.printStackTrace();
            resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje_soporte",ex.getMessage())
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();

        }

    }
    /**
     * Esta funcion consiste en cambiar el estado de una presentacion a inactivo
     * @author Carlos Silva
     * @param _id corresponde al id de la presentacion
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @DELETE
    @Path( "/delete-presentacion/{id}" )
    public Response EliminarPresentacion( @PathParam("id")long  _id )
    {
        JsonObject resul;
        try
        {
            DeletePresentacionComando comando=Fabrica.crearComandoConId(DeletePresentacionComando.class,_id);
            comando.execute();

            return Response.status(Response.Status.OK).entity(comando.getResult()).build();
        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
            resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje_soporte",ex.getMessage())
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }

    }
    /**
     * Esta funcion consiste en cambiar el estado de una presentacion a activo
     * @author Carlos Silva
     * @param _id corresponde al id de la presentacion
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @DELETE
    @Path( "/activar-presentacion/{id}" )
    public Response ActivarPresentacion( @PathParam("id")long  _id )
    {
        JsonObject resul;
        try
        {
            ActivatePresentacionComando comando=Fabrica.crearComandoConId(ActivatePresentacionComando.class,_id);
            comando.execute();

            return Response.status(Response.Status.OK).entity(comando.getResult()).build();

        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
            resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje_soporte",ex.getMessage())
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }

    }
    /**
     * Esta funcion consiste en mostrar los datos de una presentacion en especifico
     * @author Carlos Silva
     * @param id corresponde al id de la presentacion
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @GET
    @Path( "/find-presentacion/{id}" )
    public Response findPresentacion( @PathParam("id")long id )
    {
        JsonObject resul;
        try {
            GetPresentacionComando comando=Fabrica.crearComandoConId(GetPresentacionComando.class,id);
            comando.execute();

            return Response.status(Response.Status.OK).entity(comando.getResult()).build();

        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
            resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje_soporte",ex.getMessage())
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }
    }
    /**
     * Esta funcion consiste en mostrar los datos de todas las presentaciones
     * @author Carlos Silva
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @GET
    @Path( "/findall-presentaciones" )
    public Response findAllPresentacion( )
    {
        JsonObject resul;
        try {
            AllPresentacionComando comando= Fabrica.crear(AllPresentacionComando.class);
            comando.execute();

            return Response.status(Response.Status.OK).entity(comando.getResult()).build();

        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
            resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje_soporte",ex.getMessage())
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }
    }
}
