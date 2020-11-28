import org.junit.Assert;
import org.junit.Test;
import ucab.dsw.directorio.DirectorioActivo;
import ucab.dsw.dtos.UsuarioDto;
import ucab.dsw.servicio.Login;

public class pruebaDirectorioActivo
{
    @Test
    public void createUserLDAP()
    {
        UsuarioDto user = new UsuarioDto();
        user.setCorreoelectronico( "daniel@gmail.com" );
        user.setContrasena( "12345" );
        DirectorioActivo ldap = new DirectorioActivo();
        ldap.addEntryToLdap( user );
    }

    @Test
    public void deleteUserLDAP()
    {
        UsuarioDto user = new UsuarioDto();
        user.setCorreoelectronico( "bismarckpm2@gmail.com" );
        DirectorioActivo ldap = new DirectorioActivo();
        ldap.deleteEntry( user );
    }

    @Test
    public void getUserLDAP()
    {
        UsuarioDto user = new UsuarioDto();
        user.setCorreoelectronico( "bismarckpmpruebaLDAP@gmail.com" );
        DirectorioActivo ldap = new DirectorioActivo();
        ldap.getEntry( user );
    }

    @Test
    public void changePassword()
    {
        UsuarioDto user = new UsuarioDto();
        user.setCorreoelectronico( "bismarckpmpruebaLDAP@gmail.com" );
        user.setContrasena( "MARIAPEPE" );
        DirectorioActivo ldap = new DirectorioActivo();
        ldap.changePassword( user );
    }

    @Test
    public void userAuthentication()
    {
        UsuarioDto user = new UsuarioDto();
        user.setCorreoelectronico( "daniel@gmail.com" );
        user.setContrasena( "12345" );
        DirectorioActivo ldap = new DirectorioActivo();
        long resultado=ldap.userAuthentication( user );
        Assert.assertNotEquals(resultado, 0  );
    }

    @Test

    public void login()
    {
        UsuarioDto user = new UsuarioDto();
        user.setCorreoelectronico( "daniel@gmail.com" );
        user.setContrasena( "12345" );
        Login login = new Login();
        login.loginLdap(user);
    }
}
