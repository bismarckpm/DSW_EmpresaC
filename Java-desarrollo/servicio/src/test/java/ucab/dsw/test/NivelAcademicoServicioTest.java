package ucab.dsw.test;

import org.junit.Assert;
import org.junit.Test;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;

public class NivelAcademicoServicioTest {

    @Test
    public void getNivelesAcademicos() throws Exception
    {
        ucab.dsw.servicio.NivelAcademicoServicio servicio = new ucab.dsw.servicio.NivelAcademicoServicio();
        Response respuesta= servicio.getAllNivelesAcademicos();
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("niveles_academicos"));
    }

}