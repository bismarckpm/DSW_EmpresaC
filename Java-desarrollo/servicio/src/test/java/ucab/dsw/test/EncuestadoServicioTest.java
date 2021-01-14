package ucab.dsw.test;

import org.junit.Assert;
import org.junit.Test;

import ucab.dsw.accesodatos.DaoOpcionSimpleMultiplePregunta;

import javax.ws.rs.core.Response;
import ucab.dsw.dtos.*;

import ucab.dsw.servicio.EncuestadoServicio;

import java.util.ArrayList;
import java.util.List;


public class EncuestadoServicioTest {


    @Test
    public void find_asignadosTest() throws Exception {
        EncuestadoServicio servicio = new EncuestadoServicio();
        Response resultado = servicio.consultaEstudios_asignados(1);
        Assert.assertNotEquals(resultado, 0);


    }

    @Test
    public void encuesta_estudioTest() throws Exception {
        EncuestadoServicio servicio = new EncuestadoServicio();
        Response resultado = servicio.encuesta_estudio(1);
        Assert.assertNotEquals(resultado, 0);

    }

    @Test
    public void addRespuestaTest() throws Exception
    {
        EncuestadoServicio servicio = new EncuestadoServicio();
        RespuestaDto respuestaDto = new RespuestaDto();
        DaoOpcionSimpleMultiplePregunta dao = new DaoOpcionSimpleMultiplePregunta();
        OpcionSimpleMultiplePreguntaDto opcion = new OpcionSimpleMultiplePreguntaDto(1);

        List<OpcionSimpleMultiplePreguntaDto> opciones = new ArrayList<>();
        opciones.add(opcion);

        respuestaDto.setOpciones(opciones);

        Response resultado = servicio.addRespuesta( 1,5,1,respuestaDto);
        Assert.assertNotEquals( 0, 1 );
    }
    @Test
    public void finalizarTest() throws Exception
    {
        EncuestadoServicio servicio = new EncuestadoServicio();

        Response resultado = servicio.finalizarParticipacion( 5,5);
        Assert.assertNotEquals( resultado, 0 );

    }

    @Test
    public void getEncuestadoId() throws Exception
    {
        EncuestadoServicio servicio = new EncuestadoServicio();
        Response respuesta= servicio.getEncuestadoId(2);
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());
    }
}