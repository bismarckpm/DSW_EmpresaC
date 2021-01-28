package ucab.dsw.logica.comando.usuario;

import ucab.dsw.directorio.DirectorioActivo;
import ucab.dsw.dtos.CambiarClaveDto;
import ucab.dsw.dtos.UsuarioLdapDto;
import ucab.dsw.excepciones.ContrasenaInvalidaExcepcion;
import ucab.dsw.logica.comando.BaseComando;

import javax.json.Json;
import javax.json.JsonObject;

public class ChangePasswordComando extends BaseComando {

    public CambiarClaveDto cambiarClaveDto;
    public JsonObject data;

    public ChangePasswordComando(CambiarClaveDto cambiarClaveDto) {
        this.cambiarClaveDto = cambiarClaveDto;
    }

    @Override
    public void execute() throws ContrasenaInvalidaExcepcion {
        DirectorioActivo ldap = new DirectorioActivo();
        UsuarioLdapDto user = new UsuarioLdapDto();
        user.setUid(cambiarClaveDto.getUser_id());
        user.setContrasena(cambiarClaveDto.getContrasena_actual());
        user.setCn( ldap.getUserFromUid(user) );

        if( ldap.validateUser(user)){
            ldap.reSetPass( user , cambiarClaveDto.getContrasena_nueva());
        }else{
            throw new ContrasenaInvalidaExcepcion("Contraseña invalida");
        }
    }

    @Override
    public JsonObject getResult() {

        this.data= Json.createObjectBuilder()
                .add("estado","success")
                .add("mensaje","Contraseña modificada")
                .add("old_pass",this.cambiarClaveDto.getContrasena_actual()).build();

        return this.data;
    }
}
