package ucab.dsw.servicio;
import ucab.dsw.accesodatos.*;
import ucab.dsw.clases.ValidaCamposSolicitudDto;
import ucab.dsw.dtos.*;
import ucab.dsw.entidades.*;
import ucab.dsw.excepciones.CamposNulosExcepcion;

import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;
import javax.json.Json;
import javax.ws.rs.core.Response;


/**
 * Una clase para la administracion completa de las solicitudes de estudio
 * @version 1.0, 02/01/2021
 * @author Gabriel Romero
 */
@Path( "/solicitud" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class SolicitudEstudioServicio {

    /**
    * Esta funcion consiste en insertar una nueva solicitud de estudio
    * @author Gabriel Romero
    * @param solicitudEstudioDto corresponde al objeto de la capa web que contiene los nuevos datos que se desean insertar 
    * @throws Exception  si ocurre cualquier excepcion general no controlada previamente
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado y mensaje en caso de ocurrir
    *         alguna de las excepciones
    */
    @POST
    @Path( "/add" )
    public Response addSolicitud(SolicitudEstudioDto solicitudEstudioDto)
    {
        JsonObject data;
        int admin_random=0;
        Usuario admin_elegido=null;
        ValidaCamposSolicitudDto valida=new ValidaCamposSolicitudDto();
        Boolean isValidaSolitud;
        
        try
        {
            isValidaSolitud=valida.ValidarSolicitudDto(solicitudEstudioDto);
            DaoSolicitudEstudio dao = new DaoSolicitudEstudio();
            DaoParticipacion daoParticipacion=new DaoParticipacion();
            DaoEncuestado daoEncuestado=new DaoEncuestado();
			DaoMarca daoMarca=new DaoMarca();
			DaoNivelAcademico daoNivelAcademico=new DaoNivelAcademico();
			DaoParroquia daoParroquia= new DaoParroquia();
			DaoCliente daoCliente= new DaoCliente();

            SolicitudEstudio solicitudEstudio=new SolicitudEstudio();

            Date fecha=new Date();
            solicitudEstudio.set_fecha_inicio(fecha);
            solicitudEstudio.set_modoencuesta(solicitudEstudioDto.getModoencuesta());


            CaracteristicaDemografica CaracteristicaDemografica = new CaracteristicaDemografica();
            CaracteristicaDemografica.set_edad_min(solicitudEstudioDto.getCaracteristica_DemograficaDto().getEdad_min());
            CaracteristicaDemografica.set_edad_max(solicitudEstudioDto.getCaracteristica_DemograficaDto().getEdad_max());
            CaracteristicaDemografica.set_nacionalidad(solicitudEstudioDto.getCaracteristica_DemograficaDto().getNacionalidad());
            CaracteristicaDemografica.set_cantidad_hijos(solicitudEstudioDto.getCaracteristica_DemograficaDto().getCantidad_hijos());
            CaracteristicaDemografica.set_genero(solicitudEstudioDto.getCaracteristica_DemograficaDto().getGenero());
            CaracteristicaDemografica.set_nivel_socioeconomico(solicitudEstudioDto.getCaracteristica_DemograficaDto().getNivel_socioeconomico());

            NivelAcademico nivel_academico= daoNivelAcademico.find(solicitudEstudioDto.getCaracteristica_DemograficaDto().getNivel_AcademicoDto().getId(), NivelAcademico.class);
            Parroquia parroquia= daoParroquia.find(solicitudEstudioDto.getCaracteristica_DemograficaDto().getParroquiaDto().getId(),Parroquia.class);

            CaracteristicaDemografica.set_nivel_academico_demografia(nivel_academico);
            CaracteristicaDemografica.set_Parroquia_demografia(parroquia);

            solicitudEstudio.set_caracteristicademografica(CaracteristicaDemografica);

            Marca marca=daoMarca.find(solicitudEstudioDto.getMarcaDto().getId(),Marca.class);
            solicitudEstudio.set_marca(marca);

            Cliente cliente= daoCliente.find(solicitudEstudioDto.getClienteDto().getId(),Cliente.class);
            solicitudEstudio.set_cliente(cliente);

            SolicitudEstudio estudio_elegido = this.validarEstudiosPrevio(solicitudEstudio);
            System.out.println(estudio_elegido);

            if(estudio_elegido!=null){
                System.out.println("Entre");
                solicitudEstudio.set_encuesta(estudio_elegido.get_encuesta());
                solicitudEstudio.set_usuario(estudio_elegido.get_usuario());
                solicitudEstudio.set_estado("pendiente");

                SolicitudEstudio resul = dao.insert(solicitudEstudio);

                List<Participacion> participaciones_estudio_previo=estudio_elegido.get_participacion();

                for(Participacion obj1: participaciones_estudio_previo){

                    Encuestado encuestado=daoEncuestado.find(obj1.get_encuestado().get_id(),Encuestado.class);
                    Participacion participacion=new Participacion();
                    
                    participacion.set_encuestado(encuestado);
                    participacion.set_solicitudestudio(resul);
                    participacion.set_estado("activo");

                    Participacion resul2 = daoParticipacion.insert(participacion);

                }
                
                
            }
            
            else{
                System.out.println("No Entre");
                /*PARA PRODUCCION*/
                /*DaoUsuario daoUsuario=new DaoUsuario();
                List<Usuario> admins= daoUsuario.getAdmins();
                admin_random=(int)(Math.random()* admins.size());
                System.out.println("Admin random");
                System.out.println(admin_random);
                admin_elegido=admins.get(admin_random);*/

                /*PARA DESARROLLO*/
                DaoUsuario daoUsuario=new DaoUsuario();
                admin_elegido=daoUsuario.find((long)20,Usuario.class);
                solicitudEstudio.set_estado("por asignar");
                solicitudEstudio.set_usuario2(admin_elegido);

                SolicitudEstudio resul = dao.insert(solicitudEstudio);
            }


            data= Json.createObjectBuilder()
                      .add("estado","success")
                      .add("codigo",200).build();
        }
        catch ( CamposNulosExcepcion ex )
        {
            ex.printStackTrace();
            String mensaje_excepcion = ex.getMessage();     //mensaje del Exception

            if(mensaje_excepcion!=null){
                mensaje_excepcion="Ha ocurrido una excepcion - Catch en Linea 155 - Solicitud Estudio";
                data= Json.createObjectBuilder()
                        .add("estado","exception")
                        .add("mensaje","Error del servidor. Intente mas tarde.") //mensaje personalizado
                        .add("excepcion",mensaje_excepcion)
                        .add("codigo",400).build();

                System.out.println(data);
                return Response.status(Response.Status.OK).entity(data).build();
            }
            

            if(ex.getMensaje()==null){
                data= Json.createObjectBuilder()
                            .add("estado","error")
                            .add("mensaje","Error del servidor. Intente mas tarde.") //mensaje personalizado
                            .add("codigo",400).build();
                
                System.out.println(data);
                return Response.status(Response.Status.OK).entity(data).build();
            }
            else{
                data= Json.createObjectBuilder()
                            .add("estado","error")
                            .add("mensaje",ex.getMensaje()) //mensaje personalizado
                            .add("codigo",400).build();
                
                System.out.println(data);
                return Response.status(Response.Status.OK).entity(data).build();

            }


        }
        return Response.status(Response.Status.OK).entity(data).build();
    }

    /**
    * Esta funcion consiste en validar o verificar si el cliente ya realizo ese mismo estudio previamente.
    * Esto implica que sea la mismas caracteristicas demograficas y la marca.
    * @author Gabriel Romero
    * @param solicitudEstudio corresponde al objeto de la capa web que contiene los nuevos datos que se desean insertar
    * @throws Exception  si ocurre cualquier excepcion general no controlada previamente
    * @return retorna una SolicitudEstudio correspondiente al estudio. Hay dos opciones: 
    *         1- Si el estudio ya existe se asigna la misma encuesta, la misma participacion y el mismo analista
    *         2- En caso contrario, se envia a un administrador para su respectiva configuración
    */
    public SolicitudEstudio validarEstudiosPrevio(SolicitudEstudio solicitudEstudio) {

            DaoSolicitudEstudio dao=new DaoSolicitudEstudio();
            SolicitudEstudio estudio_elegido = null;
            Boolean resul=false;      
            try
            {
                List<SolicitudEstudio> estudios_previos=dao.getEstudiosByCliente(solicitudEstudio.get_cliente().get_id(), solicitudEstudio.get_marca().get_id());
                for(SolicitudEstudio obj: estudios_previos){
                    if(!obj.get_estado().equals("por asignar")){
                            resul=this.CheckearCaracteristicasDemograficas(solicitudEstudio.get_caracteristicademografica(),obj.get_caracteristicademografica());
                    }
                    if(resul){
                        estudio_elegido=obj;
                        solicitudEstudio.set_caracteristicademografica(obj.get_caracteristicademografica());
                        System.out.println("Encontre un estudio similar");
                        break;
                    }

                }
            }
            catch ( Exception ex )
            {
                String problema = ex.getMessage();
                System.out.println(problema);       
                return null;
            }

            System.out.println("ESTUDIO ELEGIDOO");
            System.out.println(estudio_elegido);
            return estudio_elegido;
    }    


    /**
    * Esta funcion consiste en chequear las caracteristicas demograficas de un estudio realizado por el cliente
    * con la nueva solicitud
    * @author Gabriel Romero
    * @param a  objeto de tipo Caracteristica_Demografica, que corresponde a las caracteristicas del estudio que se desea realizar
    * @param b  objeto de tipo Caracteristica_Demografica, que corresponde a las caracteristicas del estudio que ya ha sido realizado
    * @throws Exception  si ocurre cualquier excepcion general no controlada previamente
    * @return retorna un boolean. True en caso de que sean las mismas caracteristicas demograficas, y False en caso contrario.
    */
    public boolean CheckearCaracteristicasDemograficas(CaracteristicaDemografica a, CaracteristicaDemografica b){
        Boolean resul=false;
        int cont=0;

            try
            {
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
            }
            catch ( Exception ex )
            {
                String problema = ex.getMessage();
                System.out.println(problema);
                return resul;
            }

        return resul;
    }
}