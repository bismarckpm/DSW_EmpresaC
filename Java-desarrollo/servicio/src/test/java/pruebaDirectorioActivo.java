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
<<<<<<< HEAD
        user.setCn( "MFalcon" );
        user.setSn("Solo");
=======
        user.setUsuario( "bryanOconor" );
        user.setContrasena( "12345." );
        user.setSn("1");
>>>>>>> ec2028d05a0a9fba4df1fced2f844db80de97032
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
<<<<<<< HEAD
        user.setCorreoelectronico( "bryan@gmail.com" );
=======
        user.setUsuario( "CArlosOconor" );
>>>>>>> ec2028d05a0a9fba4df1fced2f844db80de97032
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
<<<<<<< HEAD
        user.setCn( "MFalcon" );
=======
        user.setUsuario( "danielD" );
>>>>>>> ec2028d05a0a9fba4df1fced2f844db80de97032
        user.setContrasena( "12345" );
        DirectorioActivo ldap = new DirectorioActivo();
        long resultado=ldap.userAuthentication( user );
        Assert.assertNotEquals(resultado,   0);
    }

   @Test
    public void login()
    {
        UsuarioLdapDto user = new UsuarioLdapDto();
<<<<<<< HEAD
        user.setCn( "MFalcon" );
=======
        user.setUsuario( "DiorA" );
>>>>>>> ec2028d05a0a9fba4df1fced2f844db80de97032
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
