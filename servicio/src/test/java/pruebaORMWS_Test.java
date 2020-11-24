import org.junit.Assert;
import org.junit.Test;
import ucab.dsw.dtos.UsuarioDto;


public class pruebaORMWS_Test
{

    @Test
    public void addUserTest() throws Exception
    {
        ucab.dsw.servicio.pruebaORMWS servicio = new ucab.dsw.servicio.pruebaORMWS();
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setUsuario( "Bismarck" );
        usuarioDto.setEstados( "Activo" );
        usuarioDto.setRol( "admin" );
        UsuarioDto resultado = servicio.addUser( usuarioDto );
        Assert.assertNotEquals( resultado.getId(), 0 );
    }


}
