import org.junit.Assert;
import org.junit.Test;
import ucab.dsw.dtos.MarcaDto;
import ucab.dsw.dtos.Marca_TipoDto;
import ucab.dsw.dtos.SubcategoriaDto;
import ucab.dsw.dtos.TipoDto;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class MarcaTest {
    @Test
    public void getMarcas() throws Exception
    {
        ucab.dsw.servicio.MarcaServicio servicio = new ucab.dsw.servicio.MarcaServicio();
        Response respuesta= servicio.getAllMarcas();
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());

    }

    @Test
    public void getMarca() throws Exception
    {
        ucab.dsw.servicio.MarcaServicio servicio = new ucab.dsw.servicio.MarcaServicio();
        Response respuesta= servicio.getMarca(1);
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());
    }

    @Test
    public void AddMarca() throws Exception
    {
        ucab.dsw.servicio.MarcaServicio servicio = new ucab.dsw.servicio.MarcaServicio();
        MarcaDto marcaDto=new MarcaDto();;
        List<TipoDto> tiposDto= new ArrayList<>();
        TipoDto tipoDto=new TipoDto(8);

        SubcategoriaDto subcategoriaDto=new SubcategoriaDto(4);

        tiposDto.add(tipoDto);

        marcaDto.setNombre("sfaoi");
        marcaDto.setSubcategoriaDto(subcategoriaDto);
        marcaDto.setTipo_Dto(tiposDto);

        Response respuesta= servicio.addMarca(marcaDto);
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());
    }

    @Test
    public void getMarcasBySubcategoriaId() throws Exception
    {
        ucab.dsw.servicio.MarcaServicio servicio = new ucab.dsw.servicio.MarcaServicio();
        Response respuesta= servicio.getMarcaBySubcategoriaId(4);
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());
    }

    @Test
    public void eliminarMarcaTest() throws Exception
    {
        ucab.dsw.servicio.MarcaServicio servicio = new ucab.dsw.servicio.MarcaServicio();

        Response resultado = servicio.deleteMarca( 1);
        Assert.assertNotEquals( resultado, 0 );

    }
    @Test
    public void activarMarcaTest() throws Exception
    {
        ucab.dsw.servicio.MarcaServicio servicio = new ucab.dsw.servicio.MarcaServicio();

        Response resultado = servicio.activarMarca( 1);
        Assert.assertNotEquals( resultado, 0 );

    }
}
