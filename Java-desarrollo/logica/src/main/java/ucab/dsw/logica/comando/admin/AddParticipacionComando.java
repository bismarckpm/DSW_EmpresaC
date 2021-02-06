package ucab.dsw.logica.comando.admin;

import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.ResponseDto;
import ucab.dsw.entidades.*;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class AddParticipacionComando extends BaseComando {

    public JsonArrayBuilder participante= Json.createArrayBuilder();
    public JsonArrayBuilder aprobado =Json.createArrayBuilder();
    public long _id;

    public AddParticipacionComando(long _id){
        this._id=_id;
    }

    @Override
    public void execute() {
        DaoSolicitudEstudio daoSolicitudEstudio = new DaoSolicitudEstudio();
        DaoEncuestado daoEncuestado = new DaoEncuestado();
        DaoHijo daoHijo = new DaoHijo();
        DaoCaracteristicaDemografica daoCaracteristicaDemografica=new DaoCaracteristicaDemografica();
        DaoUsuario daoUsuario = new DaoUsuario();

        SolicitudEstudio solicitudEstudio = daoSolicitudEstudio.find(_id, SolicitudEstudio.class);

        List<Encuestado> resultado = null;
        Class<Encuestado> type = Encuestado.class;
        resultado = daoEncuestado.findAll(type);

        for (Encuestado obj : resultado) {
            Encuestado encuestado = daoEncuestado.find(obj.get_id(), Encuestado.class);
            Usuario usuario = daoUsuario.find(encuestado.get_usuario_encuestado().get_id(),Usuario.class);
            Date fecha = new Date();

            ZoneId defaultZoneId = ZoneId.systemDefault();
            Instant instant = fecha.toInstant();
            LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
            ZoneId defaultZoneId2 = ZoneId.systemDefault();
            Instant instant2 = encuestado.get_fecha_nacimiento().toInstant();
            LocalDate localDate2 = instant2.atZone(defaultZoneId2).toLocalDate();
            int edad = Period.between(localDate2, localDate).getYears();
            int hijos = 0;
            List<Hijo> hijo = null;
            Class<Hijo> type2 = Hijo.class;
            hijo = daoHijo.findAll(type2);
            for (Hijo obj2 : hijo) {
                if (obj2.get_encuestado_hijo().get_id() == encuestado.get_id()) {
                    hijos = hijos + 1;
                }
            }
            CaracteristicaDemografica caracteristicaDemografica=daoCaracteristicaDemografica.find(solicitudEstudio.get_caracteristicademografica().get_id(), CaracteristicaDemografica.class);
            if (caracteristicaDemografica.get_edad_min() <= edad && solicitudEstudio.get_caracteristicademografica().get_edad_max() >= edad) {
                if (caracteristicaDemografica.get_Parroquia_demografia().get_nombre().equals(encuestado.get_Parroquia_encuestado().get_nombre())) {
                    int cont =0;
                    if (caracteristicaDemografica.get_nacionalidad().equals(encuestado.get_Parroquia_encuestado().get_ciudad().get_estado().get_pais().get_nacionalidad())) {
                        cont=cont+1;
                        aprobado.add(Json.createObjectBuilder()
                                .add("cumple_con_la_nacionalidad", encuestado.get_Parroquia_encuestado().get_ciudad().get_estado().get_pais().get_nacionalidad()));
                    }
                    if (caracteristicaDemografica.get_cantidad_hijos() == hijos) {
                        cont=cont+1;
                        aprobado.add(Json.createObjectBuilder()
                                .add("cumple_con_la_cantidad_de_hijos", hijos));
                    }
                    if (caracteristicaDemografica.get_genero().equals(encuestado.get_genero())) {
                        cont=cont+1;
                        aprobado.add(Json.createObjectBuilder()
                                .add("cumple_con_el_genero", encuestado.get_genero()));
                    }
                    if (caracteristicaDemografica.get_nivel_academico_demografia().get_nombre().equals(encuestado.get_nivel_academico_encuestado().get_nombre())) {
                        cont=cont+1;
                        aprobado.add(Json.createObjectBuilder()
                                .add("cumple_con_el_nivel_academico", encuestado.get_nivel_academico_encuestado().get_nombre()));
                    }
                    if (cont>=2){
                        JsonObject p = Json.createObjectBuilder().add("id", encuestado.get_id())
                                .add("nombre", encuestado.get_nombre())
                                .add("apellido", encuestado.get_apellido())
                                .add("username", usuario.get_usuario())
                                .add("campos_aprobados", aprobado)
                                .build();

                        participante.add(p);
                    }
                }
            }
        }
    }

    @Override
    public JsonObject getResult() {
        ResponseDto responseDto = Fabrica.crear(ResponseDto.class);
        responseDto.mensaje="Participantes";
        responseDto.estado="success";
        responseDto.objeto=this.participante;


        JsonObject data= Json.createObjectBuilder().add("mensaje","participantes")
                .add("estado","success")
                .add("Preguntas",participante).build();

        return data;
    }
}
