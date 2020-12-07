import org.junit.Assert;
import org.junit.Test;
import ucab.dsw.dtos.*;
import ucab.dsw.dtos.SolicitudEstudioDto;

import javax.ws.rs.core.Response;


public class SolicitudEstudioTest {
    @Test
    public void AddSolicitud() throws Exception
    {
        ucab.dsw.servicio.SolicitudServicio servicio = new ucab.dsw.servicio.SolicitudServicio();

        /*--------------Dto--------------*/

        SolicitudEstudioDto solicitudEstudioDto=new SolicitudEstudioDto();

        solicitudEstudioDto.setModoencuesta("online");

        Caracteristica_DemograficaDto Caracteristica_DemograficaDto= new Caracteristica_DemograficaDto();
        Caracteristica_DemograficaDto.setEdad_min(30);
        Caracteristica_DemograficaDto.setEdad_max(45);
        Caracteristica_DemograficaDto.setNivel_socioeconomico("Bajo");
        Caracteristica_DemograficaDto.setNacionalidad("Extranjero");
        Caracteristica_DemograficaDto.setCantidad_hijos(1);
        Caracteristica_DemograficaDto.setGenero("F");

        Nivel_AcademicoDto nivel_academicoDto=new Nivel_AcademicoDto(5);
        ParroquiaDto parroquiaDto= new ParroquiaDto(5);

        Caracteristica_DemograficaDto.setNivel_AcademicoDto(nivel_academicoDto);
        Caracteristica_DemograficaDto.setParroquiaDto(parroquiaDto);

        solicitudEstudioDto.setCaracteristica_DemograficaDto(Caracteristica_DemograficaDto);

        MarcaDto marcaDto=new MarcaDto(5);
        solicitudEstudioDto.setMarcaDto(marcaDto);

        ClienteDto clienteDto= new ClienteDto(5);
        solicitudEstudioDto.setClienteDto(clienteDto);

        Response respuesta= servicio.addSolicitud(solicitudEstudioDto);
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());

    }
}
