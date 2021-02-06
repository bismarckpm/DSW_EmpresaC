package ucab.dsw.mappers;

import ucab.dsw.entidades.Usuario;

public class UsuarioMapper {
    public static Usuario mapUsernameToEntityInsert(String cn, String rol){
        Usuario entity = new Usuario();

        entity.set_usuario(cn);
        entity.set_estado("activo");
        entity.set_rol(rol);

        return entity;
    }
}
