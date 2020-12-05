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

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path( "/prueba" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class Cruds_presntacion {

    @PUT
    @Path( "/addpresentacion" )
    public PresentacionDto addPresentacion(PresentacionDto presentacionDto)
    {
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


        }
        catch ( Exception ex )
        {
            String problema = ex.getMessage();
        }
        return  resultado;
    }

    @PUT
    @Path( "/channgepresentacion" )
    public PresentacionDto changePresentacion(long  _id,long  _id2,PresentacionDto presentacionDto)
    {
        PresentacionDto resultado = new PresentacionDto();

        try
        {
            DaoPresentacion dao = new DaoPresentacion();
            Presentacion presentacion= new Presentacion(_id);
            presentacion.set_nombre( presentacionDto.getNombre() );

            Tipo tipo =new Tipo(_id2);
            presentacion.set_tipo( tipo );

            Presentacion resul = dao.update( presentacion);
            resultado.setId( resul.get_id() );


        }
        catch ( Exception ex )
        {
            String problema = ex.getMessage();
        }
        return  resultado;
    }

    @DELETE
    @Path( "/delete/{id}" )
    public PresentacionDto EliminarPresentacion( long  _id )
    {
        PresentacionDto resultado = new PresentacionDto();
        try
        {
            DaoPresentacion dao = new DaoPresentacion ();
            Presentacion presentacion = dao.find(_id,Presentacion.class);

            presentacion.set_estado("Eliminado");

            Presentacion resul = dao.update(presentacion);
            resultado.setId( resul.get_id() );
        }
        catch ( Exception ex )
        {
            String problema = ex.getMessage();
        }
        return  resultado;
    }

    @PUT
    @Path( "/{id}" )
    public int findPresentacion( long id )
    {

        try {
            DaoPresentacion dao = new DaoPresentacion();
            Presentacion resul = dao.find( id,Presentacion.class );
            System.out.println("id: " + resul.get_id());
            System.out.println("nombre: " + resul.get_nombre());
            if (resul.get_estado() != null){
            System.out.println("estado: " + resul.get_estado());
            }
            System.out.println("tipo: " + resul.get_tipo().get_nombre());

        }
        catch ( Exception ex )
        {
            String problema = ex.getMessage();
        }
        return  1;
    }

    @PUT
    @Path( "/{id}" )
    public int findAllPresentacion( )
    {

        try {
            List<Presentacion> resultado= null;
            Class<Presentacion> type = Presentacion.class;

            DaoPresentacion dao = new DaoPresentacion();
            resultado = dao.findAll( type );
            for(Presentacion obj: resultado) {

                System.out.println("id: " + obj.get_id());
                System.out.println("nombre: " + obj.get_nombre());
                if (obj.get_estado() != null){
                    System.out.println("estado: " + obj.get_estado());
                }
                System.out.println("tipo: " + obj.get_tipo().get_nombre());
            }


        }
        catch ( Exception ex )
        {
            String problema = ex.getMessage();
        }
        return  1;
    }
}
