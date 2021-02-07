package ucab.dsw.servicio;

import org.eclipse.persistence.exceptions.DatabaseException;

import ucab.dsw.dtos.*;
import ucab.dsw.excepciones.ContrasenaInvalidaExcepcion;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.excepciones.UsuarioExistenteExcepcion;
import ucab.dsw.jwt.Jwt;
import ucab.dsw.logica.comando.usuario.*;
import ucab.dsw.logica.fabrica.Fabrica;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.PersistenceException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Clase para la gestion del usuarios desde el panel del aministrador
 * @author Jesus Requena
 */
@Path( "/usuario" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class UsuarioServicio extends AplicacionBase {

    /**
     * Metodo para activar cuenta a un usuario
     * @author Jesus Requena
     * @param _id representa el id del usuario que se necesita activar
     * @return Response que incluye un estado de respuesta http (OK o BAD_REQUEST) para indicar si exectivamente
     *         se pudo completar la solicitud o si ocurrió un fallo en la comunicación.
     *         La respuesta posee adjunta un json que incluye (estado, codigo y/o mensaje)
     */

    @DELETE
    @Path( "/activar/{id}" )
    public Response activarUsuario(@HeaderParam("authorization") String token, @PathParam("id") long  _id)
    {
        try  {
            if(Jwt.verificarToken(token)){
                ActivarUsuarioComando comando = Fabrica.crearComandoConId(ActivarUsuarioComando.class ,_id);
                comando.execute();

                return Response.status(Response.Status.OK).entity( comando.getResult() ).build();
            } else {
                JsonObject data = Json.createObjectBuilder()
                        .add("estado","unauthorized")
                        .add("codigo","UNAUTH")
                        .add("mensaje","No se encuentra autenticado. Inicie sesión").build();

                return Response.status(Response.Status.UNAUTHORIZED).entity(data).build();
            }
        } catch ( EmpresaException ex ) {
            ex.printStackTrace();
            JsonObject data = Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo",ex.getCodigo())
                    .add("mensaje",ex.getMensaje()).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();

        } catch ( Exception ex ){
            ex.printStackTrace();
            JsonObject data = Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo","S-EX-US01")
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(data).build();
        }
    }

    /**
     * Metodo para añadrir un nuevo administrador
     * despues de verificar que no existen coincidencias con el registro en la base de datos
     * @author Jesus Requena
     * @param nuevoUsuarioDto precargado con el conjunto de datos a ingresar en el registro
     * @return Response que incluye un estado de respuesta http (OK o BAD_REQUEST) para indicar si exectivamente
     *         se pudo completar la solicitud o si ocurrió un fallo en la comunicación.
     *         La respuesta posee adjunta un json que incluye (estado codigo y mensaje)
     */
    @POST
    @Path( "/add/admin" )
    public Response AddAdmin(@HeaderParam("authorization") String token, NuevoUsuarioDto nuevoUsuarioDto) {
        try {
            if(Jwt.verificarToken(token)){
                AddAdminComando comando = Fabrica.crearComandoConDto(AddAdminComando.class, nuevoUsuarioDto);
                comando.execute();

                return Response.status(Response.Status.OK).entity(comando.getResult()).build();
            } else {
                JsonObject data = Json.createObjectBuilder()
                        .add("estado","unauthorized")
                        .add("codigo","UNAUTH")
                        .add("mensaje","No se encuentra autenticado. Inicie sesión").build();

                return Response.status(Response.Status.UNAUTHORIZED).entity(data).build();
            }
        } catch ( EmpresaException ex ) {
            ex.printStackTrace();
            JsonObject data = Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo",ex.getCodigo())
                    .add("mensaje",ex.getMensaje()).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();

        } catch ( Exception ex ){
            ex.printStackTrace();
            JsonObject data= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo","S-EX-US02")
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(data).build();
        }
    }

    /**
     * Metodo para añadrir un nuevo analista
     * despues de verificar que no existen coincidencias con el registro en la base de datos
     * @author Jesus Requena
     * @param nuevoUsuarioDto precargado con el conjunto de datos a ingresar en el registro
     * @return Response que incluye un estado de respuesta http (OK o BAD_REQUEST) para indicar si exectivamente
     *         se pudo completar la solicitud o si ocurrió un fallo en la comunicación.
     *         La respuesta posee adjunta un json que incluye (estado codigo y mensaje)
     */
    @POST
    @Path( "/add/analista" )
    public Response AddAnalista(@HeaderParam("authorization") String token, NuevoUsuarioDto nuevoUsuarioDto) {
        try {
            if(Jwt.verificarToken(token)){
                AddAnalistaComando comando = Fabrica.crearComandoConDto(AddAnalistaComando.class, nuevoUsuarioDto);
                comando.execute();

                return Response.status(Response.Status.OK).entity(comando.getResult()).build();
            } else {
                JsonObject data = Json.createObjectBuilder()
                        .add("estado","unauthorized")
                        .add("codigo","UNAUTH")
                        .add("mensaje","No se encuentra autenticado. Inicie sesión").build();

                return Response.status(Response.Status.UNAUTHORIZED).entity(data).build();
            }
        } catch ( EmpresaException ex ) {
            ex.printStackTrace();
            JsonObject data = Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo",ex.getCodigo())
                    .add("mensaje",ex.getMensaje()).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();

        } catch ( Exception ex ){
            ex.printStackTrace();
            JsonObject data= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo","S-EX-US03")
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(data).build();
        }
    }

    /**
     * Metodo para añadrir un nuevo cliente
     * despues de verificar que no existen coincidencias con el registro en la base de datos
     * @author Jesus Requena
     * @param clienteDto precargado con el conjunto de datos a ingresar en el registro
     * @return Response que incluye un estado de respuesta http OK  para indicar si exectivamente
     *         se pudo completar la solicitud.
     *         La respuesta posee adjunta un json que incluye (estado codigo y mensaje)
     */
    @POST
    @Path( "/add/cliente" )
    public Response AddCliente(@HeaderParam("authorization") String token, ClienteDto clienteDto)
    {
        try {
            if(Jwt.verificarToken(token)){
                AddClienteComando comando = Fabrica.crearComandoConDto(AddClienteComando.class,clienteDto);
                comando.execute();

                return Response.status(Response.Status.OK).entity(comando.getResult()).build();
            } else {
                JsonObject data = Json.createObjectBuilder()
                        .add("estado","unauthorized")
                        .add("codigo","UNAUTH")
                        .add("mensaje","No se encuentra autenticado. Inicie sesión").build();

                return Response.status(Response.Status.UNAUTHORIZED).entity(data).build();
            }
        } catch ( EmpresaException ex ) {
            ex.printStackTrace();
            JsonObject data = Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo",ex.getCodigo())
                    .add("mensaje",ex.getMensaje()).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();

        } catch ( Exception ex ){
            ex.printStackTrace();
            JsonObject data= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo","S-EX-US04")
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(data).build();
        }
    }

    /**
     * Metodo para añadrir un nuevo encuestado
     * despues de verificar que no existen coincidencias con el registro en la base de datos
     * @author Jesus Requena
     * @param nuevoEncuestadoDto precargado con el conjunto de datos a ingresar en el registro
     * @return Response que incluye un estado de respuesta http (OK, UNAUTHHORIZED o BAD_REQUEST) para
     *         indicar si exectivamente se pudo completar la solicitud, se encontro un usuario o correo electronico
     *         ya registrado u ocurrió un fallo en la comunicación.
     *         La respuesta posee adjunta un json que incluye (estado codigo y mensaje)
     */
    @POST
    @Path( "/add/encuestado" )
    public Response AddEncuestado(@HeaderParam("authorization") String token, NuevoEncuestadoDto nuevoEncuestadoDto ){
        try {
            if(Jwt.verificarToken(token)){
                AddEncuestadoComando comando = Fabrica.crearComandoConDto(AddEncuestadoComando.class, nuevoEncuestadoDto);
                comando.execute();

                return Response.status(Response.Status.OK).entity(comando.getResult()).build();
            } else {
                JsonObject data = Json.createObjectBuilder()
                        .add("estado","unauthorized")
                        .add("codigo","UNAUTH")
                        .add("mensaje","No se encuentra autenticado. Inicie sesión").build();

                return Response.status(Response.Status.UNAUTHORIZED).entity(data).build();
            }
        } catch ( EmpresaException ex ) {
            ex.printStackTrace();
            JsonObject data = Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo",ex.getCodigo())
                    .add("mensaje",ex.getMensaje()).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();

        } catch ( Exception ex ){
            ex.printStackTrace();
            JsonObject data= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo","S-EX-US05")
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(data).build();
        }
    }

    /**
     * Metodo para editar los datos de un usuario administrador
     * @author Jesus Requena
     * @param usuarioLdapDto precargado con el conjunto de datos a modificar en el registro
     * @param _id con el id del usuario a modificar
     * @return Response que incluye un estado de respuesta http OK  para indicar si exectivamente
     *      se pudo completar la solicitud.
     *      La respuesta posee adjunta un json que incluye (estado codigo y mensaje)
     */
    @PUT
    @Path( "/edit/{id}" )
    public Response changeAdmin(@HeaderParam("authorization") String token, @PathParam("id")long  _id, UsuarioLdapDto usuarioLdapDto) {

        try {
            if(Jwt.verificarToken(token)){
                ChangeAdminComando comando = Fabrica.crearComandoBoth(ChangeAdminComando.class, _id, usuarioLdapDto );
                comando.execute();

                return Response.status(Response.Status.OK).entity(comando.getResult()).build();
            } else {
                JsonObject data = Json.createObjectBuilder()
                        .add("estado","unauthorized")
                        .add("codigo","UNAUTH")
                        .add("mensaje","No se encuentra autenticado. Inicie sesión").build();

                return Response.status(Response.Status.UNAUTHORIZED).entity(data).build();
            }
        } catch ( EmpresaException ex ) {
            ex.printStackTrace();
            JsonObject data = Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo",ex.getCodigo())
                    .add("mensaje",ex.getMensaje()).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();

        } catch ( Exception ex ){
            ex.printStackTrace();
            JsonObject data= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo","S-EX-US06")
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(data).build();
        }
    }

    /**
     * Metodo para editar los datos de un usuario tipo cliente
     * @author Jesus Requena
     * @param clienteDto precargado con el conjunto de datos a modificar en el registro
     * @param _id con el id del usuario a modificar
     * @return Response que incluye un estado de respuesta http OK  para indicar si exectivamente
     *      se pudo completar la solicitud.
     *      La respuesta posee adjunta un json que incluye (estado codigo y mensaje)
     */
    @PUT
    @Path( "/cliente/edit/{id}" )
    public Response changeCliente(@HeaderParam("authorization") String token, @PathParam("id")long  _id, ClienteDto clienteDto) {
        try {
            if(Jwt.verificarToken(token)){
                ChangeClienteComando comando = Fabrica.crearComandoBoth(ChangeClienteComando.class, _id, clienteDto);
                comando.execute();

                return Response.status(Response.Status.OK).entity(comando.getResult()).build();
            } else {
                JsonObject data = Json.createObjectBuilder()
                        .add("estado","unauthorized")
                        .add("codigo","UNAUTH")
                        .add("mensaje","No se encuentra autenticado. Inicie sesión").build();

                return Response.status(Response.Status.UNAUTHORIZED).entity(data).build();
            }
        } catch ( EmpresaException ex ) {
            ex.printStackTrace();
            JsonObject data = Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo",ex.getCodigo())
                    .add("mensaje",ex.getMensaje()).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();

        } catch ( Exception ex ){
            ex.printStackTrace();
            JsonObject data= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo","S-EX-US07")
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(data).build();
        }
    }

    /**
     * Metodo para modificar la contraseña de un usuario existente despues de verificar la contraseña actual
     * @author Jesus Requena
     * @param cambiarClaveDto el cual incluye el usuario a modificar, la conrtraseña actual y la nueva
     * @return Response que incluye un estado de respuesta http (OK o BAD_REQUEST) para indicar si exectivamente
     *         se pudo completar la solicitud o si ocurrió un fallo en la comunicación.
     *         La respuesta posee adjunta un json que incluye (estado y codigo)
     */
    @POST
    @Path( "/change-password" )
    public Response ChangePassword(@HeaderParam("authorization") String token, CambiarClaveDto cambiarClaveDto) {

        try {
            if(Jwt.verificarToken(token)){
                ChangePasswordComando comando = Fabrica.crearComandoConDto(ChangePasswordComando.class, cambiarClaveDto);
                comando.execute();

                return Response.status(Response.Status.OK).entity(comando.getResult()).build();
            } else {
                JsonObject data = Json.createObjectBuilder()
                        .add("estado","unauthorized")
                        .add("codigo","UNAUTH")
                        .add("mensaje","No se encuentra autenticado. Inicie sesión").build();

                return Response.status(Response.Status.UNAUTHORIZED).entity(data).build();
            }
        } catch ( EmpresaException ex ) {
            ex.printStackTrace();
            JsonObject data = Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo",ex.getCodigo())
                    .add("mensaje",ex.getMensaje()).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();

        } catch ( Exception ex ){
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            JsonObject data= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo","S-EX-US08")
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(data).build();
        }
    }

    /**
     * Metodo para inhabilitar a un usuario
     * @author Jesus Requena
     * @param _id con el id del usuario a inhabilitar
     * @param usuarioDto con los datos del usuario a inhabilitar
     * @return Response que incluye un estado de respuesta http OK  para indicar si exectivamente
     *         se pudo completar la solicitud.
     *         La respuesta posee adjunta un json que incluye (estado codigo y mensaje)
     */
    @PUT
    @Path( "/delete/{id}" )
    public Response deleteUser(@HeaderParam("authorization") String token, @PathParam("id")long  _id, UsuarioDto usuarioDto) {

        try {
            if(Jwt.verificarToken(token)){
                DeleteUserComando comando = Fabrica.crearComandoBoth(DeleteUserComando.class, _id, usuarioDto);
                comando.execute();

                return Response.status(Response.Status.OK).entity(comando.getResult()).build();
            } else {
                JsonObject data = Json.createObjectBuilder()
                        .add("estado","unauthorized")
                        .add("codigo","UNAUTH")
                        .add("mensaje","No se encuentra autenticado. Inicie sesión").build();

                return Response.status(Response.Status.UNAUTHORIZED).entity(data).build();
            }
        } catch ( EmpresaException ex ) {
            ex.printStackTrace();
            JsonObject data = Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo",ex.getCodigo())
                    .add("mensaje",ex.getMensaje()).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();

        } catch ( Exception ex ){
            ex.printStackTrace();
            JsonObject data= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo","S-EX-US09")
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(data).build();
        }
    }

    /**
     * Metodo para obtener a todos los usuarios registrados en el Directorio Activo
     * @author Jesus Requena
     * @return Response que incluye un estado de respuesta http (OK o BAD_REQUEST) para indicar si exectivamente
     *         se pudo completar la solicitud o si ocurrió un fallo en la comunicación.
     *         La respuesta posee adjunta un json que incluye (estado codigo, mensaje
     *         y el arreglo con todos los usuarios y sus respectivos datos)
     */
    @GET
    @Path( "/all" )
    public Response findAllUsers(@HeaderParam("authorization") String token){
        try {
            if(Jwt.verificarToken(token)){
                FindAllUsersComando comando = Fabrica.crear(FindAllUsersComando.class);
                comando.execute();

                return Response.status(Response.Status.OK).entity(comando.getResult()).build();
            } else {
                JsonObject data = Json.createObjectBuilder()
                        .add("estado","unauthorized")
                        .add("codigo","UNAUTH")
                        .add("mensaje","No se encuentra autenticado. Inicie sesión").build();

                return Response.status(Response.Status.UNAUTHORIZED).entity(data).build();
            }
        } catch ( EmpresaException ex ) {
            ex.printStackTrace();
            JsonObject data = Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo",ex.getCodigo())
                    .add("mensaje",ex.getMensaje()).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();

        } catch ( Exception ex ){
            ex.printStackTrace();
            JsonObject data= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo","S-EX-US10")
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(data).build();
        }
    }

    /**
     * Metodo para obtener a un cliente a partir de su id y retornar sus datos
     * @author Jesus Requena
     * @param _id con el id del cliente solicitado
     * @return Response que incluye un estado de respuesta http (OK o BAD_REQUEST) para indicar si exectivamente
     *         se pudo completar la solicitud o si ocurrió un fallo en la comunicación.
     *         La respuesta posee adjunta un json que incluye (estado, codigo
     *         y los datos del cliente)
     */
    @GET
    @Path( "/get-cliente/{id}" )
    public Response findCliente(@HeaderParam("authorization") String token, @PathParam("id")long  _id){
        try {
            if(Jwt.verificarToken(token)){
                FindClienteComando comando = Fabrica.crearComandoConId(FindClienteComando.class, _id);
                comando.execute();

                return Response.status(Response.Status.OK).entity(comando.getResult()).build();
            } else {
                JsonObject data = Json.createObjectBuilder()
                        .add("estado","unauthorized")
                        .add("codigo","UNAUTH")
                        .add("mensaje","No se encuentra autenticado. Inicie sesión").build();

                return Response.status(Response.Status.UNAUTHORIZED).entity(data).build();
            }
        } catch ( EmpresaException ex ) {
            ex.printStackTrace();
            JsonObject data = Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo",ex.getCodigo())
                    .add("mensaje",ex.getMensaje()).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();

        } catch ( Exception ex ){
            ex.printStackTrace();
            JsonObject data = Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo","S-EX-US11")
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(data).build();
        }
    }
}
