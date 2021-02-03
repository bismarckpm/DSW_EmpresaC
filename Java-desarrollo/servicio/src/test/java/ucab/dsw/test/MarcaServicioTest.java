package ucab.dsw.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ucab.dsw.dtos.*;
import ucab.dsw.entidades.Usuario;
import ucab.dsw.jwt.Jwt;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class MarcaServicioTest {

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
    public void getMarcas() throws Exception
    {
        ucab.dsw.servicio.MarcaServicio servicio = new ucab.dsw.servicio.MarcaServicio();
        Response respuesta= servicio.getAllMarcas(this.token);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertNotNull(responseDto.get("marcas"));

    }

    @Test
    public void getMarca() throws Exception
    {
        ucab.dsw.servicio.MarcaServicio servicio = new ucab.dsw.servicio.MarcaServicio();
        Response respuesta= servicio.getMarca(this.token,1);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertNotNull(responseDto.get("marca"));
    }

    @Test
    public void AddMarca() throws Exception
    {
        ucab.dsw.servicio.MarcaServicio servicio = new ucab.dsw.servicio.MarcaServicio();
        MarcaDto marcaDto=new MarcaDto();;
        List<TipoDto> tiposDto= new ArrayList<>();
        TipoDto tipoDto=new TipoDto(1);

        SubcategoriaDto subcategoriaDto=new SubcategoriaDto(1);

        tiposDto.add(tipoDto);

        marcaDto.setNombre("sfaoi");
        marcaDto.setSubcategoriaDto(subcategoriaDto);
        marcaDto.setTipo_Dto(tiposDto);

        Response respuesta= servicio.addMarca(this.token,marcaDto);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertNotEquals(0,responseDto.get("marca_id"));
    }

    @Test
    public void getMarcasBySubcategoriaId() throws Exception
    {
        ucab.dsw.servicio.MarcaServicio servicio = new ucab.dsw.servicio.MarcaServicio();
        Response respuesta= servicio.getMarcaBySubcategoriaId(this.token,1);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertNotNull(responseDto.get("marcasBySubcategoria"));
    }

    @Test
    public void eliminarMarcaTest() throws Exception
    {
        ucab.dsw.servicio.MarcaServicio servicio = new ucab.dsw.servicio.MarcaServicio();

        Response respuesta = servicio.deleteMarca( this.token,1);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertEquals("\"inactivo\"",responseDto.get("estado_marca").toString());

    }
    @Test
    public void activarMarcaTest() throws Exception
    {
        ucab.dsw.servicio.MarcaServicio servicio = new ucab.dsw.servicio.MarcaServicio();

        Response respuesta = servicio.activarMarca( this.token,1);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        System.out.println(responseDto);
        Assert.assertEquals("\"activo\"",responseDto.get("estado_marca").toString());

    }

    @After
    public void quitarToken(){
        ucab.dsw.accesodatos.DaoUsuario dao=new ucab.dsw.accesodatos.DaoUsuario();
        Usuario usuario=dao.find((long) 1,Usuario.class);
        usuario.set_token(null);
        Usuario resul= dao.update(usuario);
    }
}
