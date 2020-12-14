import org.junit.Assert;
import org.junit.Test;

import ucab.dsw.accesodatos.DaoPregunta;
import ucab.dsw.accesodatos.DaoSolicitudEstudio;
import javax.ws.rs.core.Response;
import ucab.dsw.dtos.*;

import ucab.dsw.entidades.*;

import java.util.ArrayList;
import java.util.List;


public class metodos_encuestadoTest {


    @Test
    public void find_asignadosTest() throws Exception {
        ucab.dsw.servicio.metodos_encuestados servicio = new ucab.dsw.servicio.metodos_encuestados();
        Response resultado = servicio.consultaEstudios_asignados(1);
        Assert.assertNotEquals(resultado, 0);


    }

    @Test
    public void encuesta_estudioTest() throws Exception {
        ucab.dsw.servicio.metodos_encuestados servicio = new ucab.dsw.servicio.metodos_encuestados();
        Response resultado = servicio.encuesta_estudio(1);
        Assert.assertNotEquals(resultado, 0);

    }

}