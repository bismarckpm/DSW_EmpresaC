import org.junit.Assert;
import org.junit.Test;
import ucab.dsw.dtos.PresentacionDto;
import ucab.dsw.dtos.PresentacionDto;
import ucab.dsw.dtos.SolicitudEstudioDto;
import ucab.dsw.dtos.TipoDto;
import ucab.dsw.entidades.Presentacion;


public class crud_tipo_test
{

    @Test
    public void addtipoTest() throws Exception
    {
        ucab.dsw.servicio.crud_tipo servicio = new ucab.dsw.servicio.crud_tipo();
        TipoDto tipoDto = new TipoDto();
        tipoDto.setNombre( "spray" );

        TipoDto resultado = servicio.addTipo( tipoDto);
        Assert.assertNotEquals( resultado.getId(), 1 );

    }

    @Test
    public void changePresentacionTest() throws Exception
    {
        ucab.dsw.servicio.crud_tipo servicio = new ucab.dsw.servicio.crud_tipo();
        TipoDto tipoDto = new TipoDto();
        tipoDto.setNombre( "spray" );


        TipoDto resultado = servicio.changeTipo( 1,tipoDto);
        Assert.assertNotEquals( resultado.getId(), 0 );

    }

    @Test
    public void EliminarTpoTest() throws Exception
    {
        ucab.dsw.servicio.crud_tipo servicio = new ucab.dsw.servicio.crud_tipo();;

        TipoDto resultado = servicio.EliminarTipo( 1 );
        Assert.assertNotEquals( resultado.getId(), 0 );

    }

    @Test
    public void findTipoTest() throws Exception
    {
        ucab.dsw.servicio.crud_tipo servicio = new ucab.dsw.servicio.crud_tipo();
        int resultado= servicio.findTipo(2);
        Assert.assertNotEquals( resultado, 0 );


    }
    @Test
    public void findAllPresentacionTest() throws Exception
    {
        ucab.dsw.servicio.crud_tipo servicio = new ucab.dsw.servicio.crud_tipo();
        int resultado= servicio.findAllTipo();
        Assert.assertNotEquals( resultado, 0 );


    }
}
