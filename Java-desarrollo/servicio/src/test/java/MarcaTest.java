import org.junit.Assert;
import org.junit.Test;
import ucab.dsw.dtos.MarcaDto;
import ucab.dsw.dtos.SubcategoriaDto;

import javax.ws.rs.core.Response;

public class MarcaTest {
    /*@Test
    public void getMarcas() throws Exception
    {
        ucab.dsw.servicio.MarcaServicio servicio = new ucab.dsw.servicio.MarcaServicio();
        Response respuesta= servicio.getAllMarcas();
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());

    }*/

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
        MarcaDto marcaDto=new MarcaDto();
        SubcategoriaDto subcategoriaDto=new SubcategoriaDto(4);

        marcaDto.setNombre("Juana");
        marcaDto.setSubcategoriaDto(subcategoriaDto);

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
}
