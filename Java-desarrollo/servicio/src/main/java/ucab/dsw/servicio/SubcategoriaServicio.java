package ucab.dsw.servicio;

import ucab.dsw.dtos.SubcategoriaDto;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.jwt.Jwt;
import ucab.dsw.logica.comando.subcategoria.*;
import ucab.dsw.logica.fabrica.Fabrica;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Una clase para la administracion completa de las subcategorias de MERCADEOUCAB
 * @version 1.0, 02/01/2021
 * @author Gabriel Romero
 * @author Carlos Silva
 */
@Path( "/subcategoria" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class SubcategoriaServicio extends AplicacionBase{

    /**
    * Esta funcion consiste el traer todas las subcategorias disponibles
    * @author Gabriel Romero
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado, subcategorias (array de objetos) 
    *         y mensaje en caso de ocurrir alguna de las excepciones
    */
    @GET
    @Path( "/all" )
    public Response getAllSubcategorias()
    {
        JsonObject resul;
        try {
            AllSubcategoriaComando comando= Fabrica.crear(AllSubcategoriaComando.class);
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
                    .add("codigo","S-EX-SUB01")
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }

    }
    /**
     * Esta funcion consiste en ingresar una subcategoria
     * @author Carlos Silva
     * @param subcategoriaDto corresponde al objeto de la capa web que contiene los nuevos datos que se desean insertar
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado y mensaje en caso de ocurrir
     *         alguna de las excepciones
     */
    @POST
    @Path( "/add" )
    public Response addSubcategoria(@HeaderParam("authorization") String token,SubcategoriaDto subcategoriaDto)
    {
        JsonObject resul;
        try
        {
            if(Jwt.verificarToken(token)){
                InsertSubcategoriaComando comando=Fabrica.crearComandoConDto(InsertSubcategoriaComando.class,subcategoriaDto);
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
                    .add("codigo","S-EX-SUB02")
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }
    }
    /**
     * Esta funcion consiste en cambiar el estado de una subcategoria a inactivo
     * @author Carlos Silva
     * @param _id corresponde al id de la subcategoria
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado y mensaje en caso de ocurrir
     *         alguna de las excepciones
     */
    @DELETE
    @Path( "/delete/{id}" )
    public Response deleteSubcategoria(@HeaderParam("authorization") String token,@PathParam("id") long  _id)
    {
        JsonObject resul;
        try
        {
            if(Jwt.verificarToken(token)){
                DeleteSubcategoriaComando comando=Fabrica.crearComandoConId(DeleteSubcategoriaComando.class,_id);
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
                    .add("codigo","S-EX-SUB03")
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }
    }
    /**
     * Esta funcion consiste en cambiar el estado de una subcategoria a activo
     * @author Carlos Silva
     * @param _id corresponde al id de la subcategoria
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado y mensaje en caso de ocurrir
     *         alguna de las excepciones
     */
    @DELETE
    @Path( "/activar/{id}" )
    public Response activarSubcategoria(@HeaderParam("authorization") String token,@PathParam("id") long  _id)
    {
        JsonObject resul;
        try
        {
            if(Jwt.verificarToken(token)){
                ActivateSubcategoriaComando comando=Fabrica.crearComandoConId(ActivateSubcategoriaComando.class,_id);
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
                    .add("codigo","S-EX-SUB04")
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }
    }

    /**
     * Esta funcion consiste en cambiar algun elemento de una subcategoria
     * @author Carlos Silva
     * @param _id corresponde al id de la subcategoria
     * @param subcategoriaDto corresponde al objeto de la capa web que contiene los nuevos datos que se desean insertar
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado y mensaje en caso de ocurrir
     *         alguna de las excepciones
     */
    @PUT
    @Path( "/edit/{id}" )
    public Response editSubcategoria(@HeaderParam("authorization") String token,@PathParam("id") long _id, SubcategoriaDto subcategoriaDto)
    {
        JsonObject resul;
        try
        {
            if(Jwt.verificarToken(token)){
                UpdateSubcategoriaComando comando=Fabrica.crearComandoBoth(UpdateSubcategoriaComando.class,_id,subcategoriaDto);
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
                    .add("codigo","S-EX-SUB05")
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }
    }
    /**
     * Esta funcion consiste en enviar los datos de una subcategoria en especifico
     * @author Carlos Silva
     * @param _id corresponde al id de la subcategoria
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado y mensaje en caso de ocurrir
     *         alguna de las excepciones
     */
    @GET
    @Path( "/{id}" )
    public Response getSubcategoria(@HeaderParam("authorization") String token,@PathParam("id") long  _id)
    {
        JsonObject resul;
        try
        {
            if(Jwt.verificarToken(token)){
                GetSubcategoriaComando comando=Fabrica.crearComandoConId(GetSubcategoriaComando.class,_id);
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
                    .add("codigo","S-EX-SUB06")
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }

    }

        /**
         * Esta funcion consiste en obtener las subcategoria por categoria
         * @author Gabriel Romero
         * @param _id corresponde al id de la categoria
         * @return retorna una Response con un estado de respuesta http indicando si la operacion
         *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
         *         en formato JSON con los siguiente atributos: codigo, estado y subcategoriasByCategoria
         */
        @GET
        @Path( "/by/categoria/{id}" )
        public Response getSubcategoriasByCategoriaId(@HeaderParam("authorization") String token,@PathParam("id") long  _id)
        {
            JsonObject resul;

            try
            {
                if(Jwt.verificarToken(token)){
                    GetSubcategoriaBComando comando=Fabrica.crearComandoConId(GetSubcategoriaBComando.class,_id);
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
                        .add("codigo","S-EX-SUB07")
                        .add("mensaje","Ha ocurrido un error con el servidor").build();

                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
            }


        }
}
