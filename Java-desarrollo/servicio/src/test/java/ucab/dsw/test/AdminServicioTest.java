package ucab.dsw.test;

import org.junit.Assert;
import org.junit.Test;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import ucab.dsw.dtos.*;

import ucab.dsw.servicio.AdminServicio;

import java.util.ArrayList;
import java.util.List;


public class AdminServicioTest {


    @Test
    public void find_asignadosTest() throws Exception
    {
        AdminServicio servicio = new AdminServicio();
        Response respuesta= servicio.consultaEstudios_asignados(20);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("estudios"));

    }

    @Test
    public void find_no_asignadosTest() throws Exception
    {
        AdminServicio servicio = new AdminServicio();
        Response respuesta= servicio.consultaEstudios_no_asignados(19);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("estudios"));
    }

    @Test
    public void EliminarEstudioTest() throws Exception
    {
        AdminServicio servicio = new AdminServicio();
        Response respuesta = servicio.EliminarEstudio( 13 );
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertEquals("\"inactivo\"",responseDto.get("estudio_estado").toString());
    }

    @Test
    public void addEncuestaTest() throws Exception
    {
        AdminServicio servicio = new AdminServicio();
        EncuestaDto encuestaDto = new EncuestaDto();
        encuestaDto.setNombre( "siva?" );

        Response resultado = servicio.addEncuesta( 3,11,encuestaDto);
        Assert.assertNotEquals( 0, 1 );
    }

    @Test
    public void addPreguntaTest() throws Exception
    {
        AdminServicio servicio = new AdminServicio();
        PreguntaDto preguntaDto = new PreguntaDto();
        OpcionSimpleMultipleDto opcion_Simple_MultipleDto= new OpcionSimpleMultipleDto();

        opcion_Simple_MultipleDto.setOpcion("carlos");

        preguntaDto.setDescripcion( "te calma este color?" );
        preguntaDto.setTipopregunta( "Opcion simple" );

        List<OpcionSimpleMultipleDto> opcion = new ArrayList<>();
        opcion.add(opcion_Simple_MultipleDto);
        preguntaDto.setOpciones(opcion);

        Response respuesta = servicio.addPregunta( preguntaDto);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("pregunta"));
    }

    @Test
    public void Participacion_estudioTest() throws Exception
    {
        AdminServicio servicio = new AdminServicio();
        Response respuesta= servicio.Participacion_estudio(5);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("participantes"));
    }

    @Test
    public void buscarEstudioTest() throws Exception
    {
        AdminServicio servicio = new AdminServicio();
        Response respuesta= servicio.buscarEstudio(13);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("estudio"));
    }

    @Test
    public void Pregunta_CategoriaTest() throws Exception
    {
        AdminServicio servicio = new AdminServicio();
        Response respuesta= servicio.Preguntas_categoria(1);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("preguntas"));
    }

    @Test
    public void add_Participacion_test() throws Exception
    {
        AdminServicio servicio = new AdminServicio();
        Response resultado= servicio.add_Participacion(10);
        Assert.assertNotEquals( resultado, 0 );


    }

}


