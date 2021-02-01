package ucab.dsw.servicio;

import org.eclipse.persistence.exceptions.DatabaseException;
import ucab.dsw.dtos.*;
import ucab.dsw.logica.comando.marca.*;
import ucab.dsw.logica.fabrica.Fabrica;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.PersistenceException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Una clase para la administracion completa de las marcas de MERCADEOUCAB
 * @version 1.0, 02/01/2021
 * @author Gabriel Romero
 * @author Carlos Silva
 */
@Path( "/marca" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class MarcaServicio extends AplicacionBase{

    /**
    * Esta funcion consiste el traer todas las marcas disponibles
    * @author Gabriel Romero
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado, marcas (array de objetos) 
    *         y mensaje en caso de ocurrir alguna de las excepciones
    */
    @GET
    @Path( "/all" )
    public Response getAllMarcas()
    {
        JsonObject resul;
        try
        {
            AllMarcaComando comando=Fabrica.crear(AllMarcaComando.class);
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
    * Esta funcion consiste en insertar una nueva marca
    * @author Gabriel Romero
    * @param marcaDto corresponde al objeto de la capa web que contiene los nuevos datos que se desean insertar
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado y mensaje en caso de ocurrir
    *         alguna de las excepciones
    */
    @POST
    @Path( "/add" )
    public Response addMarca(MarcaDto marcaDto)
    {
        JsonObject resul;

        try
        {
            InsertMarcaComando comando= Fabrica.crearComandoConDto(InsertMarcaComando.class,marcaDto);
            comando.execute();

            System.out.println(comando.getResult());
            return Response.status(Response.Status.OK).entity(comando.getResult()).build();
        }
        catch (PersistenceException | DatabaseException ex){
            ex.printStackTrace();

            resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje_soporte",ex.getMessage())
                    .add("mensaje","La marca ya se encuestra registrada").build();

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
    * Esta funcion consiste en inhabilitar una marca. Quedaran inhabilitadas para futuros estudios.
    * @author Gabriel Romero
    * @param _id corresponde al id de la marca
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado y mensaje en caso de ocurrir 
    *         alguna de las excepciones
    */
    @DELETE
    @Path( "/delete/{id}" )
    public Response deleteMarca(@PathParam("id") long  _id)
    {
        JsonObject resul;

        try
        {

            DeleteMarcaComando comando=Fabrica.crearComandoConId(DeleteMarcaComando.class,_id);
            comando.execute();

            System.out.println(comando.getResult());
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
    * Esta funcion consiste en habilitar nuevamente una marca previamente inhabilitada
    * @author Carlos Silva
    * @param _id corresponde al id de la marca
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado y mensaje en caso de ocurrir 
    *         alguna de las excepciones
    */

    @DELETE
    @Path( "/activar/{id}" )
    public Response activarMarca(@PathParam("id") long  _id)
    {
        JsonObject resul;
        try
        {
            ActivateMarcaComando comando=Fabrica.crearComandoConId(ActivateMarcaComando.class,_id);
            comando.execute();

            System.out.println(comando.getResult());
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
    * Esta funcion consiste en editar una marca.
    * @author Gabriel Romero
    * @param _id corresponde al id de la marca
    * @param marcaDto corresponde al objeto de la capa web que contiene los nuevos datos que se desean actualizar
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado y mensaje en caso de ocurrir 
    *         alguna de las excepciones
    */
    @PUT
    @Path( "/edit/{id}" )
    public Response editMarca(@PathParam("id") long _id, MarcaDto marcaDto)
    {
        JsonObject resul;
        try
        {
            UpdateMarcaComando comando=Fabrica.crearComandoBoth(UpdateMarcaComando.class, _id,marcaDto);
            comando.execute();

            System.out.println(comando.getResult());
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
    * Esta funcion consiste obtener una marca
    * @author Gabriel Romero
    * @param _id corresponde al id de la marca
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado, marca y mensaje en caso de ocurrir 
    *         alguna de las excepciones
    */
    @GET
    @Path( "/{id}" )
    public Response getMarca(@PathParam("id") long  _id)
    {
        JsonObject resul;

        try
        {
            GetMarcaComando comando=Fabrica.crearComandoConId(GetMarcaComando.class,_id);
            comando.execute();

            System.out.println(comando.getResult());
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
    * Esta funcion consiste obtener una marcas por subcategoria
    * @author Gabriel Romero
    * @param _id corresponde al id de la subcategoria
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado, marcasBySubcategoria y mensaje en caso de ocurrir 
    *         alguna de las excepciones
    */
    @GET
    @Path( "/by/subcategoria/{id}" )
    public Response getMarcaBySubcategoriaId(@PathParam("id") long  _id)
    {
        JsonObject resul;

        try
        {
            MarcasBySubComando comando=Fabrica.crearComandoConId(MarcasBySubComando.class,_id);
            comando.execute();

            System.out.println(comando.getResult());
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
