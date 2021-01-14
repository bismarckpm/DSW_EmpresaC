package ucab.dsw.clases;

import ucab.dsw.dtos.*;
import ucab.dsw.excepciones.CamposNulosExcepcion;
/**
 * Una clase que contiene validaciones para la solicitud
 * @version 1.0, 02/01/2021
 * @author Gabriel Romero
 */
public class ValidaCamposSolicitudDto {
    public boolean response;

    public ValidaCamposSolicitudDto() {
    }

    /**
     * Esta funcion consiste en traer los estudios que tiene un analista
     * @author Gabriel Romero
     * @param solicitudEstudioDto corresponde a los datos recibidos de la solicitud
     * @throws CamposNulosExcepcion Excepcion que controla los campos nulos
     * @return retorna un boolean. True si la solicitud no presenta campos nulos, caso contrario retorna False
     */
    public boolean ValidarSolicitudDto(SolicitudEstudioDto solicitudEstudioDto) throws CamposNulosExcepcion {

        if(solicitudEstudioDto.getModoencuesta()==null) {
            this.response=false;
            throw new CamposNulosExcepcion("Aun faltan campos por llenar. Revise, por favor");
        }

        if(this.ValidaMarcaDto(solicitudEstudioDto.getMarcaDto()) && this.ValidaClienteDto(solicitudEstudioDto.getClienteDto())
                                                                  && this.ValidaCaracteristicaDto(solicitudEstudioDto.getCaracteristica_DemograficaDto())
                                                                  && !solicitudEstudioDto.getModoencuesta().isEmpty()){
            this.response=true;
        }
        else{
            this.response=false;
            throw new CamposNulosExcepcion("Aun faltan campos por llenar. Revise, por favor");
        }

        return response;
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
