package ucab.dsw.test;

import org.junit.Assert;
import org.junit.Test;
import ucab.dsw.directorio.DirectorioActivo;
import ucab.dsw.directorio.RecuperacionPass;
import ucab.dsw.dtos.UsuarioLdapDto;
import ucab.dsw.jwt.Jwt;
import ucab.dsw.servicio.LoginServicio;
import java.io.IOException;
import java.util.ArrayList;

public class DirectorioActivoServicioTest
{
    @Test
    public void createUserLDAP()
    {
        UsuarioLdapDto user = new UsuarioLdapDto();
        user.setCn( "MFalcon" );
        user.setSn("apellido");
        user.setTipo_usuario("encuestado");
        user.setNombre("nombre");
        user.setCorreoelectronico( "drontsch00@gmail.com" );
        user.setUid("1");
        user.setContrasena( "12345" );
        DirectorioActivo ldap = new DirectorioActivo();
        ldap.addEntryToLdap( user );
    }

    @Test
    public void deleteUserLDAP()
    {
        UsuarioLdapDto user = new UsuarioLdapDto();
        user.setCn( "Godzilla77" );
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

    @Test
    public void updateUserLdap(){
        DirectorioActivo ldap = new DirectorioActivo();
        UsuarioLdapDto user = new UsuarioLdapDto();
        user.setCn( "elrequena" );
        user.setSn("Requena");
        user.setNombre("Jesus");
        user.setCorreoelectronico("elrequena123@outlook.com");
        ldap.updateUser( user , "MFalcon" );
    }

    @Test
    public void getAllUsersTest(){
        DirectorioActivo ldap = new DirectorioActivo();
        ArrayList<UsuarioLdapDto> usuarios = ldap.getAllUsers();

        for( UsuarioLdapDto usaurio : usuarios){
            System.out.println(usaurio.getCorreoelectronico());
        }
    }

    @Test
    public void setAllUsers() throws IOException {
        DirectorioActivo ldap = new DirectorioActivo();
        ldap.setAllUsersFromFile("./../../ldap.txt");
    }

    @Test
    public void VerifyToken() throws IOException {
        String token=Jwt.generarToken(1);
        boolean flag=Jwt.verificarToken(token);
        System.out.println(flag);

    }


}
