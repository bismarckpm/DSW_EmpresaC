import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

public class LugarServicioTest {

    @Test
    public void getPaises() throws Exception
    {
        ucab.dsw.servicio.PaisServicio servicio = new ucab.dsw.servicio.PaisServicio();
        Response respuesta= servicio.getAllPaises();
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());
    }

   @Test
    public void getEstados() throws Exception
    {
        ucab.dsw.servicio.EstadoServicio servicio = new ucab.dsw.servicio.EstadoServicio();
        Response respuesta= servicio.getAllEstados();
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());
    }

    @Test
    public void getCiudades() throws Exception
    {
        ucab.dsw.servicio.CiudadServicio servicio = new ucab.dsw.servicio.CiudadServicio();
        Response respuesta= servicio.getAllCiudades();
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());
    }

    @Test
    public void getPorroquias() throws Exception
    {
        ucab.dsw.servicio.ParroquiaServicio servicio = new ucab.dsw.servicio.ParroquiaServicio();
        Response respuesta= servicio.getAllParroquias();
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());
    }

}
