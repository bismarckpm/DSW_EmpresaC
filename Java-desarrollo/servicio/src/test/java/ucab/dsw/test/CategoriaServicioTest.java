package ucab.dsw.test;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ucab.dsw.dtos.CategoriaDto;
import ucab.dsw.entidades.Usuario;
import ucab.dsw.jwt.Jwt;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;



public class CategoriaServicioTest {

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
    public void getCategorias()
    {
        ucab.dsw.servicio.CategoriaServicio servicio = new ucab.dsw.servicio.CategoriaServicio();
        Response respuesta= servicio.getAllCategorias(this.token);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertNotNull(responseDto.get("categorias"));

    }

    @Test
    public void getCategoria()
    {
        ucab.dsw.servicio.CategoriaServicio servicio = new ucab.dsw.servicio.CategoriaServicio();
        Response respuesta= servicio.getCategoria(this.token,1);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertNotNull(responseDto.get("categoria"));
    }


    @Test
    public void AddCategoria()
    {
        ucab.dsw.servicio.CategoriaServicio servicio = new ucab.dsw.servicio.CategoriaServicio();
        CategoriaDto categoriaDto=new CategoriaDto();
        categoriaDto.setNombre("Categoria nueva");
        Response respuesta= servicio.addCategoria(this.token,categoriaDto);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertNotEquals(0,responseDto.get("categoria_id"));
    }

    @Test
    public void editCategoria()  {
        ucab.dsw.servicio.CategoriaServicio servicio = new ucab.dsw.servicio.CategoriaServicio();
        CategoriaDto categoriaDto=new CategoriaDto();
        categoriaDto.setNombre("Navidad");
        Response respuesta= servicio.editCategoria(this.token,1,categoriaDto);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        if(responseDto.get("categoria_nombre")!=null){
            Assert.assertEquals("\"Navidad\"",responseDto.get("categoria_nombre").toString());
        }


    }

    @Test
    public void deleteCategoria() {
        ucab.dsw.servicio.CategoriaServicio servicio = new ucab.dsw.servicio.CategoriaServicio();
        Response respuesta= servicio.deleteCategoria(this.token,1);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertEquals("\"inactivo\"",responseDto.get("categoria_estado").toString());
    }

    @Test
    public void activarCategoria()  {
        ucab.dsw.servicio.CategoriaServicio servicio = new ucab.dsw.servicio.CategoriaServicio();
        Response respuesta= servicio.activarCategoria(this.token,1);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertEquals("\"activo\"",responseDto.get("categoria_estado").toString());
    }

    @After
    public void quitarToken(){
        ucab.dsw.accesodatos.DaoUsuario dao=new ucab.dsw.accesodatos.DaoUsuario();
        Usuario usuario=dao.find((long) 1,Usuario.class);
        usuario.set_token(null);
        Usuario resul= dao.update(usuario);
    }
}
