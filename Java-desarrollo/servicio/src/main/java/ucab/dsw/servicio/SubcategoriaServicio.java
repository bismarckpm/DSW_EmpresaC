package ucab.dsw.servicio;

import org.eclipse.persistence.exceptions.DatabaseException;
import ucab.dsw.entidades.Subcategoria;
import ucab.dsw.accesodatos.DaoSubcategoria;
import ucab.dsw.accesodatos.DaoCategoria;
import ucab.dsw.dtos.SubcategoriaDto;
import ucab.dsw.entidades.Categoria;
import ucab.dsw.excepciones.PruebaExcepcion;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.PersistenceException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path( "/subcategoria" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class SubcategoriaServicio extends AplicacionBase{

    @GET
    @Path( "/all" )
    public Response getAllSubcategorias()
    {
        JsonObject data;
        try
        {
            DaoSubcategoria dao= new DaoSubcategoria();
			DaoCategoria daoCategoria= new DaoCategoria();
			
            List<Subcategoria> resultado= dao.findAll(Subcategoria.class);

            JsonArrayBuilder subcategoriaArrayJson= Json.createArrayBuilder();

            for(Subcategoria obj: resultado){
				
				Categoria categoria= daoCategoria.find(obj.get_categoria().get_id(),Categoria.class);
                JsonObject subcategoria = Json.createObjectBuilder().add("id",obj.get_id())
                                                                    .add("nombre",obj.get_nombre())
                                                                    .add("categoria_id",categoria.get_id())
                                                                    .add("categoria",categoria.get_nombre())
                                                                    .add("estado",obj.get_estado()).build();

                subcategoriaArrayJson.add(subcategoria);

            }

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200)
                    .add("subcategorias",subcategoriaArrayJson).build();


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
    public Response addSubcategoria(SubcategoriaDto subcategoriaDto)
    {
        JsonObject data;
        SubcategoriaDto resultado = new SubcategoriaDto();
        try
        {
            DaoSubcategoria daoSubcategoria = new DaoSubcategoria();
            Subcategoria subcategoria = new Subcategoria();

            Categoria categoria= new Categoria(subcategoriaDto.getCategoriaDto().getId());

            subcategoria.set_nombre(subcategoriaDto.getNombre());
            subcategoria.set_categoria(categoria);
            subcategoria.set_estado("activo");

            Subcategoria resul = daoSubcategoria.insert(subcategoria);
            resultado.setId( resul.get_id() );

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200).build();
        }
        catch (PersistenceException | DatabaseException ex){
            data= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje","La subcategoria ya se encuestra registrada")
                    .add("codigo",500).build();

            System.out.println(data);

            return Response.status(Response.Status.OK).entity(data).build();
        }
        catch ( PruebaExcepcion ex){
            data= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje",ex.getMessage())
                    .add("codigo",500).build();

            System.out.println(data);
            return Response.status(Response.Status.OK).entity(data).build();

        }
        return Response.status(Response.Status.OK).entity(data).build();
    }

    @DELETE
    @Path( "/delete/{id}" )
    public Response deleteSubcategoria(@PathParam("id") long  _id)
    {
        JsonObject data;
        SubcategoriaDto resultado = new SubcategoriaDto();
        try
        {
            DaoSubcategoria dao = new DaoSubcategoria();
            Subcategoria subcategoria = dao.find(_id,Subcategoria.class);
            subcategoria.set_estado("inactivo");

            Subcategoria resul = dao.update(subcategoria);
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
    public Response editSubcategoria(@PathParam("id") long _id, SubcategoriaDto subcategoriaDto)
    {
        JsonObject data;
        SubcategoriaDto resultado = new SubcategoriaDto();
        try
        {
            DaoSubcategoria dao = new DaoSubcategoria();
            Subcategoria subcategoria = dao.find(_id,Subcategoria.class);
            subcategoria.set_nombre(subcategoriaDto.getNombre());

            Categoria categoria=new Categoria(subcategoriaDto.getCategoriaDto().getId());
            subcategoria.set_categoria(categoria);


            Subcategoria resul = dao.update(subcategoria);
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
    public Response getSubcategoria(@PathParam("id") long  _id)
    {
        JsonObject data;
        JsonObject subcategoriaJson;
        SubcategoriaDto resultado = new SubcategoriaDto();
        try
        {
            DaoSubcategoria dao = new DaoSubcategoria();
            Subcategoria subcategoria = dao.find(_id,Subcategoria.class);
            resultado.setId( subcategoria.get_id() );

            subcategoriaJson= Json.createObjectBuilder()
                                        .add("nombre",subcategoria.get_nombre())
                                        .add("categoria_id",subcategoria.get_categoria().get_id())
                                        .add("categoria",subcategoria.get_categoria().get_nombre())
                                        .add("estado",subcategoria.get_estado()).build();

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200)
                    .add("subcategoria",subcategoriaJson).build();

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
        @Path( "/by/categoria/{id}" )
        public Response getSubcategoriasByCategoriaId(@PathParam("id") long  _id)
        {
            JsonObject data;

            try
            {
                DaoSubcategoria dao= new DaoSubcategoria();
                List<Subcategoria> resultado= dao.getSubcategoriasByCategoria(_id);

                JsonArrayBuilder subcategoriasByCategoriaId= Json.createArrayBuilder();

                for(Subcategoria obj: resultado){

                    JsonObject marca = Json.createObjectBuilder().add("id",obj.get_id())
                            .add("nombre",obj.get_nombre()).build();

                    subcategoriasByCategoriaId.add(marca);

                }

                data= Json.createObjectBuilder()
                        .add("estado","success")
                        .add("codigo",200)
                        .add("subcategoriasByCategoria",subcategoriasByCategoriaId).build();

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
            System.out.println(data);
            return Response.status(Response.Status.OK).entity(data).build();

        }
}
