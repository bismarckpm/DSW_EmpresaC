package ucab.dsw.test;


import org.junit.Assert;
import org.junit.Test;
import ucab.dsw.dtos.CategoriaDto;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;



public class CategoriaServicioTest {

    @Test
    public void getCategorias()
    {
        ucab.dsw.servicio.CategoriaServicio servicio = new ucab.dsw.servicio.CategoriaServicio();
        Response respuesta= servicio.getAllCategorias();
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("categorias"));

    }

    @Test
    public void getCategoria()
    {
        ucab.dsw.servicio.CategoriaServicio servicio = new ucab.dsw.servicio.CategoriaServicio();
        Response respuesta= servicio.getCategoria(1);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("categoria"));
    }


    @Test
    public void AddCategoria()
    {
        ucab.dsw.servicio.CategoriaServicio servicio = new ucab.dsw.servicio.CategoriaServicio();
        CategoriaDto categoriaDto=new CategoriaDto();
        categoriaDto.setNombre("Categoria nueva");
        Response respuesta= servicio.addCategoria(categoriaDto);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotEquals(0,responseDto.get("categoria_id"));
    }

    @Test
    public void editCategoria()  {
        ucab.dsw.servicio.CategoriaServicio servicio = new ucab.dsw.servicio.CategoriaServicio();
        CategoriaDto categoriaDto=new CategoriaDto();
        categoriaDto.setNombre("Navidad");
        Response respuesta= servicio.editCategoria(1,categoriaDto);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertEquals("\"Navidad\"",responseDto.get("categoria_nombre").toString());

    }

    @Test
    public void deleteCategoria() {
        ucab.dsw.servicio.CategoriaServicio servicio = new ucab.dsw.servicio.CategoriaServicio();
        Response respuesta= servicio.deleteCategoria(1);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertEquals("\"inactivo\"",responseDto.get("categoria_estado").toString());
    }

    @Test
    public void activarCategoria()  {
        ucab.dsw.servicio.CategoriaServicio servicio = new ucab.dsw.servicio.CategoriaServicio();
        Response respuesta= servicio.activarCategoria(1);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertEquals("\"activo\"",responseDto.get("categoria_estado").toString());
    }
}
