package ucab.dsw.servicio;
import ucab.dsw.accesodatos.DaoSolicitudEstudio;
import ucab.dsw.accesodatos.DaoUsuario;
import ucab.dsw.dtos.SolicitudEstudioDto;
import ucab.dsw.entidades.*;

import javax.json.JsonObject;
import javax.validation.constraints.Null;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.core.Response;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path( "/solicitud" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class SolicitudServicio {

    @POST
    @Path( "/add" )
    public Response addSolicitud(SolicitudEstudioDto solicitudEstudioDto)
    {
        JsonObject data;

        try
        {
            DaoSolicitudEstudio dao = new DaoSolicitudEstudio();

            SolicitudEstudio solicitudEstudio=new SolicitudEstudio();

            Date fecha=new Date();
            solicitudEstudio.set_fecha_inicio(fecha);
            solicitudEstudio.set_modoencuesta(solicitudEstudioDto.getModoencuesta());


            Caracteristica_Demografica Caracteristica_Demografica= new Caracteristica_Demografica();
            Caracteristica_Demografica.set_edad_min(solicitudEstudioDto.getCaracteristica_DemograficaDto().getEdad_min());
            Caracteristica_Demografica.set_edad_max(solicitudEstudioDto.getCaracteristica_DemograficaDto().getEdad_max());
            Caracteristica_Demografica.set_nivel_socioeconomico(solicitudEstudioDto.getCaracteristica_DemograficaDto().getNivel_socioeconomico());
            Caracteristica_Demografica.set_nacionalidad(solicitudEstudioDto.getCaracteristica_DemograficaDto().getNacionalidad());
            Caracteristica_Demografica.set_cantidad_hijos(solicitudEstudioDto.getCaracteristica_DemograficaDto().getCantidad_hijos());
            Caracteristica_Demografica.set_genero(solicitudEstudioDto.getCaracteristica_DemograficaDto().getGenero());

            Nivel_Academico nivel_academico=new Nivel_Academico(solicitudEstudioDto.getCaracteristica_DemograficaDto().getNivel_AcademicoDto().getId());
            Parroquia parroquia= new Parroquia(solicitudEstudioDto.getCaracteristica_DemograficaDto().getParroquiaDto().getId());

            Caracteristica_Demografica.set_nivel_academico_demografia(nivel_academico);
            Caracteristica_Demografica.set_Parroquia_demografia(parroquia);

            solicitudEstudio.set_caracteristicademografica(Caracteristica_Demografica);

            Marca marca=new Marca(solicitudEstudioDto.getMarcaDto().getId());
            solicitudEstudio.set_marca(marca);

            Cliente cliente= new Cliente(solicitudEstudioDto.getClienteDto().getId());
            solicitudEstudio.set_cliente(cliente);

            SolicitudEstudio solicitudEstudioProcesada = this.validarEstudiosPrevio(solicitudEstudio);    
            SolicitudEstudio resul = dao.insert(solicitudEstudioProcesada);

            data= Json.createObjectBuilder()
                      .add("estado","success")
                      .add("codigo",200).build();
        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
            String problema = ex.getMessage();
            data= Json.createObjectBuilder()
                      .add("estado","exception!!!")
                      .add("excepcion",problema)
                      .add("codigo",500).build();
                      
            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();
        }
        return Response.status(Response.Status.OK).entity(data).build();
    }

    public SolicitudEstudio validarEstudiosPrevio(SolicitudEstudio solicitudEstudio) throws Exception{

        
        DaoSolicitudEstudio dao=new DaoSolicitudEstudio();
        List<SolicitudEstudio> estudios_previos=dao.getEstudiosByCliente(solicitudEstudio.get_cliente().get_id(), solicitudEstudio.get_marca().get_id());
        SolicitudEstudio estudio_elegido = null;
        Boolean resul=false;
        int admin_random=0;
        Usuario admin_elegido=null;

            

            for(SolicitudEstudio obj: estudios_previos){
                if(!obj.get_estado().equals("por asignar")){
                    resul=this.CheckearCaracteristicasDemograficas(solicitudEstudio.get_caracteristicademografica(),obj.get_caracteristicademografica());
                }
                if(resul){
                    estudio_elegido=obj;
                    solicitudEstudio.set_caracteristicademografica(obj.get_caracteristicademografica());
                    break;
                }

            }
        System.out.println("ESTUDIO ELEGIDOO");
        System.out.println(estudio_elegido);

            
            if(estudio_elegido!=null){
                solicitudEstudio.set_encuesta(estudio_elegido.get_encuesta());
                solicitudEstudio.set_usuario(estudio_elegido.get_usuario());

                
                List<Participacion> participaciones_estudio_previo=estudio_elegido.get_participacion();
                List<Participacion> participaciones_actuales= new ArrayList<>();

                for(Participacion obj: participaciones_estudio_previo){
                    Participacion participacion=new Participacion();
                    participacion.set_encuestado(obj.get_encuestado());
                    participacion.set_solicitudestudio(solicitudEstudio);
                    participacion.set_estado("activo");

                    participaciones_actuales.add(participacion);
                }

                /*FALTA: Añadir metodo para verificar si existen mas encuestados que cumplan las caracteristicas*/

                solicitudEstudio.set_estado("pendiente");
                solicitudEstudio.set_participacion(participaciones_actuales);
            }
            
            else{

                /*Para produccion*/
                /*DaoUsuario daoUsuario=new DaoUsuario();
                List<Usuario> admins= daoUsuario.getAdmins();
                admin_random=(int)(Math.random()* admins.size());
                System.out.println("Admin random");
                System.out.println(admin_random);
                admin_elegido=admins.get(admin_random);*/

                /*Para la simulacion*/
                DaoUsuario daoUsuario=new DaoUsuario();
                admin_elegido=daoUsuario.find(20,Usuario.class);
                solicitudEstudio.set_estado("por asignar");
                solicitudEstudio.set_usuario2(admin_elegido);
            }

        return solicitudEstudio;
    }

    public boolean CheckearCaracteristicasDemograficas(Caracteristica_Demografica a, Caracteristica_Demografica b) throws Exception{
        Boolean resul=false;
        int cont=0;

            if(a.get_edad_min()==b.get_edad_min()){
                cont++;
            }
            if(a.get_edad_max()==b.get_edad_max()){
                cont++;
            }

            if(a.get_nivel_socioeconomico().equals(b.get_nivel_socioeconomico())){
                cont++;
            }

            if(a.get_nacionalidad().equals(b.get_nacionalidad())){
                cont++;
            }

            if(a.get_cantidad_hijos()==b.get_cantidad_hijos()){
                cont++;
            }

            if(a.get_genero().equals(b.get_genero())){
                cont++;
            }

            if(a.get_nivel_academico_demografia().get_id()==b.get_nivel_academico_demografia().get_id()){
                cont++;
            }

            if(a.get_Parroquia_demografia().get_id()==b.get_Parroquia_demografia().get_id()){
                cont++;
            }


        if(cont==8){
            resul=true;
        }

        return resul;
    }
}
