import org.junit.Assert;
import org.junit.Test;
import ucab.dsw.dtos.CategoriaDto;
import ucab.dsw.dtos.SubcategoriaDto;

import javax.ws.rs.core.Response;

public class SubcategoriasTest {
    @Test
    public void getSubcategorias() throws Exception
    {
        ucab.dsw.servicio.SubcategoriaServicio servicio = new ucab.dsw.servicio.SubcategoriaServicio();
        Response respuesta= servicio.getAllSubcategorias();
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());

    }

    @Test
    public void getSubcategoria() throws Exception
    {
        ucab.dsw.servicio.SubcategoriaServicio servicio = new ucab.dsw.servicio.SubcategoriaServicio();
        Response respuesta= servicio.getSubcategoria(1);
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());
    }

    @Test
    public void AddSubcategoria() throws Exception
    {
        ucab.dsw.servicio.SubcategoriaServicio servicio = new ucab.dsw.servicio.SubcategoriaServicio();
        SubcategoriaDto subcategoriaDto=new SubcategoriaDto();
        subcategoriaDto.setNombre("prueba");
        CategoriaDto categoriaDto=new CategoriaDto(1);
        subcategoriaDto.setCategoriaDto(categoriaDto);
        Response respuesta= servicio.addSubcategoria(subcategoriaDto);
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());
    }

    @Test
    public void getSubcategoriasByCategoriaId() throws Exception
    {
        ucab.dsw.servicio.SubcategoriaServicio servicio = new ucab.dsw.servicio.SubcategoriaServicio();
        Response respuesta= servicio.getSubcategoriasByCategoriaId(11);
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());
    }
}
