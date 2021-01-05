package ucab.dsw.servicio;
import org.eclipse.persistence.exceptions.DatabaseException;
import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.*;
import ucab.dsw.entidades.*;

import javax.persistence.PersistenceException;
import javax.validation.constraints.Null;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.core.Response;
import javax.json.JsonObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path( "/presentacion" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class Cruds_presntacion {

    /**
     * Esta funcion consiste agregar una nueva presentacion
     * @author Carlos Silva
     * @param presentacionDto corresponde al objeto de la capa web que contiene los nuevos datos que se desean insertar
     * @throws Exception si ocurre cualquier excepcion general no controlada previamente
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */

    @POST
    @Path( "/add-presentacion" )
    public Response addPresentacion(PresentacionDto presentacionDto)
    {
        JsonObject data;
        PresentacionDto resultado = new PresentacionDto();

        try
        {
            DaoPresentacion dao = new DaoPresentacion();
            Presentacion presentacion= new Presentacion();
            presentacion.set_nombre( presentacionDto.getNombre() );

            Tipo tipo =new Tipo(presentacionDto.getTipoDto().getId());
            presentacion.set_tipo( tipo );
            presentacion.set_estado("activo");

            Presentacion resul = dao.insert( presentacion);
            resultado.setId( resul.get_id() );


            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200).build();

        }
        catch (PersistenceException | DatabaseException ex){
            data= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje","La presentacion ya se encuestra registrada")
                    .add("codigo",500).build();

            System.out.println(data);
            return Response.status(Response.Status.OK).entity(data).build();
        }
        catch ( Exception ex){
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
     * Esta funcion consiste en cambiar algun elemento de una presentacion
     * @author Carlos Silva
     * @param _id corresponde al id de la presentacion
     * @param presentacionDto corresponde al objeto de la capa web que contiene los nuevos datos que se desean insertar
     * @throws Exception si ocurre cualquier excepcion general no controlada previamente
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @PUT
    @Path( "/channge-presentacion/{id}" )
    public Response changePresentacion(@PathParam("id")long  _id,PresentacionDto presentacionDto)
    {
        PresentacionDto resultado = new PresentacionDto();
        JsonObject data;
        try
        {
            DaoPresentacion dao = new DaoPresentacion();
            Presentacion presentacion= dao.find(_id, Presentacion.class);
            Tipo tipo=new Tipo(presentacionDto.getTipoDto().getId());

            presentacion.set_nombre( presentacionDto.getNombre() );
            presentacion.set_tipo(tipo);

            Presentacion resul = dao.update( presentacion);
            resultado.setId( resul.get_id() );

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200).build();


        }
        catch (PersistenceException | DatabaseException ex){
            data= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje","La presentacion ya se encuestra registrada")
                    .add("codigo",500).build();

            System.out.println(data);
            return Response.status(Response.Status.OK).entity(data).build();
        }
        catch ( Exception ex){
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
     * Esta funcion consiste en cambiar el estado de una presentacion a inactivo
     * @author Carlos Silva
     * @param _id corresponde al id de la presentacion
     * @throws Exception si ocurre cualquier excepcion general no controlada previamente
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @DELETE
    @Path( "/delete-presentacion/{id}" )
    public Response EliminarPresentacion( @PathParam("id")long  _id )
    {
        JsonObject data;
        PresentacionDto resultado = new PresentacionDto();
        try
        {
            DaoPresentacion dao = new DaoPresentacion ();
            Presentacion presentacion = dao.find(_id,Presentacion.class);

            presentacion.set_estado("inactivo");

            Presentacion resul = dao.update(presentacion);
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
    /**
     * Esta funcion consiste en cambiar el estado de una presentacion a activo
     * @author Carlos Silva
     * @param _id corresponde al id de la presentacion
     * @throws Exception si ocurre cualquier excepcion general no controlada previamente
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @DELETE
    @Path( "/activar-presentacion/{id}" )
    public Response ActivarPresentacion( @PathParam("id")long  _id )
    {
        JsonObject data;
        PresentacionDto resultado = new PresentacionDto();
        try
        {
            DaoPresentacion dao = new DaoPresentacion ();
            Presentacion presentacion = dao.find(_id,Presentacion.class);

            presentacion.set_estado("activo");

            Presentacion resul = dao.update(presentacion);
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
    /**
     * Esta funcion consiste en mostrar los datos de una presentacion en especifico
     * @author Carlos Silva
     * @param id corresponde al id de la presentacion
     * @throws Exception si ocurre cualquier excepcion general no controlada previamente
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @GET
    @Path( "/find-presentacion/{id}" )
    public Response findPresentacion( @PathParam("id")long id )
    {
        JsonObject data;
        JsonObject PresentacionJson;
        try {
            DaoPresentacion dao = new DaoPresentacion();
            Presentacion resul = dao.find( id,Presentacion.class );

            PresentacionJson= Json.createObjectBuilder()
                    .add("id: ",resul.get_id())
                    .add("nombre: ",resul.get_nombre())
                    .add("tipo: ",resul.get_tipo().get_nombre()).build();

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200)
                    .add("categoria",PresentacionJson).build();

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
    /**
     * Esta funcion consiste en mostrar los datos de todas las presentaciones
     * @author Carlos Silva
     * @throws Exception si ocurre cualquier excepcion general no controlada previamente
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @GET
    @Path( "/findall-presentaciones" )
    public Response findAllPresentacion( )
    {
        JsonObject data;
        try {
            List<Presentacion> resultado= null;
            Class<Presentacion> type = Presentacion.class;

            DaoPresentacion dao = new DaoPresentacion();
			DaoTipo daoTipo = new DaoTipo();
			
            resultado = dao.findAll( type );
            JsonArrayBuilder presentacionArrayJson= Json.createArrayBuilder();

            for(Presentacion obj: resultado) {

				Tipo tipo=daoTipo.find(obj.get_tipo().get_id(),Tipo.class);

                JsonObject p = Json.createObjectBuilder().add("id",obj.get_id())
                                                         .add("nombre",obj.get_nombre())
                                                         .add("tipo",tipo.get_nombre())
                                                         .add("tipo_id",tipo.get_id())
														 .add("estado",obj.get_estado()).build();

                presentacionArrayJson.add(p);

            }


            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200)
                    .add("presentaciones",presentacionArrayJson).build();

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
        System.out.println(data);
        return Response.status(Response.Status.OK).entity(data).build();
    }
}
