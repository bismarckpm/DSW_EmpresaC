package ucab.dsw.directorio;

import ucab.dsw.dtos.UsuarioLdapDto;

import javax.naming.Context;
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
    public void addEntryToLdap(UsuarioLdapDto user)
    {

        try
        {
            connectLDAP( _user, _password );
            Attribute oc = new BasicAttribute( "objectClass" );
            oc.add( "top" );
            oc.add( "person" );
            SimpleDateFormat format = new SimpleDateFormat( "yyyyMMddHHmm" );
            BasicAttributes entry = new BasicAttributes();
            entry.put( oc );
            entry.put( new BasicAttribute( "pwdLastSuccess", format.format( new Date() ) + "Z" ) );

        }
        catch(Exception exception)
        {
            exception.printStackTrace();
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
                _ldapContext.modifyAttributes(String.format(_userDirectory + "," + _directory, user.getCorreoelectronico()), modificationItems );
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
            environment.put( Context.SECURITY_PRINCIPAL, String.format( "cn=%s,ou=users,o=pruebaucab", user.getCorreoelectronico() ) );
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
}
