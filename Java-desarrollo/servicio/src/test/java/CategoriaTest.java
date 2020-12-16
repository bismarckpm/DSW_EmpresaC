import org.junit.Assert;
import org.junit.Test;
import ucab.dsw.dtos.CategoriaDto;

import javax.ws.rs.core.Response;


public class CategoriaTest {

    @Test
    public void getCategorias() throws Exception
    {
        ucab.dsw.servicio.CategoriaServicio servicio = new ucab.dsw.servicio.CategoriaServicio();
        Response respuesta= servicio.getAllCategorias();
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());

    }

    @Test
    public void getCategoria() throws Exception
    {
        ucab.dsw.servicio.CategoriaServicio servicio = new ucab.dsw.servicio.CategoriaServicio();
        Response respuesta= servicio.getCategoria(1);
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());
    }


    @Test
    public void AddCategoria()
    {
        ucab.dsw.servicio.CategoriaServicio servicio = new ucab.dsw.servicio.CategoriaServicio();
        CategoriaDto categoriaDto=new CategoriaDto();
        categoriaDto.setNombre("Nadasd");
        Response respuesta= servicio.addCategoria(categoriaDto);
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());
    }

    @Test
    public void editCategoria() throws Exception {
        ucab.dsw.servicio.CategoriaServicio servicio = new ucab.dsw.servicio.CategoriaServicio();
        CategoriaDto categoriaDto=new CategoriaDto();
        categoriaDto.setNombre("Navidad");
        Response respuesta= servicio.editCategoria(36,categoriaDto);
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());
    }

    @Test
    public void deleteCategoria() throws Exception {
        ucab.dsw.servicio.CategoriaServicio servicio = new ucab.dsw.servicio.CategoriaServicio();
        CategoriaDto categoriaDto=new CategoriaDto();
        Response respuesta= servicio.deleteCategoria(1);
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());
    }

    @Test
    public void activarCategoria() throws Exception {
        ucab.dsw.servicio.CategoriaServicio servicio = new ucab.dsw.servicio.CategoriaServicio();
        CategoriaDto categoriaDto=new CategoriaDto();
        Response respuesta= servicio.activarCategoria(1);
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());
    }


}
