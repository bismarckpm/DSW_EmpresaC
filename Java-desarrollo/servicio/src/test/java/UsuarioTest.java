import org.junit.Assert;
import org.junit.Test;
import ucab.dsw.dtos.*;

import javax.ws.rs.core.Response;

public class UsuarioTest {

    @Test
    public void AddUserCliente(){
        ucab.dsw.servicio.UsuarioServicio servicio = new ucab.dsw.servicio.UsuarioServicio();


        UsuarioDto usuarioDto=new UsuarioDto();
        usuarioDto.setUsuario("usuario32");
        usuarioDto.setContrasena("12345");

        ClienteDto clienteDto= new ClienteDto();
        clienteDto.setRif("1aaaa2aaa");
        clienteDto.setRazon_social("razon social x");
        clienteDto.setNombre_empresa("Polar");
        clienteDto.setUsuarioDto(usuarioDto);

        Response respuesta= servicio.AddCliente(clienteDto);
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());

    }

    @Test
    public void AddUserEncuestado() throws Exception {
        ucab.dsw.servicio.UsuarioServicio servicio = new ucab.dsw.servicio.UsuarioServicio();


        UsuarioDto usuarioDto=new UsuarioDto();
        usuarioDto.setUsuario("usuario167");
        usuarioDto.setContrasena("12345");

        EncuestadoDto encuestadoDto=new EncuestadoDto();
        encuestadoDto.setCorreo("Juan123@gmail.com");
        encuestadoDto.setDoc_id(123456);
        encuestadoDto.setNombre("Juanito");
        encuestadoDto.setApellido("Alimaña");
        encuestadoDto.setFecha_nacimiento("2020-11-25");
        encuestadoDto.setCant_personas_vivienda(4);
        encuestadoDto.setGenero("M");


        Nivel_AcademicoDto nivel_academicoDto=new Nivel_AcademicoDto(1);
        ParroquiaDto parroquiaDto=new ParroquiaDto(1);

        encuestadoDto.setUsuarioDto(usuarioDto);
        encuestadoDto.setParroquiaDto(parroquiaDto);
        encuestadoDto.setNivel_AcademicoDto(nivel_academicoDto);


        Response respuesta= servicio.AddEncuestado(encuestadoDto);
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());

    }
}
