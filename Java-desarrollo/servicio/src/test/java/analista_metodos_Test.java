import org.junit.Assert;
import org.junit.Test;

import ucab.dsw.accesodatos.DaoPregunta;
import ucab.dsw.accesodatos.DaoSolicitudEstudio;
import ucab.dsw.dtos.*;
import javax.ws.rs.core.Response;
import ucab.dsw.entidades.*;

import java.util.List;


public class analista_metodos_Test {


    @Test
    public void consultaEstudios_asignadosTest() throws Exception
    {
        ucab.dsw.servicio.analista_metodos servicio = new ucab.dsw.servicio.analista_metodos();
        Response resultado= servicio.consultaEstudios_asignados(7);
        Assert.assertNotEquals( resultado, 0 );


    }
}