import org.junit.Assert;
import org.junit.Test;

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
        Response resultado= servicio.consultaEstudios_asignados(19);
        Assert.assertNotEquals( resultado, 0 );

    }

    @Test
    public void find_no_asignadosTest() throws Exception
    {
        AdminServicio servicio = new AdminServicio();
        Response resultado= servicio.consultaEstudios_no_asignados(19);
        Assert.assertNotEquals( resultado, 0 );


    }

    @Test
    public void EliminarEstudioTest() throws Exception
    {
        AdminServicio servicio = new AdminServicio();
        Response resultado = servicio.EliminarEstudio( 1 );
        Assert.assertNotEquals( resultado, 0 );
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

        Response resultado = servicio.addPregunta( preguntaDto);
        Assert.assertNotEquals( resultado, 1 );
    }

    @Test
    public void Participacion_estudioTest() throws Exception
    {
        AdminServicio servicio = new AdminServicio();
        Response resultado= servicio.Participacion_estudio(5);
        Assert.assertNotEquals( resultado, 0 );


    }

    @Test
    public void Pregunta_CategoriaTest() throws Exception
    {
        AdminServicio servicio = new AdminServicio();
        Response resultado= servicio.Preguntas_categoria(1);
        Assert.assertNotEquals( resultado, 0 );


    }

    @Test
    public void add_Participacion_test() throws Exception
    {
        AdminServicio servicio = new AdminServicio();
        Response resultado= servicio.add_Participacion(10);
        Assert.assertNotEquals( resultado, 0 );


    }

}


