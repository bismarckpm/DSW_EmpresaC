import org.junit.Assert;
import org.junit.Test;
import ucab.dsw.clases.ValidaCamposSolicitudDto;
import ucab.dsw.dtos.*;
import ucab.dsw.dtos.SolicitudEstudioDto;
import ucab.dsw.excepciones.CamposNulosExcepcion;
import ucab.dsw.servicio.SolicitudEstudioServicio;

import javax.ws.rs.core.Response;


public class SolicitudEstudioServicioTest {
    @Test
    public void AddSolicitud() throws Exception
    {
        SolicitudEstudioServicio servicio = new SolicitudEstudioServicio();

        /*--------------Dto--------------*/

        SolicitudEstudioDto solicitudEstudioDto=new SolicitudEstudioDto();

        solicitudEstudioDto.setModoencuesta("telefono");

        CaracteristicaDemograficaDto CaracteristicaDemograficaDto = new CaracteristicaDemograficaDto();
        CaracteristicaDemograficaDto.setEdad_min(15);
        CaracteristicaDemograficaDto.setEdad_max(60);
        CaracteristicaDemograficaDto.setNivel_socioeconomico("Alta");
        CaracteristicaDemograficaDto.setNacionalidad("mexicano");
        CaracteristicaDemograficaDto.setCantidad_hijos(2);
        CaracteristicaDemograficaDto.setGenero("M");

        NivelAcademicoDto nivel_academicoDto=new NivelAcademicoDto(5);
        ParroquiaDto parroquiaDto= new ParroquiaDto(2);

        CaracteristicaDemograficaDto.setNivel_AcademicoDto(nivel_academicoDto);
        CaracteristicaDemograficaDto.setParroquiaDto(parroquiaDto);

        solicitudEstudioDto.setCaracteristica_DemograficaDto(CaracteristicaDemograficaDto);

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
        SolicitudEstudioServicio servicio = new SolicitudEstudioServicio();

        /*--------------Dto--------------*/

        SolicitudEstudioDto solicitudEstudioDto=new SolicitudEstudioDto();

        solicitudEstudioDto.setModoencuesta("");

        CaracteristicaDemograficaDto CaracteristicaDemograficaDto = new CaracteristicaDemograficaDto();
        CaracteristicaDemograficaDto.setEdad_min(0);
        CaracteristicaDemograficaDto.setEdad_max(0);
        CaracteristicaDemograficaDto.setNivel_socioeconomico("Alta");
        CaracteristicaDemograficaDto.setNacionalidad("");
        CaracteristicaDemograficaDto.setCantidad_hijos(2);
        CaracteristicaDemograficaDto.setGenero("M");

        NivelAcademicoDto nivel_academicoDto=new NivelAcademicoDto(4);
        ParroquiaDto parroquiaDto= new ParroquiaDto(1);

        CaracteristicaDemograficaDto.setNivel_AcademicoDto(nivel_academicoDto);
        CaracteristicaDemograficaDto.setParroquiaDto(parroquiaDto);

        solicitudEstudioDto.setCaracteristica_DemograficaDto(CaracteristicaDemograficaDto);

        MarcaDto marcaDto=new MarcaDto(0);
        solicitudEstudioDto.setMarcaDto(marcaDto);

        ClienteDto clienteDto= new ClienteDto(3);
        solicitudEstudioDto.setClienteDto(clienteDto);

        Response respuesta= servicio.addSolicitud(solicitudEstudioDto);
        Assert.assertEquals(respuesta.getStatus(),Response.Status.BAD_REQUEST.getStatusCode());

    }

    @Test
    public void AddSolicitudCamposNulos() throws Exception {
        SolicitudEstudioServicio servicio = new SolicitudEstudioServicio();
        ValidaCamposSolicitudDto validaCamposSolicitudDto=new ValidaCamposSolicitudDto();
        /*--------------Dto--------------*/

        SolicitudEstudioDto solicitudEstudioDto=new SolicitudEstudioDto();

        CaracteristicaDemograficaDto CaracteristicaDemograficaDto = new CaracteristicaDemograficaDto();
        CaracteristicaDemograficaDto.setEdad_min(15);
        CaracteristicaDemograficaDto.setEdad_max(60);
        CaracteristicaDemograficaDto.setNivel_socioeconomico("Alta");


        NivelAcademicoDto nivel_academicoDto=new NivelAcademicoDto(4);
        ParroquiaDto parroquiaDto= new ParroquiaDto(1);

        CaracteristicaDemograficaDto.setNivel_AcademicoDto(nivel_academicoDto);
        CaracteristicaDemograficaDto.setParroquiaDto(parroquiaDto);

        solicitudEstudioDto.setCaracteristica_DemograficaDto(CaracteristicaDemograficaDto);


        ClienteDto clienteDto= new ClienteDto(3);
        solicitudEstudioDto.setClienteDto(clienteDto);

        Assert.assertThrows(CamposNulosExcepcion.class,()->{
            validaCamposSolicitudDto.ValidarSolicitudDto(solicitudEstudioDto);
        });

    }

    @Test
    public void AddSolicitudCamposNulosYVacios() throws Exception {
        SolicitudEstudioServicio servicio = new SolicitudEstudioServicio();

        /*--------------Dto--------------*/

        SolicitudEstudioDto solicitudEstudioDto=new SolicitudEstudioDto();

        solicitudEstudioDto.setModoencuesta("");

        CaracteristicaDemograficaDto CaracteristicaDemograficaDto = new CaracteristicaDemograficaDto();
        CaracteristicaDemograficaDto.setEdad_min(15);
        CaracteristicaDemograficaDto.setEdad_max(60);
        CaracteristicaDemograficaDto.setNivel_socioeconomico("Alta");
        CaracteristicaDemograficaDto.setNacionalidad("venezolano");
        CaracteristicaDemograficaDto.setCantidad_hijos(5);
        CaracteristicaDemograficaDto.setGenero("M");

        NivelAcademicoDto nivel_academicoDto=new NivelAcademicoDto(4);
        ParroquiaDto parroquiaDto= new ParroquiaDto(1);

        CaracteristicaDemograficaDto.setNivel_AcademicoDto(nivel_academicoDto);
        CaracteristicaDemograficaDto.setParroquiaDto(parroquiaDto);

        solicitudEstudioDto.setCaracteristica_DemograficaDto(CaracteristicaDemograficaDto);

        MarcaDto marcaDto=new MarcaDto(0);
        solicitudEstudioDto.setMarcaDto(marcaDto);

        ClienteDto clienteDto= new ClienteDto(3);
        solicitudEstudioDto.setClienteDto(clienteDto);

    }
}
