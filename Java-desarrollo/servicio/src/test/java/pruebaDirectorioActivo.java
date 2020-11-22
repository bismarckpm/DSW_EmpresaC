import org.junit.Test;
import ucab.dsw.dtos.UsuarioDto;

import javax.naming.NamingException;

public class pruebaDirectorioActivo
{
    @Test
    public void createUserLDAP()
    {
        UsuarioDto user = new UsuarioDto();
        user.setCorreoelectronico( "bismarckpm2@gmail.com" );
        user.setContrasena( "PruebaLDAP1234" );
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
        user.setCorreoelectronico( "bismarckpmpruebaLDAP@gmail.com" );
        user.setContrasena( "MARIAPEPE" );
        DirectorioActivo ldap = new DirectorioActivo();
        ldap.userAuthentication( user );
    }
}
