package ucab.dsw.test;

import org.junit.Assert;
import org.junit.Test;
import ucab.dsw.dtos.*;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class MarcaServicioTest {
    @Test
    public void getMarcas() throws Exception
    {
        ucab.dsw.servicio.MarcaServicio servicio = new ucab.dsw.servicio.MarcaServicio();
        Response respuesta= servicio.getAllMarcas();
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("marcas"));

    }

    @Test
    public void getMarca() throws Exception
    {
        ucab.dsw.servicio.MarcaServicio servicio = new ucab.dsw.servicio.MarcaServicio();
        Response respuesta= servicio.getMarca(1);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
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

        Response respuesta= servicio.addMarca(marcaDto);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotEquals(0,responseDto.get("marca_id"));
    }

    @Test
    public void getMarcasBySubcategoriaId() throws Exception
    {
        ucab.dsw.servicio.MarcaServicio servicio = new ucab.dsw.servicio.MarcaServicio();
        Response respuesta= servicio.getMarcaBySubcategoriaId(1);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("marcasBySubcategoria"));
    }

    @Test
    public void eliminarMarcaTest() throws Exception
    {
        ucab.dsw.servicio.MarcaServicio servicio = new ucab.dsw.servicio.MarcaServicio();

        Response respuesta = servicio.deleteMarca( 1);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertEquals("\"inactivo\"",responseDto.get("estado_marca").toString());

    }
    @Test
    public void activarMarcaTest() throws Exception
    {
        ucab.dsw.servicio.MarcaServicio servicio = new ucab.dsw.servicio.MarcaServicio();

        Response respuesta = servicio.activarMarca( 1);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertEquals("\"activo\"",responseDto.get("estado_marca").toString());

    }

}
