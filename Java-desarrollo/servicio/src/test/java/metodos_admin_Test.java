import org.junit.Assert;
import org.junit.Test;

import ucab.dsw.accesodatos.DaoPregunta;
import ucab.dsw.accesodatos.DaoSolicitudEstudio;
import javax.ws.rs.core.Response;
import ucab.dsw.dtos.*;

import ucab.dsw.entidades.*;

import java.util.ArrayList;
import java.util.List;


public class metodos_admin_Test {


    @Test
    public void find_asignadosTest() throws Exception
    {
        ucab.dsw.servicio.metodos_admin servicio = new ucab.dsw.servicio.metodos_admin();
        Response resultado= servicio.consultaEstudios_asignados(1);
        Assert.assertNotEquals( resultado, 0 );


    }

    @Test
    public void find_no_asignadosTest() throws Exception
    {
        ucab.dsw.servicio.metodos_admin servicio = new ucab.dsw.servicio.metodos_admin();
        Response resultado= servicio.consultaEstudios_no_asignados(1);
        Assert.assertNotEquals( resultado, 0 );


    }


    @Test
    public void EliminarEstudioTest() throws Exception
    {
        ucab.dsw.servicio.metodos_admin servicio = new ucab.dsw.servicio.metodos_admin();

        Response resultado = servicio.EliminarEstudio( 1 );
        Assert.assertNotEquals( resultado, 0 );

    }

    @Test
    public void addEncuestaTest() throws Exception
    {
        ucab.dsw.servicio.metodos_admin servicio = new ucab.dsw.servicio.metodos_admin();
        EncuestaDto encuestaDto = new EncuestaDto();
        PreguntaDto preguntaDto = new PreguntaDto(1);

        encuestaDto.setNombre( "siva?" );



        List<PreguntaDto> pregunta = new ArrayList<>();

        pregunta.add(preguntaDto);

        Response resultado = servicio.addEncuesta( 3,6,encuestaDto);
        Assert.assertNotEquals( resultado, 1 );
    }

    @Test
    public void addPreguntaTest() throws Exception
    {
        ucab.dsw.servicio.metodos_admin servicio = new ucab.dsw.servicio.metodos_admin();
        PreguntaDto preguntaDto = new PreguntaDto();
        Opcion_Simple_MultipleDto opcion_Simple_MultipleDto= new Opcion_Simple_MultipleDto();

        opcion_Simple_MultipleDto.setOpcion("carlos");

        preguntaDto.setDescripcion( "te calma este color?" );
        preguntaDto.setTipopregunta( "Opcion simple" );

        List<Opcion_Simple_MultipleDto> opcion = new ArrayList<>();
        opcion.add(opcion_Simple_MultipleDto);
        preguntaDto.setOpciones(opcion);

        Response resultado = servicio.addPregunta( preguntaDto);
        Assert.assertNotEquals( resultado, 1 );
    }

    @Test
    public void Participacion_estudioTest() throws Exception
    {
        ucab.dsw.servicio.metodos_admin servicio = new ucab.dsw.servicio.metodos_admin();
        Response resultado= servicio.Participacion_estudio(5);
        Assert.assertNotEquals( resultado, 0 );


    }

    @Test
    public void Pregunta_CategoriaTest() throws Exception
    {
        ucab.dsw.servicio.metodos_admin servicio = new ucab.dsw.servicio.metodos_admin();
        Response resultado= servicio.Preguntas_categoria(5);
        Assert.assertNotEquals( resultado, 0 );


    }
}


