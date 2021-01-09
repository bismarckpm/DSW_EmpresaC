import org.junit.Assert;
import org.junit.Test;
import ucab.dsw.dtos.UsuarioDto;
import ucab.dsw.servicio.PruebaServicio;


public class PruebaServicioTest
{

   @Test
    public void addUserTest() throws Exception
    {
        PruebaServicio servicio = new PruebaServicio();
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setUsuario( "iertrsg" );
        usuarioDto.setEstado( "Activo" );
        usuarioDto.setRol( "admin" );
        UsuarioDto resultado = servicio.addUser( usuarioDto );
        Assert.assertNotEquals( resultado.getId(), 0 );
        System.out.println("HOLA");

    }
}
