package ucab.dsw.test;

import org.junit.Assert;
import org.junit.Test;
import ucab.dsw.dtos.CategoriaDto;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;

public class OcupacionServicioTest {

    @Test
    public void getOcupaciones()
    {

        ucab.dsw.servicio.OcupacionServicio servicio = new ucab.dsw.servicio.OcupacionServicio();
        Response respuesta= servicio.getAllOcupaciones();
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("ocupaciones"));

    }
}
