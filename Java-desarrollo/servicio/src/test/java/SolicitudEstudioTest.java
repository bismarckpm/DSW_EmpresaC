import org.junit.Assert;
import org.junit.Test;
import ucab.dsw.dtos.*;
import ucab.dsw.entidades.*;

import javax.ws.rs.core.Response;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class SolicitudEstudioTest {
    @Test
    public void getValidarSolicitud() throws Exception
    {
        ucab.dsw.servicio.metodos_admin servicio = new ucab.dsw.servicio.metodos_admin();


        /*--------------Entidad--------------*/

        SolicitudEstudio solicitudEstudio=new SolicitudEstudio();

        DateFormat formato= new SimpleDateFormat("yyyy-MM-dd");
        solicitudEstudio.set_fecha_inicio(formato.parse("2020-12-06"));

        solicitudEstudio.set_estado("pendiente");
        solicitudEstudio.set_modoencuesta("online");

        Cliente cliente= new Cliente(5);
        solicitudEstudio.set_cliente(cliente);

        Caracteristica_Demografica Caracteristica_Demografica= new Caracteristica_Demografica();
        Caracteristica_Demografica.set_edad_min(30);
        Caracteristica_Demografica.set_edad_max(45);
        Caracteristica_Demografica.set_nivel_socioeconomico("Medio");
        Caracteristica_Demografica.set_nacionalidad("Extranjero");
        Caracteristica_Demografica.set_cantidad_hijos(1);
        Caracteristica_Demografica.set_genero("M");

        Nivel_Academico nivel_academico=new Nivel_Academico(5);
        Parroquia parroquia= new Parroquia(5);

        Caracteristica_Demografica.set_nivel_academico_demografia(nivel_academico);
        Caracteristica_Demografica.set_Parroquia_demografia(parroquia);

        solicitudEstudio.set_caracteristicademografica(Caracteristica_Demografica);

        Marca marca=new Marca(5);
        solicitudEstudio.set_marca(marca);

        int respuesta= servicio.prueba(solicitudEstudio);
        Assert.assertEquals(respuesta,1);
    }
}
