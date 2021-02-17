package ucab.dsw.dtos;

public class NuevoUsuarioDto extends BaseDto{

    private UsuarioDto usuarioDto;
    private UsuarioLdapDto usuarioLdapDto;

    public UsuarioDto getUsuarioDto() { return usuarioDto; }

    public void setUsuarioDto(UsuarioDto usuarioDto) { this.usuarioDto = usuarioDto; }

    public UsuarioLdapDto getUsuarioLdapDto() { return usuarioLdapDto; }

    public void setUsuarioLdapDto(UsuarioLdapDto usuarioLdapDto) { this.usuarioLdapDto = usuarioLdapDto; }
}
