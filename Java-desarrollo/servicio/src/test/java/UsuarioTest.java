import org.junit.Assert;
import org.junit.Test;
import ucab.dsw.dtos.UsuarioDto;
import ucab.dsw.dtos.ClienteDto;

import javax.ws.rs.core.Response;

public class UsuarioTest {

    @Test
    public void AddUserCliente(){
        ucab.dsw.servicio.UsuarioServicio servicio = new ucab.dsw.servicio.UsuarioServicio();


        UsuarioDto usuarioDto=new UsuarioDto();
        usuarioDto.setUsuario("correo@gmail.com");
        usuarioDto.setContrasena("12345");

        ClienteDto clienteDto= new ClienteDto();
        clienteDto.setRif("1aaaa2aaa");
        clienteDto.setRazon_social("razon social x");
        clienteDto.setNombre_empresa("Polar");
        clienteDto.setUsuarioDto(usuarioDto);

        Response respuesta= servicio.AddCliente(clienteDto);
        Assert.assertEquals(respuesta.getStatus(),Response.Status.OK.getStatusCode());


    }
}
