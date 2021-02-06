package ucab.dsw.logica.comando.solicitud;

import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.*;
import ucab.dsw.entidades.*;
import ucab.dsw.excepciones.CamposNulosExcepcion;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import ucab.dsw.mappers.CaracteristicasMapper;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.Date;
import java.util.List;

public class AddSolicitudComando extends BaseComando {

    public SolicitudEstudioDto solicitudEstudioDto;
    public JsonObject data;

    public AddSolicitudComando(SolicitudEstudioDto solicitudEstudioDto) {
        this.solicitudEstudioDto = solicitudEstudioDto;
    }

    @Override
    public void execute() {
        try{
            int admin_random=0;
            Usuario admin_elegido;
            SolicitudEstudio resul;
            ValidarSolicitudDto(solicitudEstudioDto);

            //Daos
            DaoSolicitudEstudio dao = Fabrica.crear(DaoSolicitudEstudio.class);
            DaoParticipacion daoParticipacion=Fabrica.crear(DaoParticipacion.class);
            DaoEncuestado daoEncuestado=Fabrica.crear(DaoEncuestado.class);
            DaoMarca daoMarca=Fabrica.crear(DaoMarca.class);
            DaoCliente daoCliente= Fabrica.crear(DaoCliente.class);
            SolicitudEstudio solicitudEstudio=Fabrica.crear(SolicitudEstudio.class);
            Date fecha=Fabrica.crear(Date.class);

            //Caracteristicas demograficas
            CaracteristicaDemografica caracteristicaDemografica=CaracteristicasMapper.mapDtoToEntity(solicitudEstudioDto.getCaracteristica_DemograficaDto());

            //Marca
            Marca marca=daoMarca.find(solicitudEstudioDto.getMarcaDto().getId(),Marca.class);

            //Cliente
            Cliente cliente= daoCliente.find(solicitudEstudioDto.getClienteDto().getId(),Cliente.class);

            //Solicitud de estudio
            solicitudEstudio.set_cliente(cliente);
            solicitudEstudio.set_marca(marca);
            solicitudEstudio.set_caracteristicademografica(caracteristicaDemografica);
            solicitudEstudio.set_fecha_inicio(fecha);
            solicitudEstudio.set_modoencuesta(solicitudEstudioDto.getModoencuesta());

            SolicitudEstudio estudio_elegido = this.validarEstudiosPrevio(solicitudEstudio);
            System.out.println(estudio_elegido);

            if(estudio_elegido!=null){
                System.out.println("Entre");
                solicitudEstudio.set_encuesta(estudio_elegido.get_encuesta());
                solicitudEstudio.set_usuario(estudio_elegido.get_usuario());
                solicitudEstudio.set_estado("pendiente");

                resul = dao.insert(solicitudEstudio);

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

                resul = dao.insert(solicitudEstudio);
            }
            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("mensaje","Solicitud creada")
                    .add("solicitud_id",resul.get_id()).build();
        }
        catch (CamposNulosExcepcion ex )
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
            }


