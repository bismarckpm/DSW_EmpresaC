package ucab.dsw.test;

import org.junit.Assert;
import org.junit.Test;
import ucab.dsw.dtos.CategoriaDto;
import ucab.dsw.dtos.SubcategoriaDto;

import javax.ws.rs.core.Response;

public class SubcategoriasServicioTest {
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
        subcategoriaDto.setNombre("Kali");
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

    @Test
    public void editSubcategoria() throws Exception {
        ucab.dsw.servicio.SubcategoriaServicio servicio = new ucab.dsw.servicio.SubcategoriaServicio();
        SubcategoriaDto subcategoriaDto=new SubcategoriaDto();
        CategoriaDto categoriaDto=new CategoriaDto(1);
        subcategoriaDto.setNombre("Probando");
        subcategoriaDto.setCategoriaDto(categoriaDto);
        Response respuesta= servicio.editSubcategoria(5,subcategoriaDto);
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());
    }

    @Test
    public void deleteSubcategoria() throws Exception {
        ucab.dsw.servicio.SubcategoriaServicio servicio = new ucab.dsw.servicio.SubcategoriaServicio();
        CategoriaDto categoriaDto=new CategoriaDto();
        Response respuesta= servicio.deleteSubcategoria(2);
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());
    }
    @Test
    public void activarSubcategoria() throws Exception {
        ucab.dsw.servicio.SubcategoriaServicio servicio = new ucab.dsw.servicio.SubcategoriaServicio();
        CategoriaDto categoriaDto=new CategoriaDto();
        Response respuesta= servicio.activarSubcategoria(2);
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());
    }
}
