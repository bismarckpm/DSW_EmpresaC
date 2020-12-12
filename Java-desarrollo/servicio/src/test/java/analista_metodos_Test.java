import org.junit.Assert;
import org.junit.Test;

import ucab.dsw.accesodatos.DaoPregunta;
import ucab.dsw.accesodatos.DaoSolicitudEstudio;
import ucab.dsw.dtos.*;

import ucab.dsw.entidades.*;

import javax.ws.rs.core.Response;
import java.util.List;


public class analista_metodos_Test {


    @Test
    public void consultaEstudios_asignadosTest() throws Exception
    {
        ucab.dsw.servicio.analista_metodos servicio = new ucab.dsw.servicio.analista_metodos();
        Response respuesta= servicio.consultaEstudios_asignados(11);
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());


    }
}