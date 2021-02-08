package ucab.dsw.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ucab.dsw.entidades.Usuario;
import ucab.dsw.servicio.ClienteServicio;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import ucab.dsw.jwt.Jwt;


public class ClienteServicioTest {

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
    public void consultaEstudios_SolicitadosTest() throws Exception
    {
        ClienteServicio servicio = new ClienteServicio();
        Response respuesta= servicio.consultaEstudios_Solicitados(this.token,5);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertNotNull(responseDto.get("estudio"));
    }

   @Test
    public void getClienteId() throws Exception
    {
        ClienteServicio servicio = new ClienteServicio();
        Response respuesta= servicio.getClienteId(this.token,6);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertNotNull(responseDto.get("cliente"));
    }
    @Test
    public void respuesta_analistaTest() throws Exception
    {
        ClienteServicio servicio = new ClienteServicio();
        Response respuesta= servicio.respuesta_analista(this.token,13);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertNotNull(responseDto.get("respuesta"));
    }
    @After
    public void quitarToken(){
        ucab.dsw.accesodatos.DaoUsuario dao=new ucab.dsw.accesodatos.DaoUsuario();
        Usuario usuario=dao.find((long) 1,Usuario.class);
        usuario.set_token(null);
        Usuario resul= dao.update(usuario);
    }
}