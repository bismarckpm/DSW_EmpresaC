package ucab.dsw.test;

import org.junit.Assert;
import org.junit.Test;
import ucab.dsw.dtos.TipoDto;
import ucab.dsw.servicio.TipoServicio;

import javax.ws.rs.core.Response;


public class TipoServicioTest
{

    @Test
    public void addtipoTest() throws Exception
    {
        TipoServicio servicio = new TipoServicio();
        TipoDto tipoDto = new TipoDto();
        tipoDto.setNombre( "Polvo" );

        Response resultado = servicio.addTipo( tipoDto);
        Assert.assertNotEquals( resultado, 1 );

    }

    @Test
    public void changeTipoTest() throws Exception
    {
        TipoServicio servicio = new TipoServicio();
        TipoDto tipoDto = new TipoDto();
        tipoDto.setNombre( "Polvo" );



        Response resultado = servicio.changeTipo( 1,tipoDto);
        Assert.assertNotEquals( resultado, 0 );

    }

    @Test
    public void eliminarTpoTest() throws Exception
    {
        TipoServicio servicio = new TipoServicio();;

        Response resultado = servicio.EliminarTipo( 1);
        Assert.assertNotEquals( resultado, 0 );

    }

    @Test
    public void activarTipoTest() throws Exception
    {
        TipoServicio servicio = new TipoServicio();;

        Response resultado = servicio.ActivarTipo( 1);
        Assert.assertNotEquals( resultado, 0 );

    }
    @Test
    public void findTipoTest() throws Exception
    {
        TipoServicio servicio = new TipoServicio();
        Response resultado= servicio.findTipo(2);
        Assert.assertNotEquals( resultado, 0 );


    }
    @Test
    public void findAllTipoTest() throws Exception
    {
        TipoServicio servicio = new TipoServicio();
        Response resultado= servicio.findAllTipo();
        Assert.assertNotEquals( resultado, 0 );


    }
}
