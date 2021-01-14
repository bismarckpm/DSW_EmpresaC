package ucab.dsw.test;

import org.junit.Assert;
import org.junit.Test;

import ucab.dsw.servicio.AnalistaServicio;

import javax.ws.rs.core.Response;


public class AnalistaServicioTest {


    @Test
    public void consultaEstudios_asignadosTest() throws Exception
    {
        AnalistaServicio servicio = new AnalistaServicio();
        Response respuesta= servicio.consultaEstudios_asignados(15);
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());


    }

    @Test
    public void consultaEstudios_telefonoTest() throws Exception
    {
        AnalistaServicio servicio = new AnalistaServicio();
        Response respuesta= servicio.consultaEstudios_telefono(11);
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());


    }
    @Test
    public void Eliminar_ParticipacionTest() throws Exception
    {
        AnalistaServicio servicio = new AnalistaServicio();
        Response respuesta= servicio.Eliminar_Participacion(13);
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());
    }

    @Test
    public void respuestas_porcentajeTest() throws Exception
    {
        AnalistaServicio servicio = new AnalistaServicio();
        Response respuesta= servicio.respuestas_porcentaje(17);
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());
    }

    @Test
    public void empezarEstudioTest() throws Exception
    {
        AnalistaServicio servicio = new AnalistaServicio();

        Response resultado = servicio.Empezar_estudio( 1);
        Assert.assertNotEquals( 1, 0 );

    }

    @Test
    public void respuestas_estudio_Test() throws Exception
    {
        AnalistaServicio servicio = new AnalistaServicio();

        Response resultado = servicio.respuestas_estudio( 5);
        Assert.assertNotEquals( 1, 0 );

    }
}