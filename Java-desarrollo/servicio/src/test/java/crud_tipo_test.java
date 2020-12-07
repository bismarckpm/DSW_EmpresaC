import org.junit.Assert;
import org.junit.Test;
import ucab.dsw.dtos.PresentacionDto;
import ucab.dsw.dtos.PresentacionDto;
import ucab.dsw.dtos.SolicituEstudioDto;
import ucab.dsw.dtos.TipoDto;
import ucab.dsw.entidades.Presentacion;

import javax.ws.rs.core.Response;


public class crud_tipo_test
{

    @Test
    public void addtipoTest() throws Exception
    {
        ucab.dsw.servicio.crud_tipo servicio = new ucab.dsw.servicio.crud_tipo();
        TipoDto tipoDto = new TipoDto();
        tipoDto.setNombre( "spray" );

        Response resultado = servicio.addTipo( tipoDto);
        Assert.assertNotEquals( resultado, 1 );

    }

    @Test
    public void changePresentacionTest() throws Exception
    {
        ucab.dsw.servicio.crud_tipo servicio = new ucab.dsw.servicio.crud_tipo();
        TipoDto tipoDto = new TipoDto();
        tipoDto.setNombre( "spray" );


        Response resultado = servicio.changeTipo( 1,tipoDto);
        Assert.assertNotEquals( resultado, 0 );

    }

    @Test
    public void EliminarTpoTest() throws Exception
    {
        ucab.dsw.servicio.crud_tipo servicio = new ucab.dsw.servicio.crud_tipo();;

        Response resultado = servicio.EliminarTipo( 1 );
        Assert.assertNotEquals( resultado, 0 );

    }

    @Test
    public void findTipoTest() throws Exception
    {
        ucab.dsw.servicio.crud_tipo servicio = new ucab.dsw.servicio.crud_tipo();
        Response resultado= servicio.findTipo(2);
        Assert.assertNotEquals( resultado, 0 );


    }
    @Test
    public void findAllPresentacionTest() throws Exception
    {
        ucab.dsw.servicio.crud_tipo servicio = new ucab.dsw.servicio.crud_tipo();
        Response resultado= servicio.findAllTipo();
        Assert.assertNotEquals( resultado, 0 );


    }
}
