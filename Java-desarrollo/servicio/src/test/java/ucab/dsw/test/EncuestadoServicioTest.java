package ucab.dsw.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import ucab.dsw.dtos.*;
import ucab.dsw.entidades.Usuario;
import ucab.dsw.servicio.EncuestadoServicio;
import ucab.dsw.jwt.Jwt;



public class EncuestadoServicioTest {

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
    public void find_asignadosTest() throws Exception {
        EncuestadoServicio servicio = new EncuestadoServicio();
        Response respuesta = servicio.consultaEstudios_asignados(this.token,1);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertNotNull(responseDto.get("estudios"));
    }
    @Test
    public void encuesta_estudioTest() throws Exception {
        EncuestadoServicio servicio = new EncuestadoServicio();
        Response respuesta = servicio.encuesta_estudio(this.token,13);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertNotNull(responseDto.get("encuesta"));
    }
    @Test
    public void addRespuestaTest() throws Exception
    {
        EncuestadoServicio servicio = new EncuestadoServicio();
        RespuestaDto respuestaDto = new RespuestaDto();

        Response respuesta = servicio.addRespuesta( this.token,30,14,1,respuestaDto);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertNotNull(responseDto.get("respuesta"));
    }
    @Test
    public void PreguntaEstudioTest() throws Exception {
        EncuestadoServicio servicio = new EncuestadoServicio();
        Response respuesta = servicio.pregunta_estudio(this.token,14,1);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertNotNull(responseDto.get("preguntas"));
    }
    @Test
    public void finalizarTest() throws Exception
    {
        EncuestadoServicio servicio = new EncuestadoServicio();
        Response respuesta = servicio.finalizarParticipacion( this.token,14,1);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertEquals("\"inactivo\"",responseDto.get("participacion_estado").toString());

    }

    @Test
    public void getEncuestadoId() throws Exception
    {
        EncuestadoServicio servicio = new EncuestadoServicio();
        Response respuesta= servicio.getEncuestadoId(this.token,2);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertNotNull(responseDto.get("encuestado"));
    }
    @After
    public void quitarToken(){
        ucab.dsw.accesodatos.DaoUsuario dao=new ucab.dsw.accesodatos.DaoUsuario();
        Usuario usuario=dao.find((long) 1,Usuario.class);
        usuario.set_token(null);
        Usuario resul= dao.update(usuario);
    }
}