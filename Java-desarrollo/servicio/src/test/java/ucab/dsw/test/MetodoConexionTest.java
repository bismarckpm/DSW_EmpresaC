package ucab.dsw.test;

import org.junit.Assert;
import org.junit.Test;
import ucab.dsw.dtos.CategoriaDto;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;

public class MetodoConexionTest {

    @Test
    public void getMetodosConexion()
    {

        ucab.dsw.servicio.MetodoConexionServicio servicio = new ucab.dsw.servicio.MetodoConexionServicio();
        Response respuesta= servicio.getAllMetodoConexion();
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("metodos_conexion"));

    }
}
