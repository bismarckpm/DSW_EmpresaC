package ucab.dsw.test;

import org.junit.Assert;
import org.junit.Test;
import ucab.dsw.dtos.CategoriaDto;
import ucab.dsw.dtos.SubcategoriaDto;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;

public class SubcategoriasServicioTest {
    @Test
    public void getSubcategorias() throws Exception
    {
        ucab.dsw.servicio.SubcategoriaServicio servicio = new ucab.dsw.servicio.SubcategoriaServicio();
        Response respuesta= servicio.getAllSubcategorias();
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("subcategorias"));

    }

    @Test
    public void getSubcategoria() throws Exception
    {
        ucab.dsw.servicio.SubcategoriaServicio servicio = new ucab.dsw.servicio.SubcategoriaServicio();
        Response respuesta= servicio.getSubcategoria(1);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("subcategoria"));
    }

    @Test
    public void AddSubcategoria() throws Exception
    {
        ucab.dsw.servicio.SubcategoriaServicio servicio = new ucab.dsw.servicio.SubcategoriaServicio();
        SubcategoriaDto subcategoriaDto=new SubcategoriaDto();
        subcategoriaDto.setNombre("Nueva-21");
        CategoriaDto categoriaDto=new CategoriaDto(1);
        subcategoriaDto.setCategoriaDto(categoriaDto);
        Response respuesta= servicio.addSubcategoria(subcategoriaDto);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotEquals(0,responseDto.get("subcategoria_id"));
    }

    @Test
    public void getSubcategoriasByCategoriaId() throws Exception
    {
        ucab.dsw.servicio.SubcategoriaServicio servicio = new ucab.dsw.servicio.SubcategoriaServicio();
        Response respuesta= servicio.getSubcategoriasByCategoriaId(1);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("subcategoriasByCategoria"));
    }

    @Test
    public void editSubcategoria() throws Exception {
        ucab.dsw.servicio.SubcategoriaServicio servicio = new ucab.dsw.servicio.SubcategoriaServicio();
        SubcategoriaDto subcategoriaDto=new SubcategoriaDto();
        CategoriaDto categoriaDto=new CategoriaDto(3);
        subcategoriaDto.setNombre("Probando-Subcategoria");
        subcategoriaDto.setCategoriaDto(categoriaDto);
        Response respuesta= servicio.editSubcategoria(1,subcategoriaDto);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertEquals("\"Probando-Subcategoria\"",responseDto.get("nombre_subcategoria").toString());
    }

    @Test
    public void deleteSubcategoria() throws Exception {
        ucab.dsw.servicio.SubcategoriaServicio servicio = new ucab.dsw.servicio.SubcategoriaServicio();
        Response respuesta= servicio.deleteSubcategoria(2);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertEquals("\"inactivo\"",responseDto.get("estado_subcategoria").toString());
    }
    @Test
    public void activarSubcategoria() throws Exception {
        ucab.dsw.servicio.SubcategoriaServicio servicio = new ucab.dsw.servicio.SubcategoriaServicio();
        Response respuesta= servicio.activarSubcategoria(2);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertEquals("\"activo\"",responseDto.get("estado_subcategoria").toString());
    }
}
