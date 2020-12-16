import org.junit.Assert;
import org.junit.Test;
import ucab.dsw.dtos.*;

import javax.ws.rs.core.Response;

public class UsuarioTest {

    @Test
    public void AddUserCliente(){
        ucab.dsw.servicio.UsuarioServicio servicio = new ucab.dsw.servicio.UsuarioServicio();


        UsuarioDto usuarioDto=new UsuarioDto();
        usuarioDto.setUsuario("usdsadas");
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
    public void activarUsuarioTest() throws Exception
    {
        ucab.dsw.servicio.UsuarioServicio servicio = new ucab.dsw.servicio.UsuarioServicio();

        Response resultado = servicio.activarUsuario( 3);
        Assert.assertNotEquals( resultado, 0 );

    }
}
