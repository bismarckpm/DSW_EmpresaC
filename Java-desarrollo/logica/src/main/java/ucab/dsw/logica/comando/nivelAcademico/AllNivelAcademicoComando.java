package ucab.dsw.logica.comando.nivelAcademico;

import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.ResponseDto;
import ucab.dsw.entidades.*;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.List;

public class AllNivelAcademicoComando extends BaseComando {
    public JsonArrayBuilder nivelAcademicos= Json.createArrayBuilder();

    @Override
    public void execute() {

        DaoNivelAcademico dao = new DaoNivelAcademico();
        List<NivelAcademico> resultado = dao.findAll(NivelAcademico.class);

        for (NivelAcademico obj : resultado) {

            JsonObject categoria = Json.createObjectBuilder().add("id", obj.get_id())
                    .add("nombre", obj.get_nombre()).build();

            nivelAcademicos.add(categoria);
        }

    }

    @Override
    public JsonObject getResult() {
        ResponseDto responseDto =Fabrica.crear(ResponseDto.class);
        responseDto.mensaje="Todos los niveles academicos";
        responseDto.estado="success";
        responseDto.objeto=this.nivelAcademicos;


        JsonObject data= Json.createObjectBuilder().add("mensaje","Todos los niveles academicos")
                .add("estado","success")
                .add("niveles_academicos",nivelAcademicos).build();

        return data;
    }
}
