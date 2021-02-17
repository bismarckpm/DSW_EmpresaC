package ucab.dsw.clases;

import ucab.dsw.dtos.*;
import ucab.dsw.excepciones.CamposNulosExcepcion;

public class ValidaCamposSolicitudDto {
    public boolean response;

    public ValidaCamposSolicitudDto() {
    }

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
