import org.junit.Assert;
import org.junit.Test;
import ucab.dsw.dtos.PresentacionDto;
import ucab.dsw.dtos.PresentacionDto;
import ucab.dsw.dtos.SolicituEstudioDto;
import ucab.dsw.dtos.TipoDto;
import ucab.dsw.entidades.Presentacion;


public class crud_presentacion_test
{

    @Test
    public void addPresentacionTest() throws Exception
    {
        ucab.dsw.servicio.Cruds_presntacion servicio = new ucab.dsw.servicio.Cruds_presntacion();
        PresentacionDto presentacionDto = new PresentacionDto();
        presentacionDto.setNombre( "100ml" );

        TipoDto tipoDto = new TipoDto( 1);

        presentacionDto.setTipoDto( tipoDto );

        PresentacionDto resultado = servicio.addPresentacion( presentacionDto);
        Assert.assertNotEquals( resultado.getId(), 1 );

    }

    @Test
    public void changePresentacionTest() throws Exception
    {
        ucab.dsw.servicio.Cruds_presntacion servicio = new ucab.dsw.servicio.Cruds_presntacion();
        PresentacionDto presentacionDto = new PresentacionDto();
        presentacionDto.setNombre( "100ml" );


        PresentacionDto resultado = servicio.changePresentacion( 1,5,presentacionDto);
        Assert.assertNotEquals( resultado.getId(), 0 );

    }

    @Test
    public void EliminarPresentacion() throws Exception
    {
        ucab.dsw.servicio.Cruds_presntacion servicio = new ucab.dsw.servicio.Cruds_presntacion();;

        PresentacionDto resultado = servicio.EliminarPresentacion( 1 );
        Assert.assertNotEquals( resultado.getId(), 0 );

    }

    @Test
    public void findPresentacionTest() throws Exception
    {
        ucab.dsw.servicio.Cruds_presntacion servicio = new ucab.dsw.servicio.Cruds_presntacion();;
        int resultado= servicio.findPresentacion(2);
        Assert.assertNotEquals( resultado, 0 );


    }
    @Test
    public void findAllPresentacionTest() throws Exception
    {
        ucab.dsw.servicio.Cruds_presntacion servicio = new ucab.dsw.servicio.Cruds_presntacion();;
        int resultado= servicio.findAllPresentacion();
        Assert.assertNotEquals( resultado, 0 );


    }
}
