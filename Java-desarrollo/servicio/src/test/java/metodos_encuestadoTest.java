import org.junit.Assert;
import org.junit.Test;

import ucab.dsw.accesodatos.DaoOpcion_Simple_Multiple_Pregunta;
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

    @Test
    public void addRespuestaTest() throws Exception
    {
        ucab.dsw.servicio.metodos_encuestados servicio = new ucab.dsw.servicio.metodos_encuestados();
        RespuestaDto respuestaDto = new RespuestaDto();
        DaoOpcion_Simple_Multiple_Pregunta dao = new DaoOpcion_Simple_Multiple_Pregunta();
        Opcion_Simple_Multiple_PreguntaDto opcion = new Opcion_Simple_Multiple_PreguntaDto(1);

        List<Opcion_Simple_Multiple_PreguntaDto> opciones = new ArrayList<>();
        opciones.add(opcion);

        respuestaDto.setOpciones(opciones);

        Response resultado = servicio.addRespuesta( 1,5,1,respuestaDto);
        Assert.assertNotEquals( 0, 1 );
    }
    @Test
    public void finalizarTest() throws Exception
    {
        ucab.dsw.servicio.metodos_encuestados servicio = new ucab.dsw.servicio.metodos_encuestados();

        Response resultado = servicio.finalizarParticipacion( 5,5);
        Assert.assertNotEquals( resultado, 0 );

    }

    @Test
    public void getEncuestadoId() throws Exception
    {
        ucab.dsw.servicio.metodos_encuestados servicio = new ucab.dsw.servicio.metodos_encuestados();
        Response respuesta= servicio.getEncuestadoId(2);
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());
    }
}