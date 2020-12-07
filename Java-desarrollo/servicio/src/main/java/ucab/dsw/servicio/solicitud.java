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
import java.util.Date;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path( "/prueba" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class solicitud {

    @PUT
    @Path( "/addSolicitud" )
    public Response addSolicitud(long  _id_Cliente, long  _id_Marca, long  _id_Nivel_academico, long  _id_Parroquia, SolicituEstudioDto solicituEstudioDto, Caracteristica_DemograficaDto caracteristica_DemograficaDto)
    {
        JsonObject data;
        SolicituEstudioDto resultado = new SolicituEstudioDto();
        Caracteristica_DemograficaDto resultado2 = new Caracteristica_DemograficaDto();

        try
        {
            DaoSolicitudEstudio dao = new DaoSolicitudEstudio();
            DaoCaracteristica_Demografica dao2 = new DaoCaracteristica_Demografica();

            Caracteristica_Demografica caracteristica_Demografica   = new Caracteristica_Demografica();
            caracteristica_Demografica.set_edad_min(caracteristica_DemograficaDto.getEdad_min());
            caracteristica_Demografica.set_edad_max(caracteristica_DemograficaDto.getEdad_max());
            caracteristica_Demografica.set_nivel_socioeconomico(caracteristica_DemograficaDto.getNivel_socioeconomico());
            caracteristica_Demografica.set_nacionalidad(caracteristica_DemograficaDto.getNacionalidad());
            caracteristica_Demografica.set_cantidad_hijos(caracteristica_DemograficaDto.getCantidad_hijos());
            caracteristica_Demografica.set_genero(caracteristica_DemograficaDto.getGenero());
            Nivel_Academico nivel_academico = new Nivel_Academico(_id_Nivel_academico);
            caracteristica_Demografica.set_nivel_academico_demografia(nivel_academico);
            Parroquia parroquia=new Parroquia(_id_Parroquia);
            caracteristica_Demografica.set_Parroquia_demografia(parroquia);

            Caracteristica_Demografica resul = dao2.insert( caracteristica_Demografica);
            resultado2.setId( resul.get_id() );

            SolicitudEstudio solicitudEstudio = new SolicitudEstudio();

            Date date =new Date();

            solicitudEstudio.set_fecha_inicio(date);

            solicitudEstudio.set_estado("Por asignar");
            solicitudEstudio.set_modoencuesta(solicituEstudioDto.getModoencuesta());

            Cliente cliente = new Cliente(_id_Cliente);
            solicitudEstudio.set_cliente( cliente );

            Marca marca = new Marca(_id_Marca);
            solicitudEstudio.set_marca( marca );
            solicitudEstudio.set_caracteristicademografica(resul);

            SolicitudEstudio resul2 = dao.insert( solicitudEstudio);
            resultado.setId( resul2.get_id() );


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
}
