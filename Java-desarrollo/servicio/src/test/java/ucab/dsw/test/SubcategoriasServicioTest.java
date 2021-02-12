package ucab.dsw.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ucab.dsw.dtos.CategoriaDto;
import ucab.dsw.dtos.SubcategoriaDto;
import ucab.dsw.entidades.Usuario;
import ucab.dsw.jwt.Jwt;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;

public class SubcategoriasServicioTest {

    public String token;

    @Before
    public void colocarToken(){
        ucab.dsw.accesodatos.DaoUsuario dao=new ucab.dsw.accesodatos.DaoUsuario();
        Usuario usuario=dao.find((long) 1,Usuario.class);
        this.token= Jwt.generarToken(1);
        usuario.set_token(this.token);
        Usuario resul= dao.update(usuario);
    }

    @Test
    public void getSubcategorias() throws Exception
    {
        ucab.dsw.servicio.SubcategoriaServicio servicio = new ucab.dsw.servicio.SubcategoriaServicio();
        Response respuesta= servicio.getAllSubcategorias();
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertNotNull(responseDto.get("subcategorias"));

    }

    @Test
    public void getSubcategoria() throws Exception
    {
        ucab.dsw.servicio.SubcategoriaServicio servicio = new ucab.dsw.servicio.SubcategoriaServicio();
        Response respuesta= servicio.getSubcategoria(this.token,1);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertNotNull(responseDto.get("subcategoria"));
    }

    @Test
    public void AddSubcategoria() throws Exception
    {
        ucab.dsw.servicio.SubcategoriaServicio servicio = new ucab.dsw.servicio.SubcategoriaServicio();
        SubcategoriaDto subcategoriaDto=new SubcategoriaDto();
        subcategoriaDto.setNombre("Nueva-22");
        CategoriaDto categoriaDto=new CategoriaDto(1);
        subcategoriaDto.setCategoriaDto(categoriaDto);
        Response respuesta= servicio.addSubcategoria(this.token,subcategoriaDto);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertNotEquals(0,responseDto.get("subcategoria_id"));
    }

    @Test
    public void getSubcategoriasByCategoriaId() throws Exception
    {
        ucab.dsw.servicio.SubcategoriaServicio servicio = new ucab.dsw.servicio.SubcategoriaServicio();
        Response respuesta= servicio.getSubcategoriasByCategoriaId(this.token,1);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertNotNull(responseDto.get("subcategoriasByCategoria"));
    }

    @Test
    public void editSubcategoria() throws Exception {
        ucab.dsw.servicio.SubcategoriaServicio servicio = new ucab.dsw.servicio.SubcategoriaServicio();
        SubcategoriaDto subcategoriaDto=new SubcategoriaDto();
        CategoriaDto categoriaDto=new CategoriaDto(3);
        subcategoriaDto.setNombre("Probando-Subcategoria");
        subcategoriaDto.setCategoriaDto(categoriaDto);
        Response respuesta= servicio.editSubcategoria(this.token,1,subcategoriaDto);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);

        if(responseDto.get("nombre_subcategoria")!=null) {
            Assert.assertEquals("\"Probando-Subcategoria\"", responseDto.get("nombre_subcategoria").toString());
        }
    }

    @Test
    public void deleteSubcategoria() throws Exception {
        ucab.dsw.servicio.SubcategoriaServicio servicio = new ucab.dsw.servicio.SubcategoriaServicio();
        Response respuesta= servicio.deleteSubcategoria(this.token,2);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertEquals("\"inactivo\"",responseDto.get("estado_subcategoria").toString());
    }
    @Test
    public void activarSubcategoria() throws Exception {
        ucab.dsw.servicio.SubcategoriaServicio servicio = new ucab.dsw.servicio.SubcategoriaServicio();
        Response respuesta= servicio.activarSubcategoria(this.token,2);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertEquals("\"activo\"",responseDto.get("estado_subcategoria").toString());
    }

    @After
    public void quitarToken(){
        ucab.dsw.accesodatos.DaoUsuario dao=new ucab.dsw.accesodatos.DaoUsuario();
        Usuario usuario=dao.find((long) 1,Usuario.class);
        usuario.set_token(null);
        Usuario resul= dao.update(usuario);
    }
}
