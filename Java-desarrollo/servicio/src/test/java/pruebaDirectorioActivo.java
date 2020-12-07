import org.junit.Assert;
import org.junit.Test;
import ucab.dsw.directorio.DirectorioActivo;
import ucab.dsw.dtos.UsuarioLdapDto;
import ucab.dsw.servicio.LoginServicio;

public class pruebaDirectorioActivo
{
    @Test
    public void createUserLDAP()
    {
        UsuarioLdapDto user = new UsuarioLdapDto();
        user.setCn( "MFalcon" );
        user.setSn("Solo");
        user.setTipo_usuario("analista");
        user.setNombre( "Han");
        user.setCorreoelectronico( "hansolo@gmail.com" );
        user.setUid("2");
        user.setContrasena( "12345" );
        DirectorioActivo ldap = new DirectorioActivo();
        ldap.addEntryToLdap( user );
    }

    @Test
    public void deleteUserLDAP()
    {
        UsuarioLdapDto user = new UsuarioLdapDto();
        user.setCorreoelectronico( "bryan@gmail.com" );
        DirectorioActivo ldap = new DirectorioActivo();
        ldap.deleteEntry( user );
    }

    @Test
    public void getUserLDAP()
    {
        UsuarioLdapDto user = new UsuarioLdapDto();
        user.setUsuario( "bismarckpmpruebaLDAP" );
        DirectorioActivo ldap = new DirectorioActivo();
        ldap.getEntry( user );
    }

    @Test
    public void changePassword()
    {
        UsuarioLdapDto user = new UsuarioLdapDto();
        user.setUsuario( "bismarckpmpruebaLDAP2" );
        user.setContrasena( "MARIAPEPE" );
        DirectorioActivo ldap = new DirectorioActivo();
        ldap.changePassword( user );
    }

    @Test
    public void userAuthentication()
    {
        UsuarioLdapDto user = new UsuarioLdapDto();
        user.setCn( "MFalcon" );
        user.setContrasena( "12345" );
        DirectorioActivo ldap = new DirectorioActivo();
        long resultado=ldap.userAuthentication( user );
        Assert.assertNotEquals(resultado,   0);
    }

   @Test
    public void login()
    {
        UsuarioLdapDto user = new UsuarioLdapDto();
        user.setCn( "MFalcon" );
        user.setContrasena( "12345" );
        LoginServicio loginServicio = new LoginServicio();
        loginServicio.loginLdap(user);
    }

    @Test

    public void getEntryRole()
    {
        UsuarioLdapDto user = new UsuarioLdapDto();
        user.setUsuario( "gabrielOne" );
        DirectorioActivo ldap = new DirectorioActivo();
        ldap.getEntryRole(user);

    }
}
