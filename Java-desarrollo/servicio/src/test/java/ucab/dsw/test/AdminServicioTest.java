package ucab.dsw.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import ucab.dsw.dtos.*;

import ucab.dsw.entidades.Usuario;
import ucab.dsw.servicio.AdminServicio;

import java.util.ArrayList;
import java.util.List;
import ucab.dsw.jwt.Jwt;


public class AdminServicioTest {

    public String token;

    @Before
    public void colocarToken(){
        ucab.dsw.accesodatos.DaoUsuario dao=new ucab.dsw.accesodatos.DaoUsuario();
        Usuario usuario=dao.find((long) 1,Usuario.class);
        this.token= Jwt.generarToken(1);
        usuario.set_token(this.token);
        Usuario resul= dao.update(usuario);
    }

    @Test
    public void find_asignadosTest() throws Exception
    {
        AdminServicio servicio = new AdminServicio();
        Response respuesta= servicio.consultaEstudios_asignados(this.token,20);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertNotNull(responseDto.get("estudios"));

    }

    @Test
    public void find_no_asignadosTest() throws Exception
    {
        AdminServicio servicio = new AdminServicio();
        Response respuesta= servicio.consultaEstudios_no_asignados(this.token,19);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertNotNull(responseDto.get("estudios"));
    }

    @Test
    public void EliminarEstudioTest() throws Exception
    {
        AdminServicio servicio = new AdminServicio();
        Response respuesta = servicio.EliminarEstudio( this.token,13 );
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertEquals("\"inactivo\"",responseDto.get("estudio_estado").toString());
    }

    @Test
    public void addEncuestaTest() throws Exception
    {
        AdminServicio servicio = new AdminServicio();
        EncuestaDto encuestaDto = new EncuestaDto();
        encuestaDto.setNombre( "encuesta el mejor chocolate" );
        Response respuesta = servicio.addEncuesta( this.token,3,13,encuestaDto);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertNotNull(responseDto.get("encuesta"));
    }

    @Test
    public void addPreguntaTest() throws Exception
    {
        AdminServicio servicio = new AdminServicio();
        PreguntaDto preguntaDto = new PreguntaDto();
        OpcionSimpleMultipleDto opcion_Simple_MultipleDto= new OpcionSimpleMultipleDto();

        opcion_Simple_MultipleDto.setOpcion("un poco");

        preguntaDto.setDescripcion( "te calma este color?" );
        preguntaDto.setTipopregunta( "Opcion simple" );

        List<OpcionSimpleMultipleDto> opcion = new ArrayList<>();
        opcion.add(opcion_Simple_MultipleDto);
        preguntaDto.setOpciones(opcion);

        Response respuesta = servicio.addPregunta( this.token,preguntaDto);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertNotNull(responseDto.get("pregunta"));
    }

    @Test
    public void Participacion_estudioTest() throws Exception
    {
        AdminServicio servicio = new AdminServicio();
        Response respuesta= servicio.Participacion_estudio(this.token,5);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertNotNull(responseDto.get("participantes"));
    }

    @Test
    public void buscarEstudioTest() throws Exception
    {
        AdminServicio servicio = new AdminServicio();
        Response respuesta= servicio.buscarEstudio(this.token,13);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertNotNull(responseDto.get("estudio"));
    }

    @Test
    public void Pregunta_CategoriaTest() throws Exception
    {
        AdminServicio servicio = new AdminServicio();
        Response respuesta= servicio.Preguntas_categoria(this.token,1);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertNotNull(responseDto.get("preguntas"));
    }

    @Test
    public void add_Participacion_test() throws Exception
    {
        AdminServicio servicio = new AdminServicio();
        Response respuesta= servicio.add_Participacion(this.token,13);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertNotNull(responseDto.get("participantes"));
    }
    @After
    public void quitarToken(){
        ucab.dsw.accesodatos.DaoUsuario dao=new ucab.dsw.accesodatos.DaoUsuario();
        Usuario usuario=dao.find((long) 1,Usuario.class);
        usuario.set_token(null);
        Usuario resul= dao.update(usuario);
    }
}


