package ucab.dsw.test;

import org.junit.Assert;
import org.junit.Test;

import ucab.dsw.servicio.ClienteServicio;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;


public class ClienteServicioTest {


    @Test
    public void consultaEstudios_SolicitadosTest() throws Exception
    {
        ClienteServicio servicio = new ClienteServicio();
        Response respuesta= servicio.consultaEstudios_Solicitados(5);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("estudio"));
    }

   @Test
    public void getClienteId() throws Exception
    {
        ClienteServicio servicio = new ClienteServicio();
        Response respuesta= servicio.getClienteId(6);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("cliente"));
    }
    @Test
    public void respuesta_analistaTest() throws Exception
    {
        ClienteServicio servicio = new ClienteServicio();
        Response respuesta= servicio.respuesta_analista(13);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("respuesta"));
    }
}