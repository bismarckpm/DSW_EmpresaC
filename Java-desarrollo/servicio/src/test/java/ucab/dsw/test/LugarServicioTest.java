package ucab.dsw.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ucab.dsw.entidades.Usuario;
import ucab.dsw.jwt.Jwt;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;

public class LugarServicioTest {
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
    public void getPaises() throws Exception
    {
        ucab.dsw.servicio.PaisServicio servicio = new ucab.dsw.servicio.PaisServicio();
        Response respuesta= servicio.getAllPaises();
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("paises"));
    }

   @Test
    public void getEstados() throws Exception
    {
        ucab.dsw.servicio.EstadoServicio servicio = new ucab.dsw.servicio.EstadoServicio();
        Response respuesta= servicio.getAllEstados(this.token);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("estados"));
    }

    @Test
    public void getCiudades() throws Exception
    {
        ucab.dsw.servicio.CiudadServicio servicio = new ucab.dsw.servicio.CiudadServicio();
        Response respuesta= servicio.getAllCiudades();
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("ciudades"));
    }

    @Test
    public void getPorroquias() throws Exception
    {
        ucab.dsw.servicio.ParroquiaServicio servicio = new ucab.dsw.servicio.ParroquiaServicio();
        Response respuesta= servicio.getAllParroquias();
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("parroquias"));

    }

}
