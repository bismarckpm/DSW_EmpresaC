package ucab.dsw.servicio;

import org.eclipse.persistence.exceptions.DatabaseException;
import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.*;
import ucab.dsw.entidades.*;
import ucab.dsw.excepciones.PruebaExcepcion;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.PersistenceException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

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
    * @throws Exception si ocurre cualquier excepcion general no controlada previamente
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado, marcas (array de objetos) 
    *         y mensaje en caso de ocurrir alguna de las excepciones
    */
    @GET
    @Path( "/all" )
    public Response getAllMarcas()
    {
        JsonObject data;
        try
        {
			
            DaoMarca dao= new DaoMarca();
			DaoSubcategoria daoSubcategoria= new DaoSubcategoria();
			DaoPresentacion daoPresentacion=new DaoPresentacion();
			DaoMarca_Tipo daoMarca_tipo=new DaoMarca_Tipo();
			
            List<Marca> resultado= dao.findAll(Marca.class);
			
            JsonArrayBuilder marcaArrayJson= Json.createArrayBuilder();
			JsonArrayBuilder tipoArrayJson= Json.createArrayBuilder();
			JsonArrayBuilder presentacionesArrayJson= Json.createArrayBuilder();

            for(Marca obj: resultado){
				
				List<Marca_Tipo> marca_tipos=daoMarca_tipo.getAllMarcaTiposByMarca(obj.get_id());
				
				for(Marca_Tipo obj2: marca_tipos){
														 
						List<Presentacion> presentaciones=daoPresentacion.getAllPresentacionesByTipo(obj2.get_tipo().get_id());
						
						for(Presentacion obj3: presentaciones){
								JsonObject presentacion = Json.createObjectBuilder().add("presentacion_id",obj3.get_id())
																					 .add("nombre",obj3.get_nombre()).build();
								presentacionesArrayJson.add(presentacion);
								
						}		

						 JsonObject tipo = Json.createObjectBuilder().add("marca_tipo_id",obj2.get_id())
																 .add("tipo",obj2.get_tipo().get_nombre())
																 .add("tipo_id",obj2.get_tipo().get_id())
																 .add("presentaciones",presentacionesArrayJson).build();
					
					tipoArrayJson.add(tipo);
				}
				
				Subcategoria subcategoria= daoSubcategoria.find(obj.get_subcategoria().get_id(),Subcategoria.class);
                JsonObject marca = Json.createObjectBuilder().add("id",obj.get_id())
                                                             .add("nombre",obj.get_nombre())
                                                             .add("subcategoria_id",subcategoria.get_id())
                                                             .add("subcategoria",subcategoria.get_nombre())
                                                             .add("estado",obj.get_estado())
															 .add("tipos",tipoArrayJson).build();

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

    /**
    * Esta funcion consiste en insertar una nueva marca
    * @author Gabriel Romero
    * @param marcaDto corresponde al objeto de la capa web que contiene los nuevos datos que se desean insertar 
    * @throws PersistenceException si se inserta una marca duplicada
    * @throws DatabaseException    si existe algun problema con la conexion con el servidor de base de datos
    * @throws Excepcion      si ocurre cualquier excepcion general no controlada previamente
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado y mensaje en caso de ocurrir
    *         alguna de las excepciones
    */
    @POST
    @Path( "/add" )
    public Response addMarca(MarcaDto marcaDto)
    {
        JsonObject data;
        MarcaDto resultado = new MarcaDto();
        try
        {
            DaoMarca DaoMarca = new DaoMarca();
            DaoMarca_Tipo daoMarca_tipo= new DaoMarca_Tipo();
			DaoTipo daoTipo= new DaoTipo();
			
            Marca marca= new Marca();
            Subcategoria subcategoria = new Subcategoria(marcaDto.getSubcategoriaDto().getId());
            

            marca.set_nombre(marcaDto.getNombre());
            marca.set_subcategoria(subcategoria);
            marca.set_estado("activo");

            Marca resul = DaoMarca.insert(marca);

            for(TipoDto obj: marcaDto.getTipo_Dto()){
				
				Marca_Tipo marca_tipo=new Marca_Tipo();
				Tipo tipo= daoTipo.find(obj.getId(),Tipo.class);
				marca_tipo.set_tipo(tipo);
				marca_tipo.set_marca(resul);

                Marca_Tipo resul2 = daoMarca_tipo.insert(marca_tipo);
            }

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200).build();
        }
        catch (PersistenceException | DatabaseException ex){
            data= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje","La marca ya se encuestra registrada")
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
    * Esta funcion consiste en inhabilitar una marca. Quedaran inhabilitadas para futuros estudios.
    * @author Gabriel Romero
    * @param _id corresponde al id de la marca
    * @throws Exception si ocurre cualquier excepcion general no controlada previamente
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado y mensaje en caso de ocurrir 
    *         alguna de las excepciones
    */
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
            marca.set_estado("inactivo");



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

    /**
    * Esta funcion consiste en habilitar nuevamente una marca previamente inhabilitada
    * @author Carlos Silva
    * @param _id corresponde al id de la marca
    * @throws Exception si ocurre cualquier excepcion general no controlada previamente
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado y mensaje en caso de ocurrir 
    *         alguna de las excepciones
    */

    @PUT
    @Path( "/activar/{id}" )
    public Response activarMarca(@PathParam("id") long  _id)
    {
        JsonObject data;
        MarcaDto resultado = new MarcaDto();
        try
        {
            DaoMarca dao = new DaoMarca();
            Marca marca = dao.find(_id,Marca.class);
            marca.set_estado("activo");



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


    /**
    * Esta funcion consiste en editar una marca.
    * @author Gabriel Romero
    * @param _id corresponde al id de la marca
    * @param categoriaDto corresponde al objeto de la capa web que contiene los nuevos datos que se desean actualizar
    * @throws PersistenceException si se inserta una marca duplicada
    * @throws DatabaseException    si existe algun problema con la conexion con el servidor de base de datos
    * @throws Excepcion      si ocurre cualquier excepcion general no controlada previamente
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado y mensaje en caso de ocurrir 
    *         alguna de las excepciones
    */
    @PUT
    @Path( "/edit/{id}" )
    public Response editMarca(@PathParam("id") long _id, MarcaDto marcaDto)
    {
        JsonObject data;
        MarcaDto resultado = new MarcaDto();
        try
        {
            DaoMarca dao = new DaoMarca();
			DaoMarca_Tipo daoMarca_tipo= new DaoMarca_Tipo();
			DaoTipo daoTipo= new DaoTipo();
			DaoSubcategoria daoSubcategoria=new DaoSubcategoria();
			
            Marca marca = dao.find(_id,Marca.class);
			Subcategoria subcategoria = daoSubcategoria.find(marcaDto.getSubcategoriaDto().getId(),Subcategoria.class);
            Marca_Tipo marca_tipo=daoMarca_tipo.find(marcaDto.getMarcaTipo_Dto().getId(),Marca_Tipo.class);

            for(TipoDto obj: marcaDto.getTipo_Dto()){

				Tipo tipo= daoTipo.find(obj.getId(),Tipo.class);
				marca_tipo.set_tipo(tipo);
                Marca_Tipo resul2 = daoMarca_tipo.update(marca_tipo);
            }
            
            daoMarca_tipo.update(marca_tipo);

			marca.set_subcategoria(subcategoria);
            marca.set_nombre(marcaDto.getNombre());
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

    /**
    * Esta funcion consiste obtener una marca
    * @author Gabriel Romero
    * @param _id corresponde al id de la marca
    * @throws Exception si ocurre cualquier excepcion general no controlada previamente
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado, marca y mensaje en caso de ocurrir 
    *         alguna de las excepciones
    */
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

    /**
    * Esta funcion consiste obtener una marcas por subcategoria
    * @author Gabriel Romero
    * @param _id corresponde al id de la subcategoria
    * @throws Exception si ocurre cualquier excepcion general no controlada previamente
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado, marcasBySubcategoria y mensaje en caso de ocurrir 
    *         alguna de las excepciones
    */
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
