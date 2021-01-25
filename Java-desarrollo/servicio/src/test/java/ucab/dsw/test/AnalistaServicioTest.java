package ucab.dsw.test;

import org.junit.Assert;
import org.junit.Test;

import ucab.dsw.dtos.RespuestaAnalistaDto;
import ucab.dsw.dtos.SolicitudEstudioDto;
import ucab.dsw.servicio.AnalistaServicio;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;


public class AnalistaServicioTest {


    @Test
    public void consultaEstudios_asignadosTest() throws Exception
    {
        AnalistaServicio servicio = new AnalistaServicio();
        Response respuesta= servicio.consultaEstudios_asignados(15);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("estudios"));


    }

    @Test
    public void consultaEstudios_telefonoTest() throws Exception
    {
        AnalistaServicio servicio = new AnalistaServicio();
        Response respuesta= servicio.consultaEstudios_telefono(15);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("estudios"));


    }
    @Test
    public void Eliminar_ParticipacionTest() throws Exception
    {
        AnalistaServicio servicio = new AnalistaServicio();
        Response respuesta= servicio.Eliminar_Participacion(10);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertEquals("\"inactivo\"",responseDto.get("estado_participacion").toString());
    }

    @Test
    public void preguntas_porcentajeTest() throws Exception
    {
        AnalistaServicio servicio = new AnalistaServicio();
        Response respuesta= servicio.respuestas_porcentaje(4);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("Preguntas"));
    }

    @Test
    public void empezarEstudioTest() throws Exception
    {
        AnalistaServicio servicio = new AnalistaServicio();
        Response respuesta = servicio.Empezar_estudio( 26);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertEquals("\"en progreso\"",responseDto.get("estado_estudio").toString());

    }

    @Test
    public void respuestas_estudio_Test() throws Exception
    {
        AnalistaServicio servicio = new AnalistaServicio();

        Response respuesta= servicio.respuestas_estudio( 4);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
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

        Response respuesta= servicio.ResponderEstudio(dto);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertEquals("\"Una respuesta bien\"",responseDto.get("resultado").toString());

    }
}