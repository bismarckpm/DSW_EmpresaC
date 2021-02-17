package ucab.dsw.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ucab.dsw.accesodatos.DaoNivelAcademico;
import ucab.dsw.accesodatos.DaoParroquia;
import ucab.dsw.dtos.*;
import ucab.dsw.entidades.NivelAcademico;
import ucab.dsw.entidades.Parroquia;
import ucab.dsw.entidades.Usuario;
import ucab.dsw.excepciones.PruebaExcepcion;
import ucab.dsw.jwt.Jwt;
import ucab.dsw.logica.fabrica.Fabrica;
import ucab.dsw.servicio.UsuarioServicio;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;

public class UsuarioServicioTest {

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
    public void AddCliente(){
        ucab.dsw.servicio.UsuarioServicio servicio = new ucab.dsw.servicio.UsuarioServicio();

        UsuarioLdapDto usuarioLdapDto = new UsuarioLdapDto();
        usuarioLdapDto.setCn("AddClienteTest");
        usuarioLdapDto.setNombre("Juan");
        usuarioLdapDto.setSn("Perez");
        usuarioLdapDto.setCorreoelectronico("jpcliente@mail.com");
        usuarioLdapDto.setTipo_usuario("cliente");
        usuarioLdapDto.setContrasena("12345");

        ClienteDto clienteDto= new ClienteDto();
        clienteDto.setRif("J-1234567");
        clienteDto.setRazon_social("Razon Test");
        clienteDto.setNombre_empresa("EmpresaTest");
        clienteDto.setUsuarioLdapDto(usuarioLdapDto);

        Response respuesta = servicio.AddCliente(this.token, clienteDto);
        JsonObject response= (JsonObject) respuesta.getEntity();
        Assert.assertEquals("\"AddClienteTest\"", response.get("clienteUser").toString() );
    }

    @Test
    public void AddEncuestado() throws PruebaExcepcion {
        ucab.dsw.servicio.UsuarioServicio servicio = new ucab.dsw.servicio.UsuarioServicio();
        
        UsuarioLdapDto usuarioLdapDto = new UsuarioLdapDto();
        usuarioLdapDto.setCn("AddEncuestadoTest");
        usuarioLdapDto.setNombre("Juan");
        usuarioLdapDto.setSn("Perez");
        usuarioLdapDto.setCorreoelectronico("jpencuestado@mail.com");
        usuarioLdapDto.setTipo_usuario("encuestado");
        usuarioLdapDto.setContrasena("12345");
        
        NivelAcademicoDto nivelAcademicoDto = new NivelAcademicoDto();
        nivelAcademicoDto.setId(1L);

        ParroquiaDto parroquiaDto = new ParroquiaDto();
        parroquiaDto.setId(1L);

        EncuestadoDto encuestadoDto = new EncuestadoDto();
        encuestadoDto.setNombre("Juan");
        encuestadoDto.setApellido("Perez");
        encuestadoDto.setCorreo("jpencuestado@mail.com");
        encuestadoDto.setDoc_id(1234567);
        encuestadoDto.setCant_personas_vivienda(3);
        encuestadoDto.setGenero("M");
        encuestadoDto.setFecha_nacimiento("2000-01-01");
        encuestadoDto.setNivel_AcademicoDto(nivelAcademicoDto);
        encuestadoDto.setParroquiaDto(parroquiaDto);
        
        HijoDto[] hijoDtos = new HijoDto[0];
        OcupacionDto[] ocupacionDtos = new OcupacionDto[0];
        MetodoConexionDto[] metodoConexionDtos = new MetodoConexionDto[0];
        TelefonoDto[] telefonoDtos = new TelefonoDto[0];

        NuevoEncuestadoDto nuevoEncuestadoDto = new NuevoEncuestadoDto();
        nuevoEncuestadoDto.setEncuestado(encuestadoDto);
        nuevoEncuestadoDto.setUsuarioLdap(usuarioLdapDto);
        nuevoEncuestadoDto.setHijo(hijoDtos);
        nuevoEncuestadoDto.setMetodo_conexion(metodoConexionDtos);
        nuevoEncuestadoDto.setTelefono(telefonoDtos);
        nuevoEncuestadoDto.setOcupacion(ocupacionDtos);
        
        Response respuesta = servicio.AddEncuestado(this.token, nuevoEncuestadoDto);
        JsonObject response= (JsonObject) respuesta.getEntity();
        Assert.assertEquals("\"AddEncuestadoTest\"", response.get("encuestadoUser").toString() );
    }

    @Test
    public void AddAdmin(){
        ucab.dsw.servicio.UsuarioServicio servicio = new ucab.dsw.servicio.UsuarioServicio();

        UsuarioLdapDto usuarioLdapDto = new UsuarioLdapDto();
        usuarioLdapDto.setCn("AddAdminTest");
        usuarioLdapDto.setNombre("Juan");
        usuarioLdapDto.setSn("Perez");
        usuarioLdapDto.setCorreoelectronico("jpadmin@mail.com");
        usuarioLdapDto.setTipo_usuario("admin");
        usuarioLdapDto.setContrasena("12345");

        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setUsuario("AddAdminTest");

        NuevoUsuarioDto nuevoUsuarioDto = new NuevoUsuarioDto();
        nuevoUsuarioDto.setUsuarioLdapDto(usuarioLdapDto);
        nuevoUsuarioDto.setUsuarioDto(usuarioDto);

        Response respuesta = servicio.AddAdmin(this.token, nuevoUsuarioDto);
        JsonObject response= (JsonObject) respuesta.getEntity();
        Assert.assertEquals("\"AddAdminTest\"", response.get("adminUser").toString() );
    }