            if(ex.getMensaje()==null){
                data= Json.createObjectBuilder()
                        .add("estado","error")
                        .add("mensaje","Error del servidor. Intente mas tarde.") //mensaje personalizado
                        .add("codigo",400).build();

                System.out.println(data);
            }
            else{
                data= Json.createObjectBuilder()
                        .add("estado","error")
                        .add("mensaje",ex.getMensaje()) //mensaje personalizado
                        .add("codigo",400).build();

                System.out.println(data);

            }


        }

    }

    @Override
    public JsonObject getResult() {
        return data;
    }

    /**
     * Esta funcion consiste en validar o verificar si el cliente ya realizo ese mismo estudio previamente.
     * Esto implica que sea la mismas caracteristicas demograficas y la marca.
     * @author Gabriel Romero
     * @param solicitudEstudio corresponde al objeto de la capa web que contiene los nuevos datos que se desean insertar
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

    /**
     * Esta funcion consiste en traer los estudios que tiene un analista
     * @author Gabriel Romero
     * @param solicitudEstudioDto corresponde a los datos recibidos de la solicitud
     * @throws CamposNulosExcepcion Excepcion que controla los campos nulos
     * @return retorna un boolean. True si la solicitud no presenta campos nulos, caso contrario retorna False
     */
    public void ValidarSolicitudDto(SolicitudEstudioDto solicitudEstudioDto) throws CamposNulosExcepcion {

        if(solicitudEstudioDto.getModoencuesta()==null) {
            throw new CamposNulosExcepcion("Aun faltan campos por llenar. Revise, por favor");
        }

        if(!this.ValidaMarcaDto(solicitudEstudioDto.getMarcaDto()) && !this.ValidaClienteDto(solicitudEstudioDto.getClienteDto())
                && !this.ValidaCaracteristicaDto(solicitudEstudioDto.getCaracteristica_DemograficaDto())
                && solicitudEstudioDto.getModoencuesta().isEmpty()){
            throw new CamposNulosExcepcion("Aun faltan campos por llenar. Revise, por favor");
        }

    }

    /**
     * Esta funcion consiste en validar los campos recibidos del cliente en la solicitud de estudio
     * @author Gabriel Romero
     * @param clienteDto corresponde a los datos recibidos del cliente
     * @return retorna un boolean. True si el cliente no presenta campos nulos, caso contrario retorna False
     */
    public boolean ValidaClienteDto(ClienteDto clienteDto){
        boolean resul;

        if(clienteDto!=null){
            if(clienteDto.getId()!=0){
                resul=true;
            }
            else{
                resul=false;
            }
        }
        else{
            resul=false;
        }
        return resul;
    }

    /**
     * Esta funcion consiste en validar los campos recibidos de la marca en la solicitud de estudio
     * @author Gabriel Romero
     * @param marcaDto corresponde a los datos recibidos de la marca
     * @return retorna un boolean. True si el marca no presenta campos nulos, caso contrario retorna False
     */
    public boolean ValidaMarcaDto(MarcaDto marcaDto){

        boolean resul;

        if(marcaDto!=null){
            if(marcaDto.getId()!=0){
                resul=true;
            }
            else{
                resul=false;
            }
        }
        else{
            resul=false;
        }
        return resul;
    }

    /**
     * Esta funcion consiste en validar los campos recibidos de las caracteristicas demograficas en la solicitud de estudio
     * @author Gabriel Romero
     * @param caracteristica_demograficaDto corresponde a los datos recibidos de la caracteristicas demograficas
     * @return retorna un boolean. True si las caracteristicas no presentan campos nulos, caso contrario retorna False
     */
    public boolean ValidaCaracteristicaDto(CaracteristicaDemograficaDto caracteristica_demograficaDto){
        boolean resul;
        int cont=0;

        if(caracteristica_demograficaDto!=null){

            if(caracteristica_demograficaDto.getGenero()==null || caracteristica_demograficaDto.getNacionalidad()==null || caracteristica_demograficaDto.getNivel_socioeconomico()==null) {
                return false;
            }
            else{

                if(!caracteristica_demograficaDto.getGenero().isEmpty()){
                    cont++;
                }
                if(!caracteristica_demograficaDto.getNivel_socioeconomico().isEmpty()){
                    cont++;
                }
                if(!caracteristica_demograficaDto.getNacionalidad().isEmpty()){
                    cont++;
                }
            }

            if(caracteristica_demograficaDto.getEdad_min()!=0){
                cont++;
            }
            if(caracteristica_demograficaDto.getEdad_max()!=0){
                cont++;
            }
            if(this.ValidaParroquiaDto(caracteristica_demograficaDto.getParroquiaDto())){
                cont++;
            }
            if(this.ValidaNivelAcademicoDto(caracteristica_demograficaDto.getNivel_AcademicoDto())){
                cont++;
            }
            if(caracteristica_demograficaDto.getCantidad_hijos()>=0){
                cont++;
            }
        }

        if(cont==8){
            resul=true;
        }
        else{
            resul=false;
        }

        return resul;
    }

    /**
     * Esta funcion consiste en validar los campos recibidos del nivel academico en la solicitud de estudio
     * @author Gabriel Romero
     * @param nivel_academicoDto corresponde a los datos recibidos del nivel academico
     * @return retorna un boolean. True si el nivel acedemico no presenta campos nulos, caso contrario retorna False
     */
    public boolean ValidaNivelAcademicoDto(NivelAcademicoDto nivel_academicoDto){
        boolean resul;

        if(nivel_academicoDto!=null){
            if(nivel_academicoDto.getId()!=0){
                resul=true;
            }
            else{
                resul=false;
            }
        }
        else{
            resul=false;
        }
        return resul;
    }

    /**
     * Esta funcion consiste en validar los campos recibidos de la parroquia en la solicitud de estudio
     * @author Gabriel Romero
     * @param parroquiaDto corresponde a los datos recibidos de la parroquia
     * @return retorna un boolean. True si la parroquia no presenta campos nulos, caso contrario retorna False
     */
    public boolean ValidaParroquiaDto(ParroquiaDto parroquiaDto){
        boolean resul;

        if(parroquiaDto!=null){
            if(parroquiaDto.getId()!=0){
                resul=true;
            }
            else{
                resul=false;
            }
        }
        else{
            resul=false;
        }

        return resul;
    }

}
