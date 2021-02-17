package ucab.dsw.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ucab.dsw.dtos.CategoriaDto;
import ucab.dsw.entidades.Usuario;
import ucab.dsw.jwt.Jwt;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;

public class OcupacionServicioTest {

    public String token;

    @Before
    public void colocarToken(){
        ucab.dsw.accesodatos.DaoUsuario dao=new ucab.dsw.accesodatos.DaoUsuario();
        Usuario usuario=dao.find((long) 1,Usuario.class);
        this.token= Jwt.generarToken(1);
        usuario.set_token(this.token);
        Usuario resul= dao.update(usuario);
    }

    @Test
    public void getOcupaciones()
    {

        ucab.dsw.servicio.OcupacionServicio servicio = new ucab.dsw.servicio.OcupacionServicio();
        Response respuesta= servicio.getAllOcupaciones();
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("ocupaciones"));

    }
}
