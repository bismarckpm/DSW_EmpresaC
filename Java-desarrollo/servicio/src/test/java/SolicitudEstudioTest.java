import org.junit.Assert;
import org.junit.Test;
import ucab.dsw.clases.ValidaCamposSolicitudDto;
import ucab.dsw.dtos.*;
import ucab.dsw.dtos.SolicitudEstudioDto;
import ucab.dsw.excepciones.CamposNulosExcepcion;

import javax.ws.rs.core.Response;


public class SolicitudEstudioTest {
    @Test
    public void AddSolicitud() throws Exception
    {
        ucab.dsw.servicio.SolicitudServicio servicio = new ucab.dsw.servicio.SolicitudServicio();

        /*--------------Dto--------------*/

        SolicitudEstudioDto solicitudEstudioDto=new SolicitudEstudioDto();

        solicitudEstudioDto.setModoencuesta("telefono");

        Caracteristica_DemograficaDto Caracteristica_DemograficaDto= new Caracteristica_DemograficaDto();
        Caracteristica_DemograficaDto.setEdad_min(15);
        Caracteristica_DemograficaDto.setEdad_max(60);
        Caracteristica_DemograficaDto.setNivel_socioeconomico("Alta");
        Caracteristica_DemograficaDto.setNacionalidad("mexicano");
        Caracteristica_DemograficaDto.setCantidad_hijos(2);
        Caracteristica_DemograficaDto.setGenero("M");

        Nivel_AcademicoDto nivel_academicoDto=new Nivel_AcademicoDto(5);
        ParroquiaDto parroquiaDto= new ParroquiaDto(2);

        Caracteristica_DemograficaDto.setNivel_AcademicoDto(nivel_academicoDto);
        Caracteristica_DemograficaDto.setParroquiaDto(parroquiaDto);

        solicitudEstudioDto.setCaracteristica_DemograficaDto(Caracteristica_DemograficaDto);

        MarcaDto marcaDto=new MarcaDto(3);
        solicitudEstudioDto.setMarcaDto(marcaDto);

        ClienteDto clienteDto= new ClienteDto(5);
        solicitudEstudioDto.setClienteDto(clienteDto);

        Response respuesta= servicio.addSolicitud(solicitudEstudioDto);
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());

    }

    @Test
    public void AddSolicitudSinDatosCompletos() throws Exception
    {
        ucab.dsw.servicio.SolicitudServicio servicio = new ucab.dsw.servicio.SolicitudServicio();

        /*--------------Dto--------------*/

        SolicitudEstudioDto solicitudEstudioDto=new SolicitudEstudioDto();

        solicitudEstudioDto.setModoencuesta("");

        Caracteristica_DemograficaDto Caracteristica_DemograficaDto= new Caracteristica_DemograficaDto();
        Caracteristica_DemograficaDto.setEdad_min(0);
        Caracteristica_DemograficaDto.setEdad_max(0);
        Caracteristica_DemograficaDto.setNivel_socioeconomico("Alta");
        Caracteristica_DemograficaDto.setNacionalidad("");
        Caracteristica_DemograficaDto.setCantidad_hijos(2);
        Caracteristica_DemograficaDto.setGenero("M");

        Nivel_AcademicoDto nivel_academicoDto=new Nivel_AcademicoDto(4);
        ParroquiaDto parroquiaDto= new ParroquiaDto(1);

        Caracteristica_DemograficaDto.setNivel_AcademicoDto(nivel_academicoDto);
        Caracteristica_DemograficaDto.setParroquiaDto(parroquiaDto);

        solicitudEstudioDto.setCaracteristica_DemograficaDto(Caracteristica_DemograficaDto);

        MarcaDto marcaDto=new MarcaDto(0);
        solicitudEstudioDto.setMarcaDto(marcaDto);

        ClienteDto clienteDto= new ClienteDto(3);
        solicitudEstudioDto.setClienteDto(clienteDto);

        Response respuesta= servicio.addSolicitud(solicitudEstudioDto);
        Assert.assertEquals(respuesta.getStatus(),Response.Status.BAD_REQUEST.getStatusCode());

    }

    @Test
    public void AddSolicitudCamposNulos() throws Exception {
        ucab.dsw.servicio.SolicitudServicio servicio = new ucab.dsw.servicio.SolicitudServicio();
        ValidaCamposSolicitudDto validaCamposSolicitudDto=new ValidaCamposSolicitudDto();
        /*--------------Dto--------------*/

        SolicitudEstudioDto solicitudEstudioDto=new SolicitudEstudioDto();

        Caracteristica_DemograficaDto Caracteristica_DemograficaDto= new Caracteristica_DemograficaDto();
        Caracteristica_DemograficaDto.setEdad_min(15);
        Caracteristica_DemograficaDto.setEdad_max(60);
        Caracteristica_DemograficaDto.setNivel_socioeconomico("Alta");


        Nivel_AcademicoDto nivel_academicoDto=new Nivel_AcademicoDto(4);
        ParroquiaDto parroquiaDto= new ParroquiaDto(1);

        Caracteristica_DemograficaDto.setNivel_AcademicoDto(nivel_academicoDto);
        Caracteristica_DemograficaDto.setParroquiaDto(parroquiaDto);

        solicitudEstudioDto.setCaracteristica_DemograficaDto(Caracteristica_DemograficaDto);


        ClienteDto clienteDto= new ClienteDto(3);
        solicitudEstudioDto.setClienteDto(clienteDto);

        Assert.assertThrows(CamposNulosExcepcion.class,()->{
            validaCamposSolicitudDto.ValidarSolicitudDto(solicitudEstudioDto);
        });

    }

    @Test
    public void AddSolicitudCamposNulosYVacios() throws Exception {
        ucab.dsw.servicio.SolicitudServicio servicio = new ucab.dsw.servicio.SolicitudServicio();

        /*--------------Dto--------------*/

        SolicitudEstudioDto solicitudEstudioDto=new SolicitudEstudioDto();

        solicitudEstudioDto.setModoencuesta("");

        Caracteristica_DemograficaDto Caracteristica_DemograficaDto= new Caracteristica_DemograficaDto();
        Caracteristica_DemograficaDto.setEdad_min(15);
        Caracteristica_DemograficaDto.setEdad_max(60);
        Caracteristica_DemograficaDto.setNivel_socioeconomico("Alta");
        Caracteristica_DemograficaDto.setNacionalidad("venezolano");
        Caracteristica_DemograficaDto.setCantidad_hijos(5);
        Caracteristica_DemograficaDto.setGenero("M");

        Nivel_AcademicoDto nivel_academicoDto=new Nivel_AcademicoDto(4);
        ParroquiaDto parroquiaDto= new ParroquiaDto(1);

        Caracteristica_DemograficaDto.setNivel_AcademicoDto(nivel_academicoDto);
        Caracteristica_DemograficaDto.setParroquiaDto(parroquiaDto);

        solicitudEstudioDto.setCaracteristica_DemograficaDto(Caracteristica_DemograficaDto);

        MarcaDto marcaDto=new MarcaDto(0);
        solicitudEstudioDto.setMarcaDto(marcaDto);

        ClienteDto clienteDto= new ClienteDto(3);
        solicitudEstudioDto.setClienteDto(clienteDto);

    }
}
