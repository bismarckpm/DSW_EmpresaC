package ucab.dsw.servicio;
import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.*;
import ucab.dsw.entidades.*;

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

    @PUT
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

            Presentacion resul = dao.insert( presentacion);
            resultado.setId( resul.get_id() );


            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200).build();

        }
        catch ( Exception ex )
        {
            data= Json.createObjectBuilder()
                    .add("estado","exception!!!")
                    .add("excepcion",ex.getMessage())
                    .add("codigo",500).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();
        }
        return Response.status(Response.Status.OK).entity(data).build();
    }

    @PUT
    @Path( "/channge-presentacion/{id}/{id2}" )
    public Response changePresentacion(@PathParam("id")long  _id,@PathParam("id2")long  _id2,PresentacionDto presentacionDto)
    {
        PresentacionDto resultado = new PresentacionDto();
        JsonObject data;
        try
        {
            DaoPresentacion dao = new DaoPresentacion();
            Presentacion presentacion= new Presentacion(_id);
            presentacion.set_nombre( presentacionDto.getNombre() );

            Tipo tipo =new Tipo(_id2);
            presentacion.set_tipo( tipo );

            Presentacion resul = dao.update( presentacion);
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

            presentacion.set_estado("Eliminado");

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

    @GET
    @Path( "/find-Presentacion/{id}" )
    public Response findPresentacion( @PathParam("id")long id )
    {
        JsonObject data;
        JsonObject PresentacionJson;
        try {
            DaoPresentacion dao = new DaoPresentacion();
            Presentacion resul = dao.find( id,Presentacion.class );
            System.out.println("id: " + resul.get_id());
            System.out.println("nombre: " + resul.get_nombre());
            if (resul.get_estado() != null){
            System.out.println("estado: " + resul.get_estado());
            }
            System.out.println("tipo: " + resul.get_tipo().get_nombre());

            PresentacionJson= Json.createObjectBuilder()
                    .add("estado",resul.get_estado()).build();
            PresentacionJson= Json.createObjectBuilder()
                    .add("tipo",resul.get_tipo().get_nombre()).build();

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

    @GET
    @Path( "/findall-presentaciones" )
    public Response findAllPresentacion( )
    {
        JsonObject data;
        try {
            List<Presentacion> resultado= null;
            Class<Presentacion> type = Presentacion.class;

            DaoPresentacion dao = new DaoPresentacion();
            resultado = dao.findAll( type );
            JsonArrayBuilder presentacionArrayJson= Json.createArrayBuilder();

            for(Presentacion obj: resultado) {

                if (obj.get_estado() != null){
                    JsonObject e = Json.createObjectBuilder().add("estado:",obj.get_estado())
                            .build();


                    System.out.println("estado: " + obj.get_estado());
                }else{
                    JsonObject e = Json.createObjectBuilder().add("estado:","")
                            .build();
                }


                JsonObject p = Json.createObjectBuilder().add("id",obj.get_id())
                        .add("nombre",obj.get_nombre()).add("tipo",obj.get_tipo().get_nombre())
                        .build();




                presentacionArrayJson.add(p);


            }


            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200)
                    .add("categorias",presentacionArrayJson).build();

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
