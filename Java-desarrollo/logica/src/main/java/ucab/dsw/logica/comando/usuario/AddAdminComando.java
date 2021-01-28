package ucab.dsw.logica.comando.usuario;

import ucab.dsw.accesodatos.DaoUsuario;
import ucab.dsw.directorio.DirectorioActivo;
import ucab.dsw.dtos.NuevoUsuarioDto;
import ucab.dsw.entidades.Usuario;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import ucab.dsw.mappers.UsuarioMapper;

import javax.json.Json;
import javax.json.JsonObject;

public class AddAdminComando extends BaseComando {
    public JsonObject data;
    public Long ID_USER;
    public NuevoUsuarioDto nuevoUsuarioDto;

    public AddAdminComando(NuevoUsuarioDto nuevoUsuarioDto) {
        this.nuevoUsuarioDto = nuevoUsuarioDto;
        this.nuevoUsuarioDto.getUsuarioLdapDto().setTipo_usuario("admin");
    }

    @Override
    public void execute() {
        //INSERT USUARIO
        DaoUsuario daoUsuario = Fabrica.crear(DaoUsuario.class);
        Usuario usuario= UsuarioMapper.mapUsernameToEntityInsert(nuevoUsuarioDto.getUsuarioDto().getUsuario(), "admin");
        Usuario resulU = daoUsuario.insert(usuario);
        this.ID_USER = resulU.get_id();

        //SET User_ID
        nuevoUsuarioDto.getUsuarioLdapDto().setUid(String.format("%d",this.ID_USER));

        //INSERT UsuarioLDAP
        DirectorioActivo ldap = new DirectorioActivo();
        ldap.addEntryToLdap(nuevoUsuarioDto.getUsuarioLdapDto() );
    }

    @Override
    public JsonObject getResult() {
        JsonObject data= Json.createObjectBuilder()
                .add("estado","success")
                .add("mensaje","Administrador añadido")
                .add("adminUser",this.nuevoUsuarioDto.getUsuarioDto().getUsuario()).build();

        return data;
    }
}
