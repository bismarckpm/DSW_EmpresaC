package ucab.dsw.directorio;

import ucab.dsw.dtos.UsuarioLdapDto;

import javax.naming.Context;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;


/**
 * Una clase para la gestion completa del directorio activo. LDAP
 * @version 1.0, 02/01/2021
 * @author Jesus Requena
 * @author Gabriel Romero
 */
public class DirectorioActivo
{

    private DirContext _ldapContext;
    private String _url = "ldap://127.0.0.1:10389";
    private String _connType =  "simple";
    private String _directory =  "ou=users,o=empresac";
    private String _userDirectory =  "cn=%s";
    private String _user =  "admin";
    private String _password =  "secret";

    /*
    Method to connect with the ldap
     */
    public DirectorioActivo()
    {}

    /**
    * Esta metodo es la encargada de realizar la conexion con el servidor LDAP
    * @author Gabriel Romero
    * @author Jesus Requena
    * @param user corresponde al usuario del admin en el servidor LDAP
    * @param password corresponde a la contrasena de admin en el servidor LDAP
    * @throws Exception si ocurre cualquier excepcion general no controlada previamente
    */
    private void connectLDAP(String user, String password)
    {
        try
        {
            Hashtable<String, String> environment = new Hashtable<String, String>();
            environment.put( Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory" );
            environment.put( Context.PROVIDER_URL, _url );
            environment.put( Context.SECURITY_AUTHENTICATION, _connType );
            environment.put( Context.SECURITY_PRINCIPAL, String.format( "uid=%s,ou=system", user ) );
            environment.put( Context.SECURITY_CREDENTIALS, password );
            _ldapContext = new InitialDirContext( environment );
        }
        catch ( Exception e )
        {

        }
    }

    private void disconnectLDAP()
    {
        try
        {
            _ldapContext.close();
        }
        catch ( Exception e )
        {

        }
    }

    /*
      Method that adds users to ldap
     */
    public void addEntryToLdap(UsuarioLdapDto user) {
        try
        {
            connectLDAP( _user, _password );
            Attribute oc = new BasicAttribute( "objectClass" );
            oc.add( "top" );
            oc.add( "person" );
            oc.add( "organizationalPerson" );
            oc.add( "inetOrgPerson" );
            SimpleDateFormat format = new SimpleDateFormat( "yyyyMMddHHmm" );
            BasicAttributes entry = new BasicAttributes();
            entry.put( oc );
            entry.put( new BasicAttribute( "cn", user.getCn() ) );
            entry.put( new BasicAttribute( "sn", user.getSn() ) );
            entry.put( new BasicAttribute( "description", user.getTipo_usuario()) );
            entry.put( new BasicAttribute( "givenName", user.getNombre() ) );
            entry.put( new BasicAttribute( "mail", user.getCorreoelectronico() ) );
            entry.put( new BasicAttribute( "uid", user.getUid() ) );
            entry.put( new BasicAttribute( "userPassword", user.getContrasena() ) );
            entry.put( new BasicAttribute( "pwdLastSuccess", format.format( new Date() ) + "Z" ) );
            _ldapContext.createSubcontext( String.format( _userDirectory + "," + _directory, user.getCn()), entry );

        } catch (Exception exception) {

            if(exception.getClass().equals(NameAlreadyBoundException.class)){
                System.out.println("Ya hay un usuario con ese correo registrado en el sistema");
            }else{
                exception.printStackTrace();
            }
        }
    }

    /*
     Method that remove users to ldap
    */
    public void deleteEntry(UsuarioLdapDto user)
    {
        try
        {
            connectLDAP( _user, _password );
            _ldapContext.destroySubcontext( String.format(_userDirectory + "," + _directory, user.getCn() ) );
        }
        catch ( Exception exception )
        {
            exception.printStackTrace();
        }
        finally
        {
            disconnectLDAP();
        }
    }

    /*
     Method that obtains user data from ldap
    */
    public void getEntry(UsuarioLdapDto user)
    {
        try
        {
            connectLDAP( _user, _password );
            SearchControls searcCon = new SearchControls();
            searcCon.setSearchScope( SearchControls.SUBTREE_SCOPE );
            NamingEnumeration results =
                    _ldapContext.search( _directory, String.format(_userDirectory, user.getCn() ), searcCon );
            if ( results != null )
            {
                while ( results.hasMore() )
                {
                    SearchResult res = ( SearchResult ) results.next();
                    Attributes atbs = res.getAttributes();
                    Attribute atb = atbs.get( "cn" );
                    String name = ( String ) atb.get();
                }
            }
            else
            {
                System.out.println( "fail" );
            }
        }
        catch ( Exception exception )
        {
            exception.printStackTrace();
        }
        finally
        {
            disconnectLDAP();
        }
    }

    /*
    Method that updates the user in the ldap
     */
    public void updateEntry(UsuarioLdapDto user)
    {
        try
        {
            connectLDAP( _user, _password );
            Attributes atbs = new BasicAttributes();
            Attribute atb = new BasicAttribute("mail","java2db@mai.com");
            atbs.put(atb);

            _ldapContext.modifyAttributes( String.format(_userDirectory + "," + _directory, user.getCn())
                    , DirContext.REPLACE_ATTRIBUTE,atbs );
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            disconnectLDAP();
        }
    }


    public void changePassword(UsuarioLdapDto user)
    {
        try
        {
            connectLDAP( _user, _password );
            ModificationItem[] modificationItems = new ModificationItem[ 2 ];
            modificationItems[ 0 ] = new ModificationItem( DirContext.REPLACE_ATTRIBUTE,
                    new BasicAttribute( "userpassword", user.getContrasena()
                    ) );
            //esto sebe ser cambiado, pero por ahora es una guia
            modificationItems[ 1 ] = new ModificationItem( DirContext.REPLACE_ATTRIBUTE,
                    new BasicAttribute( "description", "NUEVO"
                    ) );
            _ldapContext.modifyAttributes(String.format(_userDirectory + "," + _directory, user.getCn()), modificationItems );
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            disconnectLDAP();
        }
    }

    /**
    * Esta funcion consiste autenticar las credenciales de un usuario 
    * @author Gabriel Romero
    * @param user corresponde al objeto de la capa web que contiene los datos que se desean autenticar (usuario/correo y contraseña)
    * @return retorna un long. Devuelve 0 si las credenciales son incorrectas y devuelve 1 en caso de ser correctas.
    */
    public long userAuthentication(UsuarioLdapDto user)
    {
        try
        {
            connectLDAP( _user, _password );
            if(this.validateUser(user)){
                SimpleDateFormat format = new SimpleDateFormat( "yyyyMMddHHmm" );
                ModificationItem[] modificationItems = new ModificationItem[ 1 ];
                modificationItems[ 0 ] = new ModificationItem( DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(
                        "pwdLastSuccess", format.format( new Date() ) + "Z" ) );
                _ldapContext.modifyAttributes(String.format(_userDirectory + "," + _directory, user.getCn()), modificationItems );
                System.out.println("Credenciales correctas");
                return 1;
            }
            else{
                System.out.println("Credenciales incorrectas");
                return 0;
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            return 0;
        }
        finally
        {
            disconnectLDAP();
        }
    }

    /**
    * Esta funcion consiste validar las credenciales de un usuario con el servidor LDAP
    * @author Gabriel Romero
    * @param user corresponde al objeto que contiene los datos a validar (usuario/correo y contraseña)
    * @return retorna un boolean. Devuelve true si el userContext es distinto de nulo, lo que indica 
    * que las credenciales son validas y devuelve false en caso de ser incorrectas.
    */
    public Boolean validateUser(UsuarioLdapDto user){
        try
        {
            Hashtable<String, String> environment = new Hashtable<String, String>();
            environment.put( Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory" );
            environment.put( Context.PROVIDER_URL, _url );
            environment.put( Context.SECURITY_AUTHENTICATION, _connType );
            environment.put( Context.SECURITY_PRINCIPAL, String.format( "cn=%s,ou=users,o=empresac", user.getCn() ) );
            environment.put( Context.SECURITY_CREDENTIALS, user.getContrasena());
            DirContext userContext = new InitialDirContext( environment );

            if(userContext!=null){
                return true;
            }else{
                return false;
            }
        }
        catch ( Exception e )
        {
            return false;
        }
    }

    /**
    * Esta funcion consiste en obtener el rol del usuario LDAP
    * @author Gabriel Romero 
    * @param user corresponde al objeto que contiene los datos basicos del usuario
    * @return retorna un String, que corresponde con al rol
    */
    public String getEntryRole(UsuarioLdapDto user)
    {
        String role="";
        try
        {
            connectLDAP( _user, _password );
            SearchControls searcCon = new SearchControls();
            searcCon.setSearchScope( SearchControls.SUBTREE_SCOPE );
            NamingEnumeration results =
                    _ldapContext.search( _directory, String.format(_userDirectory, user.getCn()), searcCon );

            if ( results != null )
            {
                while ( results.hasMore() )
                {
                    SearchResult res = ( SearchResult ) results.next();
                    Attributes atbs = res.getAttributes();
                    Attribute atb = atbs.get( "description" );
                    role = ( String ) atb.get();
                }
            }
            else
            {
                System.out.println( "fail" );
                return null;
            }
        }
        catch ( Exception exception )
        {
            exception.printStackTrace();
        }
        finally
        {
            disconnectLDAP();
        }
        System.out.println(role);
        return role;
    }

    /**
    * Esta funcion consiste en obtener el uid del usuario LDAP
    * @author Gabriel Romero 
    * @param user corresponde al objeto que contiene los datos basicos del usuario
    * @return retorna un String, que corresponde con el uid
    */
    public String getEntryUid(UsuarioLdapDto user)
    {
        String uid="";
        try
        {
            connectLDAP( _user, _password );
            SearchControls searcCon = new SearchControls();
            searcCon.setSearchScope( SearchControls.SUBTREE_SCOPE );
            NamingEnumeration results =
                    _ldapContext.search( _directory, String.format(_userDirectory, user.getCn()), searcCon );

            if ( results != null )
            {
                while ( results.hasMore() )
                {
                    SearchResult res = ( SearchResult ) results.next();
                    Attributes atbs = res.getAttributes();
                    Attribute atb = atbs.get( "uid" );
                    uid = ( String ) atb.get();
                }
            }
            else
            {
                System.out.println( "fail" );
                return null;
            }
        }
        catch ( Exception exception )
        {
            exception.printStackTrace();
        }
        finally
        {
            disconnectLDAP();
        }
        System.out.println(uid);
        return uid;
    }


    /**
     * Método para obtener un nombre de usuario del Directorio activo a partir de su correo eletronico
     * @author Jesus Requena
     * @param user precargado con el correo electronico del usuario a buscar en el ldap
     * @return String con el nombre de usuario o cadena vacia en caso de no encontrarlo
     */
    public String getUserFromMail(UsuarioLdapDto user) {
        String usuario = "";
        try {
            connectLDAP( _user, _password );
            SearchControls searcCon = new SearchControls();
            searcCon.setSearchScope( SearchControls.SUBTREE_SCOPE );
            NamingEnumeration results =
                    _ldapContext.search( _directory, String.format("mail=%s", user.getCorreoelectronico()), searcCon );

            if ( results != null ) {
                while ( results.hasMore() ) {
                    SearchResult res = ( SearchResult ) results.next();
                    Attributes atbs = res.getAttributes();
                    Attribute atb = atbs.get( "cn" );
                    usuario = (String)atb.get();
                }
            } else {
                System.out.println( "fail" );
                return null;
            }

        } catch(Exception exception) {
            exception.printStackTrace();
        } finally {
            disconnectLDAP();
        }
        return usuario;
    }

    /**
     * Método para establecer una nueva contraseña a un usuario
     * @author Jesus Requena
     * @param user precargado con el nombre de usuario a modificar en el Directorio Activo
     * @param newPass que almacena la nueva contraseña a insertar
     */
    public void reSetPass(UsuarioLdapDto user, String newPass) {
        try {
            connectLDAP( _user, _password );
            Attributes atbs = new BasicAttributes();
            Attribute atb = new BasicAttribute("userPassword",newPass);
            atbs.put(atb);

            _ldapContext.modifyAttributes( String.format(_userDirectory +","+ _directory, user.getCn())
                    , DirContext.REPLACE_ATTRIBUTE,atbs );

        } catch(Exception exception) {
            exception.printStackTrace();
        } finally {
            disconnectLDAP();
        }
    }

    /**
     * Método para verificar la existencia de un nombre de usuario registrado en el Directorio activo
     * @author Jesus Requena
     * @param user precargado con el nombre de ususario a buscar en el ldap
     * @return String con el nombre de usuario o cadena vacia en caso de no encontrarlo
     */
    public String userExist(UsuarioLdapDto user) {
        String usuario = "";
        try {
            connectLDAP( _user, _password );
            SearchControls searcCon = new SearchControls();
            searcCon.setSearchScope( SearchControls.SUBTREE_SCOPE );
            NamingEnumeration results =
                    _ldapContext.search( _directory, String.format("cn=%s", user.getCn()), searcCon );
            if ( results != null ) {
                while ( results.hasMore() ) {
                    SearchResult res = ( SearchResult ) results.next();
                    Attributes atbs = res.getAttributes();
                    Attribute atb = atbs.get( "cn" );
                    usuario = (String)atb.get();
                }
            } else {
                System.out.println( "fail" );
                return null;
            }
        } catch(Exception exception) {
            exception.printStackTrace();
        } finally {
            disconnectLDAP();
        }
        return usuario;
    }

    /**
     * Método para verificar la existencia de un correo electronico registrado en el Directorio activo
     * @author Jesus Requena
     * @param user precargado con el correo electronico del usuario a buscar en el ldap
     * @return String con el correo electronico o cadena vacia en caso de no encontrarlo
     */
    public String emailExist(UsuarioLdapDto user) {
        String email = "";
        try {
            connectLDAP( _user, _password );
            SearchControls searcCon = new SearchControls();
            searcCon.setSearchScope( SearchControls.SUBTREE_SCOPE );
            NamingEnumeration results =
                    _ldapContext.search( _directory, String.format("mail=%s", user.getCorreoelectronico()), searcCon );
            if ( results != null ) {
                while ( results.hasMore() ) {
                    SearchResult res = ( SearchResult ) results.next();
                    Attributes atbs = res.getAttributes();
                    Attribute atb = atbs.get( "mail" );
                    email = (String)atb.get();
                }
            } else {
                System.out.println( "fail" );
                return null;
            }
        } catch(Exception exception) {
            exception.printStackTrace();
        } finally {
            disconnectLDAP();
        }
        return email;
    }

    /**
     * Método modificar todos los datos de un usuario en el Directorio activo
     * @author Jesus Requena
     * @param user precargado con el id del usuario a buscar en el ldap
     * @return String con el nombre de usuario
     */
    public String getUserFromUid(UsuarioLdapDto user) {
        String usuario = "";
        try {
            connectLDAP( _user, _password );
            SearchControls searcCon = new SearchControls();
            searcCon.setSearchScope( SearchControls.SUBTREE_SCOPE );
            NamingEnumeration results =
                    _ldapContext.search( _directory, String.format("uid=%s", user.getUid()), searcCon );

            if ( results != null ) {
                while ( results.hasMore() ) {
                    SearchResult res = ( SearchResult ) results.next();
                    Attributes atbs = res.getAttributes();
                    Attribute atb = atbs.get( "cn" );
                    usuario = (String)atb.get();
                }
            } else {
                System.out.println( "fail" );
                return null;
            }
        } catch(Exception exception) {
            exception.printStackTrace();
        } finally {
            disconnectLDAP();
        }
        return usuario;
    }

    /**
     * Método modificar todos los datos de un usuario en el Directorio activo
     * @author Jesus Requena
     * @param user precargado con toda la informacion de un usuario a modificar
     * @param originalCn que incluye el nombre de usuario original en caso de la modificacion del mismo
     */
    public void updateUser(UsuarioLdapDto user, String originalCn)
    {
        try
        {
            connectLDAP( _user, _password );
            Attributes atbs = new BasicAttributes();
            atbs.put( new BasicAttribute("sn",user.getSn() ) );
            atbs.put( new BasicAttribute("givenName",user.getNombre() ) );
            atbs.put( new BasicAttribute("mail",user.getCorreoelectronico() ) );

            _ldapContext.modifyAttributes( String.format(_userDirectory + "," + _directory, originalCn)
                    , DirContext.REPLACE_ATTRIBUTE,atbs );

            _ldapContext.rename( String.format(_userDirectory + "," + _directory, originalCn),String.format(_userDirectory + "," + _directory, user.getCn()));
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            disconnectLDAP();
        }
    }

    /**
     * Método para obtener el correo electronico de un usuario del Directorio activo a partir de su id
     * @author Jesus Requena
     * @param user precargado con el id del usuario a buscar en el ldap
     * @return String con el correo electronico o cadena vacia en caso de no encontrarlo
     */
    public String getMailFromUid(UsuarioLdapDto user) {
        String mail = "";
        try {
            connectLDAP( _user, _password );
            SearchControls searcCon = new SearchControls();
            searcCon.setSearchScope( SearchControls.SUBTREE_SCOPE );
            NamingEnumeration results =
                    _ldapContext.search( _directory, String.format("uid=%s", user.getUid()), searcCon );

            if ( results != null ) {
                while ( results.hasMore() ) {
                    SearchResult res = ( SearchResult ) results.next();
                    Attributes atbs = res.getAttributes();
                    Attribute atb = atbs.get( "mail" );
                    mail = (String)atb.get();
                }
            } else {
                System.out.println( "fail" );
                return null;
            }
        } catch(Exception exception) {
            exception.printStackTrace();
        } finally {
            disconnectLDAP();
        }
        return mail;
    }

    /**
     * Método para obtener todos los usuarios del Directorio activo
     * @author Jesus Requena
     * @return ArrayList de usuarios del ldap con sus datos
     */
    public ArrayList<UsuarioLdapDto> getAllUsers(){

        ArrayList<UsuarioLdapDto> usuarios = new ArrayList<UsuarioLdapDto>();

        try {
            connectLDAP( _user, _password );
            SearchControls searcCon = new SearchControls();
            searcCon.setSearchScope( SearchControls.SUBTREE_SCOPE );
            NamingEnumeration results = _ldapContext.search( _directory, "cn=*", searcCon );

            if ( results != null ) {
                while ( results.hasMore() ) {
                    UsuarioLdapDto usuario = new UsuarioLdapDto();
                    SearchResult res = ( SearchResult ) results.next();
                    Attributes atbs = res.getAttributes();

                    usuario.setCn( (String)atbs.get( "cn" ).get() );
                    usuario.setSn( (String)atbs.get( "sn" ).get() );
                    usuario.setTipo_usuario( (String)atbs.get( "description" ).get() );
                    usuario.setNombre( (String)atbs.get( "givenName" ).get() );
                    usuario.setCorreoelectronico( (String)atbs.get( "mail" ).get() );
                    usuario.setUid( (String)atbs.get( "uid" ).get() );
                    usuarios.add( usuario );
                }

            } else {
                System.out.println( "fail" );
                return null;
            }
        } catch(Exception exception) {
            exception.printStackTrace();
        } finally {
            disconnectLDAP();
        }
        return usuarios;
    }

    /**
     * Método para llenar el Directorio activo con los datos de un archivo de texto
     * @author Jesus Requena
     * @param archivo con los datos a cargar en la forma de un string la ruta
     * @throws java.io.IOException Excepcion en caso de encontrar el archivo
     */
    public void setAllUsersFromFile(String archivo) throws IOException {
        String cadena;
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        while((cadena = b.readLine())!=null) {
            String[] datos = cadena.split(" ");
            System.out.println(datos[0] +" "+ datos[1]);
            UsuarioLdapDto user = new UsuarioLdapDto();
            user.setCn( datos[1] );
            user.setSn( datos[4] );
            user.setTipo_usuario( datos[2] );
            user.setNombre( datos[3] );
            user.setCorreoelectronico( datos[5] );
            user.setUid(datos[0]);
            user.setContrasena( "12345" );
            DirectorioActivo ldap = new DirectorioActivo();
            ldap.addEntryToLdap( user );
        }
        b.close();
    }
}