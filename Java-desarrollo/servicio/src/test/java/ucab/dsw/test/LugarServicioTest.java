package ucab.dsw.test;

import org.junit.Assert;
import org.junit.Test;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;

public class LugarServicioTest {

    @Test
    public void getPaises() throws Exception
    {
        ucab.dsw.servicio.PaisServicio servicio = new ucab.dsw.servicio.PaisServicio();
        Response respuesta= servicio.getAllPaises();
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("paises"));
    }

   @Test
    public void getEstados() throws Exception
    {
        ucab.dsw.servicio.EstadoServicio servicio = new ucab.dsw.servicio.EstadoServicio();
        Response respuesta= servicio.getAllEstados();
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("estados"));
    }

    @Test
    public void getCiudades() throws Exception
    {
        ucab.dsw.servicio.CiudadServicio servicio = new ucab.dsw.servicio.CiudadServicio();
        Response respuesta= servicio.getAllCiudades();
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("ciudades"));
    }

    @Test
    public void getPorroquias() throws Exception
    {
        ucab.dsw.servicio.ParroquiaServicio servicio = new ucab.dsw.servicio.ParroquiaServicio();
        Response respuesta= servicio.getAllParroquias();
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("parroquias"));

    }

}
