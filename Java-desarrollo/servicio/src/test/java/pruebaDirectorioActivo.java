import org.junit.Assert;
import org.junit.Test;
import ucab.dsw.directorio.DirectorioActivo;
import ucab.dsw.directorio.RecuperacionPass;
import ucab.dsw.dtos.UsuarioLdapDto;
import ucab.dsw.servicio.LoginServicio;

public class pruebaDirectorioActivo
{
    @Test
    public void createUserLDAP()
    {
        UsuarioLdapDto user = new UsuarioLdapDto();
        user.setCn( "elrequena" );
        user.setSn("Requena");
        user.setTipo_usuario("administrador");
        user.setNombre( "Jesus");
        user.setCorreoelectronico( "elrequena123@outlook.com" );
        user.setUid("3");
        user.setContrasena( "12345" );
        DirectorioActivo ldap = new DirectorioActivo();
        ldap.addEntryToLdap( user );
    }

    @Test
    public void deleteUserLDAP()
    {
        UsuarioLdapDto user = new UsuarioLdapDto();
        user.setCn( "MFalcon" );
        DirectorioActivo ldap = new DirectorioActivo();
        ldap.deleteEntry( user );
    }

    @Test
    public void getUserLDAP()
    {
        UsuarioLdapDto user = new UsuarioLdapDto();
        user.setCn( "MFalcon" );
        DirectorioActivo ldap = new DirectorioActivo();
        ldap.getEntry( user );
    }

    @Test
    public void changePassword()
    {
        UsuarioLdapDto user = new UsuarioLdapDto();
        user.setCn( "MFalcon" );
        user.setContrasena( "00000" );
        DirectorioActivo ldap = new DirectorioActivo();
        ldap.changePassword( user );
    }

    /*@Test
    public void userAuthentication()
    {
        UsuarioLdapDto user = new UsuarioLdapDto();
        user.setCn( "MFalcon" );
        user.setContrasena( "12345" );
        DirectorioActivo ldap = new DirectorioActivo();
        long resultado=ldap.userAuthentication( user );
        Assert.assertNotEquals(resultado,   0);
    }*/

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
        user.setCn( "MFalcon" );
        DirectorioActivo ldap = new DirectorioActivo();
        ldap.getEntryRole(user);

    }

    @Test
    public void getEntryUid()
    {
        UsuarioLdapDto user = new UsuarioLdapDto();
        user.setCn( "MFalcon" );
        DirectorioActivo ldap = new DirectorioActivo();
        ldap.getEntryUid(user);

    }

    @Test
    public void getUserFromMailtest()
    {
        UsuarioLdapDto user = new UsuarioLdapDto();
        user.setCorreoelectronico( "hansolo@gmail.com" );
        DirectorioActivo ldap = new DirectorioActivo();

        System.out.println(ldap.getUserFromMail(user));
    }

    @Test
    public void reSetPasstest()
    {
        UsuarioLdapDto user = new UsuarioLdapDto();
        user.setCn( "elrequena" );
        DirectorioActivo ldap = new DirectorioActivo();
        ldap.reSetPass(user,"12345");
    }

    @Test
    public void randomtest(){
        RecuperacionPass rec = new RecuperacionPass();
        String str = rec.newPass();
        System.out.println(str);
    }

    @Test
    public void userExisttest() {
        UsuarioLdapDto user = new UsuarioLdapDto();
        user.setCn( "MFalcon" );
        DirectorioActivo ldap = new DirectorioActivo();
        if(user.getCn().equals(ldap.userExist(user))){
            System.out.println("Si existe");
        }else{
            System.out.println("No existe");
        }
    }
}
