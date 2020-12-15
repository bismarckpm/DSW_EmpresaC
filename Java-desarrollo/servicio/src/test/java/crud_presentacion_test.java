import org.junit.Assert;
import org.junit.Test;
import ucab.dsw.dtos.PresentacionDto;
import ucab.dsw.dtos.PresentacionDto;
import ucab.dsw.dtos.SolicitudEstudioDto;
import ucab.dsw.dtos.TipoDto;
import ucab.dsw.entidades.Presentacion;

import javax.ws.rs.core.Response;


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

        Response resultado = servicio.addPresentacion( presentacionDto);
        Assert.assertNotEquals( resultado, 1 );

    }

    @Test
    public void changePresentacionTest() throws Exception
    {
        ucab.dsw.servicio.Cruds_presntacion servicio = new ucab.dsw.servicio.Cruds_presntacion();
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
        ucab.dsw.servicio.Cruds_presntacion servicio = new ucab.dsw.servicio.Cruds_presntacion();;

        Response resultado = servicio.EliminarPresentacion( 1 );
        Assert.assertNotEquals( resultado, 0 );

    }
    @Test
    public void ActivarPresentacion() throws Exception
    {
        ucab.dsw.servicio.Cruds_presntacion servicio = new ucab.dsw.servicio.Cruds_presntacion();;

        Response resultado = servicio.ActivarPresentacion( 2 );
        Assert.assertNotEquals( resultado, 0 );

    }

    @Test
    public void findPresentacionTest() throws Exception
    {
        ucab.dsw.servicio.Cruds_presntacion servicio = new ucab.dsw.servicio.Cruds_presntacion();;
        Response resultado= servicio.findPresentacion(2);
        Assert.assertNotEquals( resultado, 0 );


    }
    @Test
    public void findAllPresentacionTest() throws Exception
    {
        ucab.dsw.servicio.Cruds_presntacion servicio = new ucab.dsw.servicio.Cruds_presntacion();;
        Response resultado= servicio.findAllPresentacion();
        Assert.assertNotEquals( resultado, 0 );


    }
}
