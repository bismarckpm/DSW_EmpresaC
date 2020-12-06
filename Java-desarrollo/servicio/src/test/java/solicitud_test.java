import org.junit.Assert;
import org.junit.Test;
import ucab.dsw.dtos.Caracteristica_DemograficaDto;
import ucab.dsw.dtos.SolicituEstudioDto;
import ucab.dsw.dtos.UsuarioDto;


public class solicitud_test
{

    @Test
    public void addSolicitudTest() throws Exception
    {
        ucab.dsw.servicio.solicitud servicio = new ucab.dsw.servicio.solicitud();
        SolicituEstudioDto solicituEstudioDto = new SolicituEstudioDto();
        Caracteristica_DemograficaDto caracteristica_DemograficaDto = new Caracteristica_DemograficaDto();

        solicituEstudioDto.setModoencuesta("online");

        caracteristica_DemograficaDto.setEdad_min(20);
        caracteristica_DemograficaDto.setEdad_max(25);
        caracteristica_DemograficaDto.setNivel_socioeconomico("bajo");
        caracteristica_DemograficaDto.setNacionalidad("Venezolano");
        caracteristica_DemograficaDto.setCantidad_hijos(1);
        caracteristica_DemograficaDto.setGenero("M");


        SolicituEstudioDto resultado = servicio.addSolicitud(1,1,1,1, solicituEstudioDto,caracteristica_DemograficaDto );
        Assert.assertNotEquals( resultado.getId(), 0 );

    }


}
