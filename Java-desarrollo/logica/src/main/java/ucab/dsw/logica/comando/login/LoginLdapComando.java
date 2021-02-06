package ucab.dsw.logica.comando.login;

import ucab.dsw.accesodatos.DaoUsuario;
import ucab.dsw.directorio.DirectorioActivo;
import ucab.dsw.dtos.UsuarioLdapDto;
import ucab.dsw.entidades.Usuario;
import ucab.dsw.excepciones.ContrasenaInvalidaExcepcion;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.jwt.Jwt;
import ucab.dsw.logica.fabrica.Fabrica;

import javax.json.Json;
import javax.json.JsonObject;

public class LoginLdapComando extends BaseComando {

    public UsuarioLdapDto usuarioLdapDto;
    public JsonObject data;
    public String token = "";

    public LoginLdapComando(UsuarioLdapDto usuarioLdapDto) {
        this.usuarioLdapDto = usuarioLdapDto;
    }

    @Override
    public void execute() throws ContrasenaInvalidaExcepcion {

        DirectorioActivo ldap = new DirectorioActivo();

        if ( this.usuarioLdapDto.getCorreoelectronico() != null ){
            this.usuarioLdapDto.setCn(ldap.getUserFromMail(this.usuarioLdapDto));
        }
        long resultado=ldap.userAuthentication( this.usuarioLdapDto );

        if(resultado==1){
            DaoUsuario daoUsuario = Fabrica.crear(DaoUsuario.class);
            Usuario usuario = daoUsuario.find(Long.parseLong(ldap.getEntryUid(this.usuarioLdapDto)), Usuario.class);

            if (usuario.get_estado().equals("inactivo")){
                throw new ContrasenaInvalidaExcepcion("Usuario Inactivo");
            }

            Jwt jwt = new Jwt();
            this.token= jwt.generarToken(Long.parseLong(ldap.getEntryUid(this.usuarioLdapDto)));
            usuario.set_token(this.token);

            Usuario resul = daoUsuario.update(usuario);

            this.data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200)
                    .add("token",this.token)
                    .add("rol", ldap.getEntryRole(this.usuarioLdapDto))
                    .add("user_id",ldap.getEntryUid(this.usuarioLdapDto)).build();

        }else{
            throw new ContrasenaInvalidaExcepcion("Las credenciales no son correctas. Intente de nuevo.");
        }

    }

    @Override
    public JsonObject getResult() {

        return this.data;
    }
}
