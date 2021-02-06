package ucab.dsw.servicio;
import org.eclipse.persistence.exceptions.DatabaseException;

import ucab.dsw.dtos.*;
import ucab.dsw.logica.comando.categoria.*;
import ucab.dsw.logica.fabrica.Fabrica;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.PersistenceException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



/**
 * Una clase para la administracion completa de las categorias de MERCADEOUCAB
 * @version 1.0, 02/01/2021
 * @author Gabriel Romero
 * @author Carlos Silva
 */

@Path( "/categoria" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class CategoriaServicio extends AplicacionBase{

    /**
    * Esta funcion consiste el traer todas las categorias disponibles
    * @author Gabriel Romero
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado, categorias (array de objetos) 
    *         y mensaje en caso de ocurrir alguna de las excepciones
    */
    @GET
    @Path( "/all" )
    public Response getAllCategorias()
    {
        JsonObject resul;

        try
        {
            AllCategorialComando comando= Fabrica.crear(AllCategorialComando.class);
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
    * Esta funcion consiste en insertar una nueva categoria
    * @author Gabriel Romero
    * @param categoriaDto corresponde al objeto de la capa web que contiene los nuevos datos que se desean insertar
    * @return retorna una Response con un estado de respuesta http indicando si la operacion
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
    *         en formato JSON con los siguiente atributos: codigo, estado y mensaje en caso de ocurrir
    *         alguna de las excepciones
    */
    @POST
    @Path( "/add" )
    public Response addCategoria(CategoriaDto categoriaDto)
    {
        JsonObject resul;

        try
        {
            InsertCategoriaComando comando=Fabrica.crearComandoConDto(InsertCategoriaComando.class,categoriaDto);
            comando.execute();

            return Response.status(Response.Status.OK).entity(comando.getResult()).build();

        }
        catch (PersistenceException | DatabaseException ex){

            ex.printStackTrace();

            resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje_soporte",ex.getMessage())
                    .add("mensaje","La categoria ya se encuestra registrada").build();

            return Response.status(Response.Status.BAD_REQUEST).entity(resul).build();

        }
        catch (Exception ex){
            ex.printStackTrace();
            resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje_soporte",ex.getMessage())
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }

    }


    /**
    * Esta funcion consiste en inhabilitar una categoria, asi como las subcategorias que involucra
    * y asimismo, las marcas de esas subcategorias. Quedaran inhabilitadas para futuros estudios.
    * @author Gabriel Romero
    * @param _id corresponde al id de la categoria
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado y mensaje en caso de ocurrir 
    *         alguna de las excepciones
    */

    @DELETE
    @Path( "/delete/{id}" )
    public Response deleteCategoria(@PathParam("id") long  _id)
    {
        JsonObject resul;
        try
        {
            DeleteCategoriaComando comando=Fabrica.crearComandoConId(DeleteCategoriaComando.class,_id);
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
    * Esta funcion consiste en habilitar nuevamente una categoria previamente inhabilitada
    * @author Carlos Silva
    * @param _id corresponde al id de la categoria
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado y mensaje en caso de ocurrir 
    *         alguna de las excepciones
    */
    @DELETE
    @Path( "/activar/{id}" )
    public Response activarCategoria(@PathParam("id") long  _id)
    {
        JsonObject resul;
        try
        {
            ActivateCategoriaComando comando=Fabrica.crearComandoConId(ActivateCategoriaComando.class,_id);
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
    * Esta funcion consiste en editar una categoria.
    * @author Gabriel Romero
    * @param _id corresponde al id de la categoria
    * @param categoriaDto corresponde al objeto de la capa web que contiene los nuevos datos que se desean actualizar
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado y mensaje en caso de ocurrir 
    *         alguna de las excepciones
    */
    @PUT
    @Path( "/edit/{id}" )
    public Response editCategoria(@PathParam("id") long _id, CategoriaDto categoriaDto)
    {
        JsonObject resul;

        try
        {
            UpdateCategoriaComando comando=Fabrica.crearComandoBoth(UpdateCategoriaComando.class,_id,categoriaDto);
            comando.execute();

            return Response.status(Response.Status.OK).entity(comando.getResult()).build();

        }
        catch (PersistenceException | DatabaseException ex){

            ex.printStackTrace();
            resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje_soporte",ex.getMessage())
                    .add("mensaje","La categoria ya se encuestra registrada").build();

            return Response.status(Response.Status.BAD_REQUEST).entity(resul).build();

        }
        catch (Exception ex){
            ex.printStackTrace();
            resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje_soporte",ex.getMessage())
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }
    }

    /**
    * Esta funcion consiste obtener una categoria
    * @author Gabriel Romero
    * @param _id corresponde al id de la categoria
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado, categoria y mensaje en caso de ocurrir 
    *         alguna de las excepciones
    */
    @GET
    @Path( "/{id}" )
    public Response getCategoria(@PathParam("id") long  _id)
    {
        JsonObject resul;

        try
        {

            GetCategoriaComando comando=Fabrica.crearComandoConId(GetCategoriaComando.class,_id);
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
