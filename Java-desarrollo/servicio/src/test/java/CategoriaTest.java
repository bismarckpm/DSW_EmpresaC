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
    public void AddCategoria() throws Exception
    {
        ucab.dsw.servicio.CategoriaServicio servicio = new ucab.dsw.servicio.CategoriaServicio();
        CategoriaDto categoriaDto=new CategoriaDto();
        categoriaDto.setNombre("Telefonos");
        Response respuesta= servicio.addCategoria(categoriaDto);
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());
    }
}
