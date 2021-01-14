package ucab.dsw.test;

import org.junit.Assert;
import org.junit.Test;

import ucab.dsw.servicio.ClienteServicio;

import javax.ws.rs.core.Response;


public class ClienteServicioTest {


    @Test
    public void consultaEstudios_SolicitadosTest() throws Exception
    {
        ClienteServicio servicio = new ClienteServicio();
        Response respuesta= servicio.consultaEstudios_Solicitados(5);
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());
    }

    @Test
    public void getClienteId() throws Exception
    {
        ClienteServicio servicio = new ClienteServicio();
        Response respuesta= servicio.getClienteId(10);
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());
    }

}