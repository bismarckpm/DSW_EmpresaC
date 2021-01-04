package ucab.dsw.servicio;
import org.eclipse.persistence.exceptions.DatabaseException;
import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.*;
import ucab.dsw.entidades.*;
import ucab.dsw.excepciones.PruebaExcepcion;

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
import javax.json.JsonObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path( "/tipo" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class crud_tipo {

    /**
     * Esta funcion consiste en agregar un tipo al sistema
     * @author Carlos Silva
     * @param tipoDto corresponde al objeto de la capa web que contiene los nuevos datos que se desean insertar
     * @throws Exception si ocurre cualquier excepcion general no controlada previamente
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */

    @POST
    @Path( "/add-tipo" )
    public Response addTipo(TipoDto tipoDto)
    {
        TipoDto resultado = new TipoDto();
        JsonObject data;
        try
        {
            DaoTipo dao = new DaoTipo();
            Tipo tipo= new Tipo();
            tipo.set_nombre( tipoDto.getNombre() );
            tipo.set_estado("activo");

            Tipo resul = dao.insert( tipo);
            resultado.setId( resul.get_id() );

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200).build();

        }
        catch (PersistenceException | DatabaseException ex){
            data= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje","El tipo ya se encuestra registrado")
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
     * Esta funcion consiste en cambiar algun elemento de un tipo
     * @author Carlos Silva
     * @param _id corresponde al id del tipo
     * @param tipoDto corresponde al objeto de la capa web que contiene los nuevos datos que se desean insertar
     * @throws Exception si ocurre cualquier excepcion general no controlada previamente
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @PUT
    @Path( "/channge-tipo/{id}" )
    public Response changeTipo(@PathParam("id")long  _id,TipoDto tipoDto)
    {
        TipoDto resultado = new TipoDto();
        JsonObject data;
        try
        {
            DaoTipo dao = new DaoTipo();
            Tipo tipo = dao.find(_id,Tipo.class);
            tipo.set_nombre(tipoDto.getNombre());

            Tipo resul = dao.update( tipo);
            resultado.setId( resul.get_id() );

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200).build();


        }
        catch (PersistenceException | DatabaseException ex){
            data= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje","El tipo ya se encuestra registrado")
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

        return Response.status(Response.Status.OK).entity(data).build();
    }
    /**
     * Esta funcion consiste en cambiar el estado de un tipo a inactivo lo que hace que tambien se
     * cambien los estados de las presentaciones y marcas que esten relacionadas a al tipo
     * @author Carlos Silva
     * @param _id corresponde al id del tipo
     * @throws Exception si ocurre cualquier excepcion general no controlada previamente
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @DELETE
    @Path( "/delete-tipo/{id}" )
    public Response EliminarTipo( @PathParam("id")long  _id )
    {
        JsonObject data;
        TipoDto resultado = new TipoDto();
        try
        {
            DaoTipo dao = new DaoTipo ();
            Tipo tipo = dao.find(_id,Tipo.class);

            tipo.set_estado("inactivo");

            Tipo resul = dao.update(tipo);
            resultado.setId( resul.get_id() );

            List<Presentacion> resultado2= null;
            Class<Presentacion> type = Presentacion.class;

            DaoPresentacion dao2 = new DaoPresentacion();
            resultado2 = dao2.findAll( type );

            for(Presentacion obj: resultado2) {

                if (obj.get_tipo().get_id() == resul.get_id()){
                    DaoPresentacion dao3 = new DaoPresentacion();
                    PresentacionDto resultado3 = new PresentacionDto();
                    Presentacion presentacion = dao3.find(obj.get_id(), Presentacion.class);

                    presentacion.set_estado("inactivo");

                    Presentacion resul2 = dao3.update(presentacion);
                    resultado3.setId( resul2.get_id() );
                }



            }
            List<Marca_Tipo> resultado4= null;
            Class<Marca_Tipo> type2 = Marca_Tipo.class;

            DaoMarca_Tipo dao4 = new DaoMarca_Tipo();
            resultado4 = dao4.findAll( type2 );
            for(Marca_Tipo obj: resultado4) {

                if (obj.get_tipo().get_id() == resul.get_id()){
                    DaoMarca dao5 = new DaoMarca();
                    MarcaDto resultado5 = new MarcaDto();
                    Marca marca = dao5.find(obj.get_marca().get_id(), Marca.class);

                    marca.set_estado("inactivo");

                    Marca resul3 = dao5.update(marca);
                    resultado5.setId( resul3.get_id() );
                }



            }
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
     * Esta funcion consiste en cambiar el estado de un tipo a activo lo que hace que tambien se
     * cambien los estados de las presentaciones y marcas que esten relacionadas a al tipo
     * @author Carlos Silva
     * @param _id corresponde al id del tipo
     * @throws Exception si ocurre cualquier excepcion general no controlada previamente
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @DELETE
    @Path( "/activar-tipo/{id}" )
    public Response ActivarTipo( @PathParam("id")long  _id )
    {
        JsonObject data;
        TipoDto resultado = new TipoDto();
        try
        {
            DaoTipo dao = new DaoTipo ();
            Tipo tipo = dao.find(_id,Tipo.class);

            tipo.set_estado("activo");

            Tipo resul = dao.update(tipo);
            resultado.setId( resul.get_id() );

            List<Presentacion> resultado2= null;
            Class<Presentacion> type = Presentacion.class;

            DaoPresentacion dao2 = new DaoPresentacion();
            resultado2 = dao2.findAll( type );

            for(Presentacion obj: resultado2) {

                if (obj.get_tipo().get_id() == resul.get_id()){
                    DaoPresentacion dao3 = new DaoPresentacion();
                    PresentacionDto resultado3 = new PresentacionDto();
                    Presentacion presentacion = dao3.find(obj.get_id(), Presentacion.class);

                    presentacion.set_estado("activo");

                    Presentacion resul2 = dao3.update(presentacion);
                    resultado3.setId( resul2.get_id() );
                }



            }
            List<Marca_Tipo> resultado4= null;
            Class<Marca_Tipo> type2 = Marca_Tipo.class;

            DaoMarca_Tipo dao4 = new DaoMarca_Tipo();
            resultado4 = dao4.findAll( type2 );
            for(Marca_Tipo obj: resultado4) {

                if (obj.get_tipo().get_id() == resul.get_id()){
                    DaoMarca dao5 = new DaoMarca();
                    MarcaDto resultado5 = new MarcaDto();
                    Marca marca = dao5.find(obj.get_marca().get_id(), Marca.class);

                    marca.set_estado("activo");

                    Marca resul3 = dao5.update(marca);
                    resultado5.setId( resul3.get_id() );
                }



            }
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
     * Esta funcion consiste en mostrar los datos de un tipo en especifico
     * @author Carlos Silva
     * @param id corresponde al id del tipo
     * @throws Exception si ocurre cualquier excepcion general no controlada previamente
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @GET
    @Path( "/find-tipo/{id}" )
    public Response findTipo(@PathParam("id") long id )
    {
        JsonObject data;
        JsonObject tipoJson;
        try {
            DaoTipo dao = new DaoTipo();
            Tipo resul = dao.find( id,Tipo.class );

            tipoJson= Json.createObjectBuilder().add("id",resul.get_id() )
                    .add("nombre",resul.get_nombre()).build();

            if (resul.get_estado() != null){
                System.out.println("estado: " + resul.get_estado());
            }


            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200)
                    .add("categoria",tipoJson).build();

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
     * Esta funcion consiste en mostrar los datos de todos los tipos
     * @author Carlos Silva
     * @throws Exception si ocurre cualquier excepcion general no controlada previamente
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @GET
    @Path( "/findall-tipos" )
    public Response findAllTipo( )
    {
        JsonObject data;
        try {
            List<Tipo> resultado= null;
            Class<Tipo> type = Tipo.class;

            DaoTipo dao = new DaoTipo();
            resultado = dao.findAll( type );

            JsonArrayBuilder tipoArrayJson= Json.createArrayBuilder();

            for(Tipo obj: resultado) {

                JsonObject tipo = Json.createObjectBuilder().add("id",obj.get_id())
                                                            .add("nombre",obj.get_nombre())
                                                            .add("estado",obj.get_estado()).build();

                tipoArrayJson.add(tipo);
            }

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200)
                    .add("tipos",tipoArrayJson).build();


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