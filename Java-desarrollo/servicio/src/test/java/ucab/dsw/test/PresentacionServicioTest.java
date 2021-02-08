package ucab.dsw.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ucab.dsw.dtos.PresentacionDto;
import ucab.dsw.dtos.TipoDto;
import ucab.dsw.entidades.Usuario;
import ucab.dsw.jwt.Jwt;
import ucab.dsw.servicio.PresentacionServicio;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;


public class PresentacionServicioTest
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
    public void addPresentacionTest() throws Exception
    {
        PresentacionServicio servicio = new PresentacionServicio();
        PresentacionDto presentacionDto = new PresentacionDto();
        presentacionDto.setNombre( "100ml" );

        TipoDto tipoDto = new TipoDto( 1);

        presentacionDto.setTipoDto( tipoDto );

        Response resultado = servicio.addPresentacion(this.token, presentacionDto);

        JsonObject responseDto= (JsonObject) resultado.getEntity();
        Assert.assertNotEquals(0,responseDto.get("presentacion_id"));

    }

    @Test
    public void changePresentacionTest() throws Exception
    {
        PresentacionServicio servicio = new PresentacionServicio();
        PresentacionDto presentacionDto = new PresentacionDto();
        TipoDto tipoDto=new TipoDto(3);
        presentacionDto.setNombre( "200ml" );
        presentacionDto.setTipoDto(tipoDto);


        Response resultado = servicio.changePresentacion( this.token,5,presentacionDto);
        JsonObject responseDto= (JsonObject) resultado.getEntity();
        Assert.assertEquals("\"200ml\"",responseDto.get("nombre_presentacion").toString());

    }

    @Test
    public void EliminarPresentacion() throws Exception
    {
        PresentacionServicio servicio = new PresentacionServicio();;

        Response resultado = servicio.EliminarPresentacion(this.token, 1 );
        JsonObject responseDto= (JsonObject) resultado.getEntity();
        Assert.assertEquals("\"inactivo\"",responseDto.get("estado_presentacion").toString());

    }
    @Test
    public void ActivarPresentacion() throws Exception
    {
        PresentacionServicio servicio = new PresentacionServicio();;

        Response resultado = servicio.ActivarPresentacion(this.token, 2 );
        JsonObject responseDto= (JsonObject) resultado.getEntity();
        Assert.assertEquals("\"activo\"",responseDto.get("estado_presentacion").toString());

    }

    @Test
    public void findPresentacionTest() throws Exception
    {
        PresentacionServicio servicio = new PresentacionServicio();;
        Response resultado= servicio.findPresentacion(this.token,2);
        JsonObject responseDto= (JsonObject) resultado.getEntity();
        Assert.assertNotNull(responseDto.get("categoria"));


    }
    @Test
    public void findAllPresentacionTest() throws Exception
    {
        PresentacionServicio servicio = new PresentacionServicio();;
        Response resultado= servicio.findAllPresentacion(this.token);
        JsonObject responseDto= (JsonObject) resultado.getEntity();
        Assert.assertNotNull(responseDto.get("presentaciones"));


    }
}
