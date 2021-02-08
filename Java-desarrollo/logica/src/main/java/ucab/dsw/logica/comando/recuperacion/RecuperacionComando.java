package ucab.dsw.logica.comando.recuperacion;

import ucab.dsw.directorio.DirectorioActivo;
import ucab.dsw.directorio.RecuperacionPass;
import ucab.dsw.dtos.UsuarioLdapDto;
import ucab.dsw.excepciones.EmpresaException;
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
    public void execute() throws EmpresaException{
        try{
            DirectorioActivo ldap = new DirectorioActivo();
            String user = ldap.getUserFromMail(this.usuarioLdapDto);

            if (user.equals("")) {
                throw new UsuarioExistenteExcepcion("Usuario no existente");
            }

            RecuperacionPass rec = new RecuperacionPass();
            String newPass = rec.newPass();
            this.usuarioLdapDto.setCn(user);
            ldap.reSetPass(this.usuarioLdapDto, newPass);
            rec.recuperar(this.usuarioLdapDto.getCorreoelectronico(), newPass);
        } catch (UsuarioExistenteExcepcion ex){
            ex.printStackTrace();
            throw new EmpresaException("C-RC01-E-UEE",ex.getMessage(), "Username o correo existente");
        }
    }

    @Override
    public JsonObject getResult() throws EmpresaException {
        try{
            this.data = Json.createObjectBuilder()
                    .add("estado", "success")
                    .add("mensaje", "Correo enviado").build();

            return this.data;
        } catch (NullPointerException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-RC01-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }
}
