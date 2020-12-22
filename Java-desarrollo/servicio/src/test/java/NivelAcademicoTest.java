import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

public class NivelAcademicoTest {

    @Test
    public void getNivelesAcademicos() throws Exception
    {
        ucab.dsw.servicio.NivelAcademicoServicio servicio = new ucab.dsw.servicio.NivelAcademicoServicio();
        Response respuesta= servicio.getAllNivelesAcademicos();
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());
    }

}