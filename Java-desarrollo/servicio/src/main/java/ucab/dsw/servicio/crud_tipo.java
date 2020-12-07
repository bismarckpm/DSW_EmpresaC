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
public class crud_tipo {

    @PUT
    @Path( "/addpresentacion" )
    public TipoDto addTipo(TipoDto tipoDto)
    {
        TipoDto resultado = new TipoDto();

        try
        {
            DaoTipo dao = new DaoTipo();
            Tipo tipo= new Tipo();
            tipo.set_nombre( tipoDto.getNombre() );

            Tipo resul = dao.insert( tipo);
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
    public TipoDto changeTipo(long  _id,TipoDto tipoDto)
    {
        TipoDto resultado = new TipoDto();

        try
        {
            DaoTipo dao = new DaoTipo();
            Tipo tipo =new Tipo(_id);
            tipo.set_nombre( tipoDto.getNombre() );

            Tipo resul = dao.update( tipo);
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
    public TipoDto EliminarTipo( long  _id )
    {
        TipoDto resultado = new TipoDto();
        try
        {
            DaoTipo dao = new DaoTipo ();
            Tipo tipo = dao.find(_id,Tipo.class);

            tipo.set_estado("Eliminado");

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

                    presentacion.set_estado("Eliminado");

                    Presentacion resul2 = dao3.update(presentacion);
                    resultado3.setId( resul2.get_id() );
                }
            }



        }
        catch ( Exception ex )
        {
            String problema = ex.getMessage();
        }
        return  resultado;
    }

    @PUT
    @Path( "/{id}" )
    public int findTipo( long id )
    {

        try {
            DaoTipo dao = new DaoTipo();
            Tipo resul = dao.find( id,Tipo.class );
            System.out.println("id: " + resul.get_id());
            System.out.println("nombre: " + resul.get_nombre());
            if (resul.get_estado() != null){
                System.out.println("estado: " + resul.get_estado());
            }


        }
        catch ( Exception ex )
        {
            String problema = ex.getMessage();
        }
        return  1;
    }

    @PUT
    @Path( "/{id}" )
    public int findAllTipo( )
    {

        try {
            List<Tipo> resultado= null;
            Class<Tipo> type = Tipo.class;

            DaoTipo dao = new DaoTipo();
            resultado = dao.findAll( type );
            for(Tipo obj: resultado) {

                System.out.println("id: " + obj.get_id());
                System.out.println("nombre: " + obj.get_nombre());
                if (obj.get_estado() != null){
                    System.out.println("estado: " + obj.get_estado());
                }
            }


        }
        catch ( Exception ex )
        {
            String problema = ex.getMessage();
        }
        return  1;
    }
}