import org.junit.Assert;
import org.junit.Test;
import ucab.dsw.dtos.PresentacionDto;
import ucab.dsw.dtos.TipoDto;
import ucab.dsw.servicio.PresentacionServicio;

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
        Assert.assertNotEquals( resultado, 1 );

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
        Assert.assertNotEquals( resultado, 0 );

    }

    @Test
    public void EliminarPresentacion() throws Exception
    {
        PresentacionServicio servicio = new PresentacionServicio();;

        Response resultado = servicio.EliminarPresentacion( 1 );
        Assert.assertNotEquals( resultado, 0 );

    }
    @Test
    public void ActivarPresentacion() throws Exception
    {
        PresentacionServicio servicio = new PresentacionServicio();;

        Response resultado = servicio.ActivarPresentacion( 2 );
        Assert.assertNotEquals( resultado, 0 );

    }

    @Test
    public void findPresentacionTest() throws Exception
    {
        PresentacionServicio servicio = new PresentacionServicio();;
        Response resultado= servicio.findPresentacion(2);
        Assert.assertNotEquals( resultado, 0 );


    }
    @Test
    public void findAllPresentacionTest() throws Exception
    {
        PresentacionServicio servicio = new PresentacionServicio();;
        Response resultado= servicio.findAllPresentacion();
        Assert.assertNotEquals( resultado, 0 );


    }
}
