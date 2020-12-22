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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

public class DirectorioActivo
{

    private DirContext _ldapContext;
    private String _url = "ldap://127.0.0.1:10389";
    private String _connType =  "simple";
    private String _directory =  "ou=users,o=pruebaucab";
    private String _userDirectory =  "cn=%s";
    private String _user =  "admin";
    private String _password =  "secret";

    /*
    Method to connect with the ldap
     */
    public DirectorioActivo()
    {}

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

    public Boolean validateUser(UsuarioLdapDto user){
        try
        {
            Hashtable<String, String> environment = new Hashtable<String, String>();
            environment.put( Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory" );
            environment.put( Context.PROVIDER_URL, _url );
            environment.put( Context.SECURITY_AUTHENTICATION, _connType );
            environment.put( Context.SECURITY_PRINCIPAL, String.format( "cn=%s,ou=users,o=pruebaucab", user.getCn() ) );
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

    public String getEntryUid(UsuarioLdapDto user)
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
                    Attribute atb = atbs.get( "uid" );
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
}