package ucab.dsw.servicio;

import ucab.dsw.accesodatos.DaoMarca;
import ucab.dsw.dtos.MarcaDto;
import ucab.dsw.entidades.Marca;
import ucab.dsw.entidades.Subcategoria;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path( "/marca" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class MarcaServicio extends AplicacionBase{
    
    @GET
    @Path( "/all" )
    public Response getAllMarcas()
    {
        JsonObject data;
        try
        {
            DaoMarca dao= new DaoMarca();
            List<Marca> resultado= dao.findAll(Marca.class);

            JsonArrayBuilder marcaArrayJson= Json.createArrayBuilder();

            for(Marca obj: resultado){

                JsonObject marca = Json.createObjectBuilder().add("id",obj.get_id())
                                                             .add("nombre",obj.get_nombre())
                                                             .add("subcategoria_id",obj.get_subcategoria().get_id())
                                                             .add("categoria_id",obj.get_subcategoria().get_categoria().get_id()).build();

                marcaArrayJson.add(marca);

            }

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200)
                    .add("marcas",marcaArrayJson).build();


        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
            data= Json.createObjectBuilder()
                    .add("estado","exception!!!")
                    .add("excepcion",ex.getMessage())
                    .add("codigo",500).build();

            System.out.println(data);
            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();


        }

        System.out.println(data);
        return Response.status(Response.Status.OK).entity(data).build();

    }

    @POST
    @Path( "/add" )
    public Response addMarca(MarcaDto marcaDto)
    {
        JsonObject data;
        MarcaDto resultado = new MarcaDto();
        try
        {
            DaoMarca DaoMarca = new DaoMarca();

            Marca marca= new Marca();
            Subcategoria subcategoria = new Subcategoria(marcaDto.getSubcategoriaDto().getId());
            

            marca.set_nombre(marcaDto.getNombre());
            marca.set_subcategoria(subcategoria);

            Marca resul = DaoMarca.insert(marca);
            resultado.setId( resul.get_id() );

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200).build();
        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
            data= Json.createObjectBuilder()
                    .add("estado","exception!!!")
                    .add("excepcion",ex.getMessage())
                    .add("codigo",500).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();
        }
        return Response.status(Response.Status.OK).entity(data).build();
    }

    @DELETE
    @Path( "/delete/{id}" )
    public Response deleteMarca(@PathParam("id") long  _id)
    {
        JsonObject data;
        MarcaDto resultado = new MarcaDto();
        try
        {
            DaoMarca dao = new DaoMarca();
            Marca marca = dao.find(_id,Marca.class);

            //set_estado aqui

            Marca resul = dao.update(marca);
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
    @Path( "/edit/{id}" )
    public Response editMarca(@PathParam("id") long _id, MarcaDto marcaDto)
    {
        JsonObject data;
        MarcaDto resultado = new MarcaDto();
        try
        {
            DaoMarca dao = new DaoMarca();
            Marca marca = dao.find(_id,Marca.class);
            marca.set_nombre(marcaDto.getNombre());

            //set_estado aqui

            Marca resul = dao.update(marca);
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

    @GET
    @Path( "/{id}" )
    public Response getMarca(@PathParam("id") long  _id)
    {
        JsonObject data;
        JsonObject marcaJson;
        MarcaDto resultado = new MarcaDto();
        try
        {
            DaoMarca dao = new DaoMarca();
            Marca marca = dao.find(_id,Marca.class);
            resultado.setId( marca.get_id() );

            marcaJson= Json.createObjectBuilder()
                    .add("nombre",marca.get_nombre()).build();

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200)
                    .add("marca",marcaJson).build();

        }
        catch ( Exception ex )
        {
            data= Json.createObjectBuilder()
                    .add("estado","exception!!!")
                    .add("excepcion",ex.getMessage())
                    .add("codigo",500).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();
        }
        System.out.println(data);
        return Response.status(Response.Status.OK).entity(data).build();


    }

    @GET
    @Path( "/by/subcategoria/{id}" )
    public Response getMarcaBySubcategoriaId(@PathParam("id") long  _id)
    {
        JsonObject data;

        try
        {
            DaoMarca dao= new DaoMarca();
            List<Marca> resultado= dao.getMarcasBySubcategoria(_id);

            JsonArrayBuilder marcasByCategoriaIdJson= Json.createArrayBuilder();

            for(Marca obj: resultado){

                JsonObject marca = Json.createObjectBuilder().add("id",obj.get_id())
                                                             .add("nombre",obj.get_nombre()).build();

                marcasByCategoriaIdJson.add(marca);

            }

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200)
                    .add("marcasBySubcategoria",marcasByCategoriaIdJson).build();

        }
        catch ( Exception ex )
        {
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