    @Test
    public void AddAnalista(){
        ucab.dsw.servicio.UsuarioServicio servicio = new ucab.dsw.servicio.UsuarioServicio();

        UsuarioLdapDto usuarioLdapDto = new UsuarioLdapDto();
        usuarioLdapDto.setCn("AddAnalistaTest");
        usuarioLdapDto.setNombre("Juan");
        usuarioLdapDto.setSn("Perez");
        usuarioLdapDto.setCorreoelectronico("jpanalista@mail.com");
        usuarioLdapDto.setTipo_usuario("analista");
        usuarioLdapDto.setContrasena("12345");

        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setUsuario("AddAnaistaTest");

        NuevoUsuarioDto nuevoUsuarioDto = new NuevoUsuarioDto();
        nuevoUsuarioDto.setUsuarioLdapDto(usuarioLdapDto);
        nuevoUsuarioDto.setUsuarioDto(usuarioDto);

        Response respuesta = servicio.AddAnalista(this.token, nuevoUsuarioDto);
        JsonObject response= (JsonObject) respuesta.getEntity();
        Assert.assertEquals("\"AddAnalistaTest\"", response.get("analistaUser").toString() );
    }

    @Test
    public void activarUsuario(){
        ucab.dsw.servicio.UsuarioServicio servicio = new ucab.dsw.servicio.UsuarioServicio();

        Response respuesta= servicio.activarUsuario(this.token, 25);
        JsonObject response= (JsonObject) respuesta.getEntity();
        Assert.assertEquals("\"activo\"",response.get("estado_user").toString());
    }

    @Test
    public void changeAdmin(){
        ucab.dsw.servicio.UsuarioServicio servicio = new ucab.dsw.servicio.UsuarioServicio();

        UsuarioLdapDto usuarioLdapDto = new UsuarioLdapDto();
        usuarioLdapDto.setCn("ChangeAdminTest");
        usuarioLdapDto.setNombre("Juan");
        usuarioLdapDto.setSn("Perez");
        usuarioLdapDto.setCorreoelectronico("jpadmin@mail.com");
        usuarioLdapDto.setTipo_usuario("admin");
        usuarioLdapDto.setContrasena("12345");

        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setUsuario("ChangeAdminTest");

        NuevoUsuarioDto nuevoUsuarioDto = new NuevoUsuarioDto();
        nuevoUsuarioDto.setUsuarioLdapDto(usuarioLdapDto);
        nuevoUsuarioDto.setUsuarioDto(usuarioDto);

        Response respuesta = servicio.AddAdmin(this.token, nuevoUsuarioDto);
        JsonObject response= (JsonObject) respuesta.getEntity();
        Assert.assertEquals("\"ChangeAdminTest\"", response.get("adminUser").toString() );
    }

    @Test
    public void changeCliente(){
        ucab.dsw.servicio.UsuarioServicio servicio = new ucab.dsw.servicio.UsuarioServicio();

        UsuarioLdapDto usuarioLdapDto = new UsuarioLdapDto();
        usuarioLdapDto.setCn("ChangeClienteTest");
        usuarioLdapDto.setNombre("Juan");
        usuarioLdapDto.setSn("Perez");
        usuarioLdapDto.setCorreoelectronico("jpcliente@mail.com");
        usuarioLdapDto.setTipo_usuario("cliente");
        usuarioLdapDto.setContrasena("12345");

        ClienteDto clienteDto= new ClienteDto();
        clienteDto.setRif("J-1234567");
        clienteDto.setRazon_social("Razon Test");
        clienteDto.setNombre_empresa("EmpresaTest");
        clienteDto.setUsuarioLdapDto(usuarioLdapDto);

        Response respuesta = servicio.changeCliente(this.token, 6,clienteDto);
        JsonObject response= (JsonObject) respuesta.getEntity();
        Assert.assertEquals("\"ChangeClienteTest\"", response.get("clienteUser").toString() );

    }

    @Test
    public void findAllUsers(){
        ucab.dsw.servicio.UsuarioServicio servicio = new ucab.dsw.servicio.UsuarioServicio();

        Response respuesta= servicio.findAllUsers(this.token);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("usuarios"));
    }

    @Test
    public void findCliente(){
        ucab.dsw.servicio.UsuarioServicio servicio = new ucab.dsw.servicio.UsuarioServicio();

        Response respuesta= servicio.findCliente(this.token, 5);
        JsonObject responseDto = (JsonObject) respuesta.getEntity();
        Assert.assertNotNull(responseDto.get("cliente"));
    }

    @Test
    public void deleteUser(){
        ucab.dsw.servicio.UsuarioServicio servicio = new ucab.dsw.servicio.UsuarioServicio();
        UsuarioDto usuarioDto = new UsuarioDto();

        Response respuesta= servicio.deleteUser(this.token, 26,usuarioDto);
        JsonObject responseDto= (JsonObject) respuesta.getEntity();
        Assert.assertEquals("\"inactivo\"",responseDto.get("estado_user").toString());
    }

}
