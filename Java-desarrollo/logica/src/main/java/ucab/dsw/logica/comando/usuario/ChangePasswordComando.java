package ucab.dsw.logica.comando.usuario;

import org.eclipse.persistence.exceptions.DatabaseException;
import ucab.dsw.directorio.DirectorioActivo;
import ucab.dsw.dtos.CambiarClaveDto;
import ucab.dsw.dtos.UsuarioLdapDto;
import ucab.dsw.excepciones.ContrasenaInvalidaExcepcion;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.logica.comando.BaseComando;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.PersistenceException;

public class ChangePasswordComando extends BaseComando {

    public CambiarClaveDto cambiarClaveDto;
    public JsonObject data;

    public ChangePasswordComando(CambiarClaveDto cambiarClaveDto) {
        this.cambiarClaveDto = cambiarClaveDto;
    }

    @Override
    public void execute() throws EmpresaException {
        try{
            DirectorioActivo ldap = new DirectorioActivo();
            UsuarioLdapDto user = new UsuarioLdapDto();
            user.setUid(cambiarClaveDto.getUser_id());
            user.setContrasena(cambiarClaveDto.getContrasena_actual());
            user.setCn(ldap.getUserFromUid(user));

            if (ldap.validateUser(user)) {
                ldap.reSetPass(user, cambiarClaveDto.getContrasena_nueva());
            } else {
                throw new ContrasenaInvalidaExcepcion("Contraseña invalida");
            }
        } catch ( ContrasenaInvalidaExcepcion ex){
            ex.printStackTrace();
            throw new EmpresaException("C-US08-E-CIE",ex.getMessage(), "Contraseña invalida");
        }
    }

    @Override
    public JsonObject getResult() throws EmpresaException {

        try{
            this.data = Json.createObjectBuilder()
                    .add("estado", "success")
                    .add("mensaje", "Contraseña modificada")
                    .add("old_pass", this.cambiarClaveDto.getContrasena_actual()).build();

            return this.data;
        } catch (NullPointerException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-US08-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }
}
