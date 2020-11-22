import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ucab.dsw.dtos.TipoUsuarioDto;
import ucab.dsw.dtos.UsuarioDto;
import ucab.dsw.entidades.Usuario;


public class pruebaORMWS_Test
{

    @Test
    public void addUserTest() throws Exception
    {
        ucab.dsw.servicio.pruebaORMWS servicio = new ucab.dsw.servicio.pruebaORMWS();
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setNombre( "Bismarck" );
        usuarioDto.setApellido( "Ponce" );
        usuarioDto.setCorreoelectronico( "bismarckpm@gmail.com" );
        usuarioDto.setEstatus( "A" );
        TipoUsuarioDto tipoUsuario = new TipoUsuarioDto( 1);
        usuarioDto.setTipoUsuarioDto( tipoUsuario );
        UsuarioDto resultado = servicio.addUser( usuarioDto );
        Assert.assertNotEquals( resultado.getId(), 0  );
    }


}
