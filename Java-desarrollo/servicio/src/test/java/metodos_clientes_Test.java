import org.junit.Assert;
import org.junit.Test;

import ucab.dsw.accesodatos.DaoPregunta;
import ucab.dsw.accesodatos.DaoSolicitudEstudio;
import ucab.dsw.dtos.*;

import ucab.dsw.entidades.*;

import javax.ws.rs.core.Response;
import java.util.List;


public class metodos_clientes_Test {


    @Test
    public void consultaEstudios_SolicitadosTest() throws Exception
    {
        ucab.dsw.servicio.metodos_clientes servicio = new ucab.dsw.servicio.metodos_clientes();
        Response respuesta= servicio.consultaEstudios_Solicitados(3);
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());
    }

}