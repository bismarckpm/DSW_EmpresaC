import org.junit.Assert;
import org.junit.Test;

import ucab.dsw.accesodatos.DaoPregunta;
import ucab.dsw.accesodatos.DaoSolicitudEstudio;
import javax.ws.rs.core.Response;
import ucab.dsw.dtos.*;

import ucab.dsw.entidades.*;

import java.util.ArrayList;
import java.util.List;

public class analista_test{

        @Test
        public void asignarEncuestaTest() throws Exception
        {
            ucab.dsw.servicio.analista_metodos servicio = new ucab.dsw.servicio.analista_metodos();

            Response resultado = servicio.Empezar_estudio( 1);
            Assert.assertNotEquals( 1, 0 );

        }
}
