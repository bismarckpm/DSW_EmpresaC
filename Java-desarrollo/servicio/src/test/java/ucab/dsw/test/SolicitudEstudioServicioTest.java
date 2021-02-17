package ucab.dsw.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ucab.dsw.clases.ValidaCamposSolicitudDto;
import ucab.dsw.dtos.*;
import ucab.dsw.dtos.SolicitudEstudioDto;
import ucab.dsw.entidades.Usuario;
import ucab.dsw.excepciones.CamposNulosExcepcion;
import ucab.dsw.jwt.Jwt;
import ucab.dsw.servicio.SolicitudEstudioServicio;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;


public class SolicitudEstudioServicioTest {

    public String token;

    @Before
    public void colocarToken(){
        ucab.dsw.accesodatos.DaoUsuario dao=new ucab.dsw.accesodatos.DaoUsuario();
        Usuario usuario=dao.find((long) 1,Usuario.class);
        this.token= Jwt.generarToken(1);
        usuario.set_token(this.token);
        Usuario resul= dao.update(usuario);
    }

    @Test
    public void AddSolicitud() throws Exception
    {
        SolicitudEstudioServicio servicio = new SolicitudEstudioServicio();

        /*--------------Dto--------------*/

        SolicitudEstudioDto solicitudEstudioDto=new SolicitudEstudioDto();

        solicitudEstudioDto.setModoencuesta("online");

        CaracteristicaDemograficaDto CaracteristicaDemograficaDto = new CaracteristicaDemograficaDto();
        CaracteristicaDemograficaDto.setEdad_min(15);
        CaracteristicaDemograficaDto.setEdad_max(80);
        CaracteristicaDemograficaDto.setNivel_socioeconomico("Media");
        CaracteristicaDemograficaDto.setNacionalidad("britanico");
        CaracteristicaDemograficaDto.setCantidad_hijos(1);
        CaracteristicaDemograficaDto.setGenero("M");

        NivelAcademicoDto nivel_academicoDto=new NivelAcademicoDto(1);
        ParroquiaDto parroquiaDto= new ParroquiaDto(1);

        CaracteristicaDemograficaDto.setNivel_AcademicoDto(nivel_academicoDto);
        CaracteristicaDemograficaDto.setParroquiaDto(parroquiaDto);

        solicitudEstudioDto.setCaracteristica_DemograficaDto(CaracteristicaDemograficaDto);

        MarcaDto marcaDto=new MarcaDto(2);
        solicitudEstudioDto.setMarcaDto(marcaDto);

        ClienteDto clienteDto= new ClienteDto(1);
        solicitudEstudioDto.setClienteDto(clienteDto);

        Response respuesta= servicio.addSolicitud(this.token,solicitudEstudioDto);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertNotEquals(0,responseDto.get("solicitud_id"));

    }

    @Test
    public void AddSolicitudSinDatosCompletos() throws Exception
    {
        SolicitudEstudioServicio servicio = new SolicitudEstudioServicio();



        SolicitudEstudioDto solicitudEstudioDto=new SolicitudEstudioDto();

        solicitudEstudioDto.setModoencuesta("online");

        CaracteristicaDemograficaDto CaracteristicaDemograficaDto = new CaracteristicaDemograficaDto();
        CaracteristicaDemograficaDto.setEdad_min(10);
        CaracteristicaDemograficaDto.setEdad_max(20);
        CaracteristicaDemograficaDto.setNivel_socioeconomico("Alta");
        CaracteristicaDemograficaDto.setNacionalidad("");
        CaracteristicaDemograficaDto.setCantidad_hijos(3);
        CaracteristicaDemograficaDto.setGenero("F");

        NivelAcademicoDto nivel_academicoDto=new NivelAcademicoDto(5);
        ParroquiaDto parroquiaDto= new ParroquiaDto(1);

        CaracteristicaDemograficaDto.setNivel_AcademicoDto(nivel_academicoDto);
        CaracteristicaDemograficaDto.setParroquiaDto(parroquiaDto);

        solicitudEstudioDto.setCaracteristica_DemograficaDto(CaracteristicaDemograficaDto);

        MarcaDto marcaDto=new MarcaDto(0);
        solicitudEstudioDto.setMarcaDto(marcaDto);

        ClienteDto clienteDto= new ClienteDto(3);
        solicitudEstudioDto.setClienteDto(clienteDto);

        Response respuesta= servicio.addSolicitud(this.token,solicitudEstudioDto);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertNotEquals(0,responseDto.get("solicitud_id"));

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

        Response respuesta= servicio.addSolicitud(this.token,solicitudEstudioDto);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertNotEquals(0,responseDto.get("solicitud_id"));

    }

    @After
    public void quitarToken(){
        ucab.dsw.accesodatos.DaoUsuario dao=new ucab.dsw.accesodatos.DaoUsuario();
        Usuario usuario=dao.find((long) 1,Usuario.class);
        usuario.set_token(null);
        Usuario resul= dao.update(usuario);
    }
}
