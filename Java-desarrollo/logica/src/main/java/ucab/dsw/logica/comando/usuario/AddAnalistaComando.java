package ucab.dsw.logica.comando.usuario;

import org.eclipse.persistence.exceptions.DatabaseException;
import ucab.dsw.accesodatos.DaoUsuario;
import ucab.dsw.directorio.DirectorioActivo;
import ucab.dsw.dtos.NuevoUsuarioDto;
import ucab.dsw.entidades.Usuario;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import ucab.dsw.mappers.UsuarioMapper;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.PersistenceException;

public class AddAnalistaComando extends BaseComando {
    public JsonObject data;
    public Long ID_USER;
    public NuevoUsuarioDto nuevoUsuarioDto;

    public AddAnalistaComando(NuevoUsuarioDto nuevoUsuarioDto) {
        this.nuevoUsuarioDto = nuevoUsuarioDto;
        this.nuevoUsuarioDto.getUsuarioLdapDto().setTipo_usuario("analista");
    }

    @Override
    public void execute() throws EmpresaException{
        try{
            //INSERT USUSARIO
            DaoUsuario daoUsuario = Fabrica.crear(DaoUsuario.class);
            Usuario usuario = UsuarioMapper.mapUsernameToEntityInsert(nuevoUsuarioDto.getUsuarioDto().getUsuario(), "analista");
            Usuario resulU = daoUsuario.insert(usuario);
            ID_USER = resulU.get_id();

            //SET User_ID
            nuevoUsuarioDto.getUsuarioLdapDto().setUid(String.format("%d", ID_USER));

            //INSERT UsuarioLDAP
            DirectorioActivo ldap = new DirectorioActivo();
            ldap.addEntryToLdap(nuevoUsuarioDto.getUsuarioLdapDto());
        } catch (PersistenceException | DatabaseException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-US03-E-DUP",ex.getMessage(), "El analista ya se encuentra registrado");
        }
    }

    @Override
    public JsonObject getResult() throws EmpresaException{
        try{
            JsonObject data = Json.createObjectBuilder()
                    .add("estado", "success")
                    .add("mensaje", "Analista añadido")
                    .add("analistaUser", this.nuevoUsuarioDto.getUsuarioDto().getUsuario()).build();

            return data;
        } catch (NullPointerException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-US03-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }
}
