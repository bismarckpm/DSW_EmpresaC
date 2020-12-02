import org.junit.Assert;
import org.junit.Test;

import ucab.dsw.accesodatos.DaoSolicitudEstudio;
import ucab.dsw.dtos.*;

import ucab.dsw.entidades.Caracteristica_Demografica;
import ucab.dsw.entidades.Cliente;
import ucab.dsw.entidades.SolicitudEstudio;
import ucab.dsw.entidades.Usuario;


public class metodos_admin_Test {


    @Test
    public void find_asignadosTest() throws Exception
    {
        ucab.dsw.servicio.metodos_admin servicio = new ucab.dsw.servicio.metodos_admin();
        int resultado= servicio.consultaEstudios_asignados();
        Assert.assertNotEquals( resultado, 0 );


    }

    @Test
    public void find_no_asignadosTest() throws Exception
    {
        ucab.dsw.servicio.metodos_admin servicio = new ucab.dsw.servicio.metodos_admin();
        int resultado= servicio.consultaEstudios_no_asignados();
        Assert.assertNotEquals( resultado, 0 );


    }

    @Test
    public void preguntas_categoria_subcategoriaTest() throws Exception
    {
        ucab.dsw.servicio.metodos_admin servicio = new ucab.dsw.servicio.metodos_admin();
        int resultado= servicio.preguntas_categoria_subcategoria(1,1);
        Assert.assertNotEquals( resultado, 0 );


    }

    @Test
    public void asignarEncuestaTest() throws Exception
    {
        ucab.dsw.servicio.metodos_admin servicio = new ucab.dsw.servicio.metodos_admin();

        SolicituEstudioDto resultado = servicio.asignarEncuesta( 1 ,2);
        Assert.assertNotEquals( resultado.getId(), 0 );

    }

    @Test
    public void EliminarEstudioTest() throws Exception
    {
        ucab.dsw.servicio.metodos_admin servicio = new ucab.dsw.servicio.metodos_admin();

        SolicituEstudioDto resultado = servicio.EliminarEstudio( 1 );
        Assert.assertNotEquals( resultado.getId(), 0 );

    }
}


