package ucab.dsw.servicio;
import org.eclipse.persistence.exceptions.DatabaseException;
import ucab.dsw.accesodatos.DaoCategoria;
import ucab.dsw.accesodatos.DaoMarca;
import ucab.dsw.accesodatos.DaoPresentacion;
import ucab.dsw.accesodatos.DaoSubcategoria;
import ucab.dsw.dtos.CategoriaDto;

import ucab.dsw.dtos.MarcaDto;
import ucab.dsw.dtos.PresentacionDto;
import ucab.dsw.dtos.SubcategoriaDto;
import ucab.dsw.entidades.Categoria;
import ucab.dsw.entidades.Marca;
import ucab.dsw.entidades.Presentacion;
import ucab.dsw.entidades.Subcategoria;
import ucab.dsw.excepciones.PruebaExcepcion;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.PersistenceException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path( "/categoria" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class CategoriaServicio extends AplicacionBase{

    @GET
    @Path( "/all" )
    public Response getAllCategorias()
    {
        JsonObject data;
        try
        {
                DaoCategoria dao= new DaoCategoria();
                List<Categoria> resultado= dao.findAll(Categoria.class);

                JsonArrayBuilder categoriaArrayJson= Json.createArrayBuilder();

                for(Categoria obj: resultado){

                    JsonObject categoria = Json.createObjectBuilder().add("id",obj.get_id())
                                                                     .add("nombre",obj.get_nombre())
                                                                     .add("estado",obj.get_estado()).build();

                    categoriaArrayJson.add(categoria);

                }

                data= Json.createObjectBuilder()
                        .add("estado","success")
                        .add("codigo",200)
                        .add("categorias",categoriaArrayJson).build();


        }
        catch ( Exception ex )
        {
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
    public Response addCategoria(CategoriaDto categoriaDto)
    {
        JsonObject data;
        CategoriaDto resultado = new CategoriaDto();
        try
        {
            DaoCategoria dao = new DaoCategoria();
            Categoria categoria = new Categoria();
            categoria.set_nombre(categoriaDto.getNombre());
            categoria.set_estado("activo");
            Categoria resul = dao.insert( categoria );
            resultado.setId( resul.get_id() );

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200).build();
        }
        catch (PersistenceException | DatabaseException ex){
            data= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje","La categoria ya se encuestra registrada")
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
        System.out.println(data);
        return Response.status(Response.Status.OK).entity(data).build();
    }

    @DELETE
    @Path( "/delete/{id}" )
    public Response deleteCategoria(@PathParam("id") long  _id)
    {
        JsonObject data;
        CategoriaDto resultado = new CategoriaDto();
        try
        {
            DaoCategoria dao = new DaoCategoria();
            Categoria categoria = dao.find(_id,Categoria.class);

            categoria.set_estado("inactivo");

            Categoria resul = dao.update(categoria);
            resultado.setId( resul.get_id() );

            List<Subcategoria> resultado2= null;
            Class<Subcategoria> type = Subcategoria.class;

            DaoSubcategoria dao2 = new DaoSubcategoria();
            resultado2 = dao2.findAll( type );
            for(Subcategoria obj: resultado2) {

                if (obj.get_categoria().get_id() == resul.get_id()){
                    DaoSubcategoria dao3 = new DaoSubcategoria();
                    SubcategoriaDto resultado3 = new SubcategoriaDto();
                    Subcategoria subcategoria = dao3.find(obj.get_id(), Subcategoria.class);

                    subcategoria.set_estado("inactivo");

                    Subcategoria resul2 = dao3.update(subcategoria);
                    resultado3.setId( resul2.get_id() );
                }



            }

            List<Marca> resultado4= null;
            Class<Marca> type2 = Marca.class;

            DaoMarca dao4 = new DaoMarca();
            resultado4 = dao4.findAll( type2 );
            for(Marca obj: resultado4) {

                if (obj.get_subcategoria().get_categoria().get_id() == resul.get_id()){
                    DaoMarca dao5 = new DaoMarca();
                    MarcaDto resultado5 = new MarcaDto();
                    Marca marca = dao5.find(obj.get_id(), Marca.class);

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
    public Response editCategoria(@PathParam("id") long _id, CategoriaDto categoriaDto)
    {
        JsonObject data;
        CategoriaDto resultado = new CategoriaDto();
        try
        {
            DaoCategoria dao = new DaoCategoria();
            Categoria categoria = dao.find(_id,Categoria.class);
            categoria.set_nombre(categoriaDto.getNombre());

            Categoria resul = dao.update(categoria);
            resultado.setId( resul.get_id() );

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200).build();

        }
        catch (PersistenceException | DatabaseException ex){
            data= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje","La categoria ya se encuestra registrada")
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
        System.out.println(data);
        return Response.status(Response.Status.OK).entity(data).build();
    }

    @GET
    @Path( "/{id}" )
    public Response getCategoria(@PathParam("id") long  _id)
    {
        JsonObject data;
        JsonObject categoriaJson;
        CategoriaDto resultado = new CategoriaDto();
        try
        {
            DaoCategoria dao = new DaoCategoria();
            Categoria categoria = dao.find(_id,Categoria.class);
            resultado.setId( categoria.get_id() );

            categoriaJson= Json.createObjectBuilder()
                               .add("nombre",categoria.get_nombre())
                               .add("estado",categoria.get_estado()).build();

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200)
                    .add("categoria",categoriaJson).build();

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
