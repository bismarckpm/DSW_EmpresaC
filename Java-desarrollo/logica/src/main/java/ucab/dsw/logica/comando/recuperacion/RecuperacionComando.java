package ucab.dsw.logica.comando.recuperacion;

import ucab.dsw.directorio.DirectorioActivo;
import ucab.dsw.directorio.RecuperacionPass;
import ucab.dsw.dtos.UsuarioLdapDto;
import ucab.dsw.excepciones.UsuarioExistenteExcepcion;
import ucab.dsw.logica.comando.BaseComando;

import javax.json.Json;
import javax.json.JsonObject;

public class RecuperacionComando extends BaseComando {

    public UsuarioLdapDto usuarioLdapDto;
    public JsonObject data;

    public RecuperacionComando(UsuarioLdapDto usuarioLdapDto) {
        this.usuarioLdapDto = usuarioLdapDto;
    }

    @Override
    public void execute() throws UsuarioExistenteExcepcion{
        DirectorioActivo ldap = new DirectorioActivo();
        String user = ldap.getUserFromMail(this.usuarioLdapDto);

        if(user.equals("")) {
            throw new UsuarioExistenteExcepcion("Usuario no existente");
        }

        RecuperacionPass rec = new RecuperacionPass();
        String newPass = rec.newPass();
        this.usuarioLdapDto.setCn(user);
        ldap.reSetPass(this.usuarioLdapDto,newPass);
        rec.recuperar(this.usuarioLdapDto.getCorreoelectronico(), newPass);
    }

    @Override
    public JsonObject getResult() {
        this.data= Json.createObjectBuilder()
                .add("estado","success")
                .add("mensaje","Correo enviado").build();

        return this.data;
    }
}
