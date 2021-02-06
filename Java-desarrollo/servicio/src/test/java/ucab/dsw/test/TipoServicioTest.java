package ucab.dsw.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ucab.dsw.dtos.TipoDto;
import ucab.dsw.entidades.Usuario;
import ucab.dsw.jwt.Jwt;
import ucab.dsw.servicio.TipoServicio;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;


public class TipoServicioTest
{
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
    public void addtipoTest() throws Exception
    {
        TipoServicio servicio = new TipoServicio();
        TipoDto tipoDto = new TipoDto();
        tipoDto.setNombre( "Polvo" );

        Response resultado = servicio.addTipo(this.token, tipoDto);
        JsonObject responseDto= (JsonObject) resultado.getEntity();
        Assert.assertNotEquals(0,responseDto.get("tipo_id"));

    }

    @Test
    public void changeTipoTest() throws Exception
    {
        TipoServicio servicio = new TipoServicio();
        TipoDto tipoDto = new TipoDto();
        tipoDto.setNombre( "Polvo" );



        Response resultado = servicio.changeTipo( this.token,1,tipoDto);
        JsonObject responseDto= (JsonObject) resultado.getEntity();
        Assert.assertEquals("\"Polvo\"",responseDto.get("nombre_tipo").toString());

    }

    @Test
    public void eliminarTpoTest() throws Exception
    {
        TipoServicio servicio = new TipoServicio();;

        Response resultado = servicio.EliminarTipo( this.token,1);
        JsonObject responseDto= (JsonObject) resultado.getEntity();
        Assert.assertEquals("\"inactivo\"",responseDto.get("estado_tipo").toString());

    }

    @Test
    public void activarTipoTest() throws Exception
    {
        TipoServicio servicio = new TipoServicio();;

        Response resultado = servicio.ActivarTipo( this.token,1);
        JsonObject responseDto= (JsonObject) resultado.getEntity();
        Assert.assertEquals("\"activo\"",responseDto.get("estado_tipo").toString());

    }
    @Test
    public void findTipoTest() throws Exception
    {
        TipoServicio servicio = new TipoServicio();
        Response resultado= servicio.findTipo(this.token,2);
        JsonObject responseDto= (JsonObject) resultado.getEntity();
        Assert.assertNotNull(responseDto.get("categoria"));


    }
    @Test
    public void findAllTipoTest() throws Exception
    {
        TipoServicio servicio = new TipoServicio();
        Response resultado= servicio.findAllTipo(this.token);
        JsonObject responseDto= (JsonObject) resultado.getEntity();
        Assert.assertNotNull(responseDto.get("tipos"));


    }
}
