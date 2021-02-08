package ucab.dsw.logica.comando.login;

import ucab.dsw.accesodatos.DaoUsuario;
import ucab.dsw.entidades.Usuario;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;

import javax.json.Json;
import javax.json.JsonObject;

public class LogoutComando extends BaseComando {

    public long _id;
    public JsonObject data;

    public LogoutComando(long _id) {
        this._id = _id;
    }

    @Override
    public void execute() {
        DaoUsuario daoUsuario = Fabrica.crear(DaoUsuario.class);
        Usuario usuario = daoUsuario.find(_id, Usuario.class);
        usuario.set_token("");
        Usuario resultado = daoUsuario.update(usuario);
    }

    @Override
    public JsonObject getResult() throws EmpresaException{
        try{
            this.data = Json.createObjectBuilder()
                    .add("estado", "success")
                    .build();

            return this.data;
        } catch (NullPointerException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-LO02-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }
}
