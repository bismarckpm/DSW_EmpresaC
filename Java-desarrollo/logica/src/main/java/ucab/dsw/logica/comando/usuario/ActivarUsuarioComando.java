package ucab.dsw.logica.comando.usuario;

import ucab.dsw.accesodatos.DaoUsuario;
import ucab.dsw.dtos.UsuarioDto;
import ucab.dsw.entidades.Usuario;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.excepciones.PruebaExcepcion;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;

import javax.json.Json;
import javax.json.JsonObject;

public class ActivarUsuarioComando extends BaseComando {
    public JsonObject data;
    public long _id;
    public UsuarioDto usuarioDto;

    public ActivarUsuarioComando(long _id) {
        this._id = _id;
    }

    @Override
    public void execute() {
        DaoUsuario dao = Fabrica.crear(DaoUsuario.class);
        Usuario usuario = dao.find(_id,Usuario.class);
        usuario.set_estado("activo");
        Usuario resul = dao.update(usuario);
    }

    @Override
    public JsonObject getResult() throws EmpresaException {
        try{
            this.data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("mensaje","Usuario activado")
                    .add("estado_user","activo").build();

            return this.data;
        } catch (NullPointerException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-US01-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }
}
