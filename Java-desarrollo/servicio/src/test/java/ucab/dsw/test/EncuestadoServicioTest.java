package ucab.dsw.test;

import org.junit.Assert;
import org.junit.Test;
import ucab.dsw.accesodatos.DaoOpcionSimpleMultiplePregunta;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import ucab.dsw.dtos.*;
import ucab.dsw.servicio.EncuestadoServicio;
import java.util.ArrayList;
import java.util.List;


public class EncuestadoServicioTest {
    @Test
    public void find_asignadosTest() throws Exception {
        EncuestadoServicio servicio = new EncuestadoServicio();
        Response respuesta = servicio.consultaEstudios_asignados(1);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("estudios"));
    }
    @Test
    public void encuesta_estudioTest() throws Exception {
        EncuestadoServicio servicio = new EncuestadoServicio();
        Response respuesta = servicio.encuesta_estudio(13);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("encuesta"));
    }
    @Test
    public void addRespuestaTest() throws Exception
    {
        EncuestadoServicio servicio = new EncuestadoServicio();
        RespuestaDto respuestaDto = new RespuestaDto();

        Response respuesta = servicio.addRespuesta( 30,14,1,respuestaDto);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("respuesta"));
    }
    @Test
    public void PreguntaEstudioTest() throws Exception {
        EncuestadoServicio servicio = new EncuestadoServicio();
        Response respuesta = servicio.pregunta_estudio(14,1);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("preguntas"));
    }
    @Test
    public void finalizarTest() throws Exception
    {
        EncuestadoServicio servicio = new EncuestadoServicio();
        Response respuesta = servicio.finalizarParticipacion( 14,1);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertEquals("\"inactivo\"",responseDto.get("participacion_estado").toString());

    }

    @Test
    public void getEncuestadoId() throws Exception
    {
        EncuestadoServicio servicio = new EncuestadoServicio();
        Response respuesta= servicio.getEncuestadoId(2);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("encuestado"));
    }
}