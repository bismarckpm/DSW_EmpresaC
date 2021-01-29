package ucab.dsw.test;

import org.junit.Assert;
import org.junit.Test;
import ucab.dsw.dtos.PresentacionDto;
import ucab.dsw.dtos.TipoDto;
import ucab.dsw.servicio.PresentacionServicio;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;


public class PresentacionServicioTest
{

    @Test
    public void addPresentacionTest() throws Exception
    {
        PresentacionServicio servicio = new PresentacionServicio();
        PresentacionDto presentacionDto = new PresentacionDto();
        presentacionDto.setNombre( "100ml" );

        TipoDto tipoDto = new TipoDto( 1);

        presentacionDto.setTipoDto( tipoDto );

        Response resultado = servicio.addPresentacion( presentacionDto);

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


        Response resultado = servicio.changePresentacion( 5,presentacionDto);
        JsonObject responseDto= (JsonObject) resultado.getEntity();
        Assert.assertEquals("\"200ml\"",responseDto.get("nombre_presentacion").toString());

    }

    @Test
    public void EliminarPresentacion() throws Exception
    {
        PresentacionServicio servicio = new PresentacionServicio();;

        Response resultado = servicio.EliminarPresentacion( 1 );
        JsonObject responseDto= (JsonObject) resultado.getEntity();
        Assert.assertEquals("\"inactivo\"",responseDto.get("estado_presentacion").toString());

    }
    @Test
    public void ActivarPresentacion() throws Exception
    {
        PresentacionServicio servicio = new PresentacionServicio();;

        Response resultado = servicio.ActivarPresentacion( 2 );
        JsonObject responseDto= (JsonObject) resultado.getEntity();
        Assert.assertEquals("\"activo\"",responseDto.get("estado_presentacion").toString());

    }

    @Test
    public void findPresentacionTest() throws Exception
    {
        PresentacionServicio servicio = new PresentacionServicio();;
        Response resultado= servicio.findPresentacion(2);
        JsonObject responseDto= (JsonObject) resultado.getEntity();
        Assert.assertNotNull(responseDto.get("categoria"));


    }
    @Test
    public void findAllPresentacionTest() throws Exception
    {
        PresentacionServicio servicio = new PresentacionServicio();;
        Response resultado= servicio.findAllPresentacion();
        JsonObject responseDto= (JsonObject) resultado.getEntity();
        Assert.assertNotNull(responseDto.get("presentaciones"));


    }
}
