package ucab.dsw.logica.comando.usuario;

import ucab.dsw.accesodatos.DaoUsuario;
import ucab.dsw.directorio.DirectorioActivo;
import ucab.dsw.dtos.UsuarioLdapDto;
import ucab.dsw.entidades.Usuario;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;

public class FindAllUsersComando extends BaseComando {
    public JsonObject data;
    public JsonArrayBuilder usuariosArrayJson;

    public FindAllUsersComando() { }

    @Override
    public void execute() throws EmpresaException{
        try{
            DirectorioActivo ldap = new DirectorioActivo();
            ArrayList<UsuarioLdapDto> usuarios = ldap.getAllUsers();
            this.usuariosArrayJson = Json.createArrayBuilder();

            DaoUsuario dao = Fabrica.crear(DaoUsuario.class);
            List<Usuario> resultado = dao.findAll(Usuario.class);

            for (UsuarioLdapDto u : usuarios) {
                JsonObject user_in = null;
                for (Usuario usuar : resultado) {
                    if (u.getUid().equals(String.format("%d", usuar.get_id()))) {
                        user_in = Json.createObjectBuilder()
                                .add("id", usuar.get_id())
                                .add("usuario", usuar.get_usuario())
                                .add("rol", usuar.get_rol())
                                .add("estado", usuar.get_estado())
                                .build();
                    }
                }

                JsonObject usuario = Json.createObjectBuilder()
                        .add("cn", u.getCn())
                        .add("sn", u.getSn())
                        .add("tipo_usuario", u.getTipo_usuario())
                        .add("nombre", u.getNombre())
                        .add("correoelectronico", u.getCorreoelectronico())
                        .add("uid", u.getUid())
                        .add("usuario", user_in)
                        .add("estado", user_in.getString("estado"))
                        .build();

                this.usuariosArrayJson.add(usuario);
            }
        } catch (NullPointerException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-US10-E-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }

    @Override
    public JsonObject getResult() throws EmpresaException{
        try{
            this.data = Json.createObjectBuilder()
                    .add("estado", "success")
                    .add("usuarios", this.usuariosArrayJson).build();

            return this.data;
        } catch (NullPointerException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-US10-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }
}
