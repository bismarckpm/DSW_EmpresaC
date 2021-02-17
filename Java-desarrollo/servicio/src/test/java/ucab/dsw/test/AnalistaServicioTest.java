package ucab.dsw.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ucab.dsw.dtos.RespuestaAnalistaDto;
import ucab.dsw.dtos.SolicitudEstudioDto;
import ucab.dsw.entidades.Usuario;
import ucab.dsw.jwt.Jwt;
import ucab.dsw.servicio.AnalistaServicio;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;


public class AnalistaServicioTest {

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
    public void consultaEstudios_asignadosTest() throws Exception
    {
        AnalistaServicio servicio = new AnalistaServicio();
        Response respuesta= servicio.consultaEstudios_asignados(this.token,15);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertNotNull(responseDto.get("estudios"));


    }

    @Test
    public void consultaEstudios_telefonoTest() throws Exception
    {
        AnalistaServicio servicio = new AnalistaServicio();
        Response respuesta= servicio.consultaEstudios_telefono(this.token,15);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertNotNull(responseDto.get("estudios"));


    }
    @Test
    public void Eliminar_ParticipacionTest() throws Exception
    {
        AnalistaServicio servicio = new AnalistaServicio();
        Response respuesta= servicio.Eliminar_Participacion(this.token,10);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertEquals("\"inactivo\"",responseDto.get("estado_participacion").toString());
    }

    @Test
    public void preguntas_porcentajeTest() throws Exception
    {
        AnalistaServicio servicio = new AnalistaServicio();
        Response respuesta= servicio.respuestas_porcentaje(this.token,4);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertNotNull(responseDto.get("Preguntas"));
    }

    @Test
    public void empezarEstudioTest() throws Exception
    {
        AnalistaServicio servicio = new AnalistaServicio();
        Response respuesta = servicio.Empezar_estudio( this.token,45);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertEquals("\"en progreso\"",responseDto.get("estado_estudio").toString());

    }

    @Test
    public void respuestas_estudio_Test() throws Exception
    {
        AnalistaServicio servicio = new AnalistaServicio();

        Response respuesta= servicio.respuestas_estudio( this.token,4);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertNotNull(responseDto.get("participaciones"));

    }

    @Test
    public void responder_estudio_Test() throws Exception
    {
        AnalistaServicio servicio = new AnalistaServicio();
        RespuestaAnalistaDto dto=new RespuestaAnalistaDto();
        SolicitudEstudioDto estudioDto=new SolicitudEstudioDto(24);
        dto.setSolicituEstudioDto(estudioDto);
        dto.setRespueta("Una respuesta bien");

        Response respuesta= servicio.ResponderEstudio(this.token,dto);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertEquals("\"Una respuesta bien\"",responseDto.get("resultado").toString());

    }

    @After
    public void quitarToken(){
        ucab.dsw.accesodatos.DaoUsuario dao=new ucab.dsw.accesodatos.DaoUsuario();
        Usuario usuario=dao.find((long) 1,Usuario.class);
        usuario.set_token(null);
        Usuario resul= dao.update(usuario);
    }
}