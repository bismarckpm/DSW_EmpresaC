package ucab.dsw.logica.comando.nivelAcademico;

import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.ResponseDto;
import ucab.dsw.entidades.*;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.List;

public class AllNivelAcademicoComando extends BaseComando {
    public JsonArrayBuilder nivelAcademicos= Json.createArrayBuilder();

    @Override
    public void execute() throws EmpresaException {

        try{
        
            DaoNivelAcademico dao= Fabrica.crear(DaoNivelAcademico.class);
            List<NivelAcademico> resultado = dao.findAll(NivelAcademico.class);

            for (NivelAcademico obj : resultado) {

                JsonObject categoria = Json.createObjectBuilder().add("id", obj.get_id())
                        .add("nombre", obj.get_nombre()).build();

                nivelAcademicos.add(categoria);
            }
        }
        catch (NullPointerException ex){
            throw new EmpresaException("C-NA01-E-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }

    }

    @Override
    public JsonObject getResult() throws EmpresaException {

        try{
            ResponseDto responseDto =Fabrica.crear(ResponseDto.class);
            responseDto.mensaje="Todos los niveles academicos";
            responseDto.estado="success";
            responseDto.objeto=this.nivelAcademicos;


            JsonObject data= Json.createObjectBuilder().add("mensaje","Todos los niveles academicos")
                    .add("estado","success")
                    .add("niveles_academicos",nivelAcademicos).build();

            return data;
        }
        catch (NullPointerException ex){
            throw new EmpresaException("C-NA01-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }
}
