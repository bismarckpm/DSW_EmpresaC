package ucab.dsw.servicio;

import org.eclipse.persistence.exceptions.DatabaseException;
import ucab.dsw.dtos.*;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.jwt.Jwt;
import ucab.dsw.logica.comando.tipo.*;
import ucab.dsw.logica.fabrica.Fabrica;
import javax.persistence.PersistenceException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path( "/tipo" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class TipoServicio {

    /**
     * Esta funcion consiste en agregar un tipo al sistema
     * @author Carlos Silva
     * @param tipoDto corresponde al objeto de la capa web que contiene los nuevos datos que se desean insertar
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */

    @POST
    @Path( "/add-tipo" )
    public Response addTipo(@HeaderParam("authorization") String token, TipoDto tipoDto)
    {
        TipoDto resultado = new TipoDto();
        JsonObject resul;
        try
        {
            if(Jwt.verificarToken(token)){
                InsertTipoComando comando=Fabrica.crearComandoConDto(InsertTipoComando.class,tipoDto);
                comando.execute();

                System.out.println(comando.getResult());
                return Response.status(Response.Status.OK).entity(comando.getResult()).build();
            }
            else{
                resul= Json.createObjectBuilder()
                        .add("estado","unauthorized")
                        .add("codigo","UNAUTH")
                        .add("mensaje","No se encuentra autenticado. Inicie sesión").build();

                return Response.status(Response.Status.UNAUTHORIZED).entity(resul).build();
            }

        }
        catch (PersistenceException | DatabaseException ex){
            ex.printStackTrace();
            resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje_soporte",ex.getMessage())
                    .add("mensaje","El tipo ya se encuestra registrada").build();

            return Response.status(Response.Status.BAD_REQUEST).entity(resul).build();
        }
        catch ( EmpresaException ex )
        {
            ex.printStackTrace();
            resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo",ex.getCodigo())
                    .add("mensaje",ex.getMensaje()).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(resul).build();
        }
        catch ( Exception ex){
            ex.printStackTrace();
            resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo","S-EX-TI01")
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();

        }

    }
    /**
     * Esta funcion consiste en cambiar algun elemento de un tipo
     * @author Carlos Silva
     * @param _id corresponde al id del tipo
     * @param tipoDto corresponde al objeto de la capa web que contiene los nuevos datos que se desean insertar
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @PUT
    @Path( "/channge-tipo/{id}" )
    public Response changeTipo(@HeaderParam("authorization") String token, @PathParam("id")long  _id,TipoDto tipoDto)
    {

        JsonObject resul;
        try
        {
            if(Jwt.verificarToken(token)){
                UpdateTipoComando comando=Fabrica.crearComandoBoth(UpdateTipoComando.class,_id,tipoDto);
                comando.execute();

                System.out.println(comando.getResult());
                return Response.status(Response.Status.OK).entity(comando.getResult()).build();
            }
            else{
                resul= Json.createObjectBuilder()
                        .add("estado","unauthorized")
                        .add("codigo","UNAUTH")
                        .add("mensaje","No se encuentra autenticado. Inicie sesión").build();

                return Response.status(Response.Status.UNAUTHORIZED).entity(resul).build();
            }

        }
        catch ( EmpresaException ex )
        {
            ex.printStackTrace();
            resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo",ex.getCodigo())
                    .add("mensaje",ex.getMensaje()).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(resul).build();
        }
        catch ( Exception ex){
            ex.printStackTrace();
            resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo","S-EX-TIP02")
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();

        }
    }
    /**
     * Esta funcion consiste en cambiar el estado de un tipo a inactivo lo que hace que tambien se
     * cambien los estados de las presentaciones y marcas que esten relacionadas a al tipo
     * @author Carlos Silva
     * @param _id corresponde al id del tipo
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @DELETE
    @Path( "/delete-tipo/{id}" )
    public Response EliminarTipo(@HeaderParam("authorization") String token, @PathParam("id")long  _id )
    {
        JsonObject resul;
        try
        {
            if(Jwt.verificarToken(token)){
                DeleteTipoComando comando=Fabrica.crearComandoConId(DeleteTipoComando.class,_id);
                comando.execute();

                System.out.println(comando.getResult());
                return Response.status(Response.Status.OK).entity(comando.getResult()).build();
            }
            else{
                resul= Json.createObjectBuilder()
                        .add("estado","unauthorized")
                        .add("codigo","UNAUTH")
                        .add("mensaje","No se encuentra autenticado. Inicie sesión").build();

                return Response.status(Response.Status.UNAUTHORIZED).entity(resul).build();
            }

        }
        catch ( EmpresaException ex )
        {
            ex.printStackTrace();
            resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo",ex.getCodigo())
                    .add("mensaje",ex.getMensaje()).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(resul).build();
        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
            resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo","S-EX-TI03")
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }

    }
    /**
     * Esta funcion consiste en cambiar el estado de un tipo a activo lo que hace que tambien se
     * cambien los estados de las presentaciones y marcas que esten relacionadas a al tipo
     * @author Carlos Silva
     * @param _id corresponde al id del tipo
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @DELETE
    @Path( "/activar-tipo/{id}" )
    public Response ActivarTipo(@HeaderParam("authorization") String token, @PathParam("id")long  _id )
    {
        JsonObject resul;

        try
        {
            if(Jwt.verificarToken(token)){
                ActivateTipoComando comando=Fabrica.crearComandoConId(ActivateTipoComando.class,_id);
                comando.execute();

                System.out.println(comando.getResult());
                return Response.status(Response.Status.OK).entity(comando.getResult()).build();
            }
            else{
                resul= Json.createObjectBuilder()
                        .add("estado","unauthorized")
                        .add("codigo","UNAUTH")
                        .add("mensaje","No se encuentra autenticado. Inicie sesión").build();

                return Response.status(Response.Status.UNAUTHORIZED).entity(resul).build();
            }

        }
        catch ( EmpresaException ex )
        {
            ex.printStackTrace();
            resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo",ex.getCodigo())
                    .add("mensaje",ex.getMensaje()).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(resul).build();
        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
            resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo","S-EX-TIP04")
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }

    }

    /**
     * Esta funcion consiste en mostrar los datos de un tipo en especifico
     * @author Carlos Silva
     * @param id corresponde al id del tipo
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @GET
    @Path( "/find-tipo/{id}" )
    public Response findTipo(@HeaderParam("authorization") String token, @PathParam("id") long id )
    {
        JsonObject resul;
        try {
            if(Jwt.verificarToken(token)){
                GetTipoComando comando=Fabrica.crearComandoConId(GetTipoComando.class,id);
                comando.execute();

                return Response.status(Response.Status.OK).entity(comando.getResult()).build();
            }
            else{
                resul= Json.createObjectBuilder()
                        .add("estado","unauthorized")
                        .add("codigo","UNAUTH")
                        .add("mensaje","No se encuentra autenticado. Inicie sesión").build();

                return Response.status(Response.Status.UNAUTHORIZED).entity(resul).build();
            }

        }
        catch ( EmpresaException ex )
        {
            ex.printStackTrace();
            resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo",ex.getCodigo())
                    .add("mensaje",ex.getMensaje()).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(resul).build();
        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
            resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo","S-EX-TIP05")
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }

    }
    /**
     * Esta funcion consiste en mostrar los datos de todos los tipos
     * @author Carlos Silva
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @GET
    @Path( "/findall-tipos" )
    public Response findAllTipo()
    {
        JsonObject resul;
        try {
            AllTipoComando comando= Fabrica.crear(AllTipoComando.class);
            comando.execute();

            return Response.status(Response.Status.OK).entity(comando.getResult()).build();

        }
        catch ( EmpresaException ex )
        {
            ex.printStackTrace();
            resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo",ex.getCodigo())
                    .add("mensaje",ex.getMensaje()).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(resul).build();
        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
            resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo","S-EX-TIP06")
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }

    }
}