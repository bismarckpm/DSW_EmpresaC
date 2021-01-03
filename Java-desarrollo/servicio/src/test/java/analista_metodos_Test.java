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
        Response respuesta= servicio.consultaEstudios_asignados(15);
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());


    }

    @Test
    public void consultaEstudios_telefonoTest() throws Exception
    {
        ucab.dsw.servicio.analista_metodos servicio = new ucab.dsw.servicio.analista_metodos();
        Response respuesta= servicio.consultaEstudios_telefono(11);
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());


    }

    @Test
    public void Estudios_telefonoTest() throws Exception
    {
        ucab.dsw.servicio.analista_metodos servicio = new ucab.dsw.servicio.analista_metodos();
        Response respuesta= servicio.Estudio_telefono(5);
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());


    }

    @Test
    public void Eliminar_ParticipacionTest() throws Exception
    {
        ucab.dsw.servicio.analista_metodos servicio = new ucab.dsw.servicio.analista_metodos();
        Response respuesta= servicio.Eliminar_Participacion(13);
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());
    }

    @Test
    public void respuestas_porcentajeTest() throws Exception
    {
        ucab.dsw.servicio.analista_metodos servicio = new ucab.dsw.servicio.analista_metodos();
        Response respuesta= servicio.respuestas_porcentaje(17);
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());
    }
}