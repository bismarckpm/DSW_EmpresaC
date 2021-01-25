package ucab.dsw.logica.comando.admin;

import ucab.dsw.accesodatos.DaoOpcionSimpleMultiple;
import ucab.dsw.accesodatos.DaoOpcionSimpleMultiplePregunta;
import ucab.dsw.accesodatos.DaoPregunta;
import ucab.dsw.dtos.OpcionSimpleMultipleDto;
import ucab.dsw.dtos.OpcionSimpleMultiplePreguntaDto;
import ucab.dsw.dtos.ResponseDto;
import ucab.dsw.dtos.PreguntaDto;
import ucab.dsw.entidades.OpcionSimpleMultiple;
import ucab.dsw.entidades.OpcionSimpleMultiplePregunta;
import ucab.dsw.entidades.Pregunta;
import ucab.dsw.excepciones.PruebaExcepcion;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import ucab.dsw.mappers.OpcionSimpleMultiplePreguntaMapper;
import ucab.dsw.mappers.OpcionSimpleMultipleMapper;
import ucab.dsw.mappers.PreguntaMapper;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.List;

public class AddPreguntaComando extends BaseComando
{
    public PreguntaDto preguntaDto;
    public OpcionSimpleMultipleDto OpcionSimpleMultipleDto;
    public OpcionSimpleMultiplePreguntaDto OpcionSimpleMultiplePreguntaDto;

    public AddPreguntaComando(PreguntaDto preguntadto) {
        this.preguntaDto = preguntadto;
    }

    @Override
    public void execute() {
        try{
            DaoPregunta dao = new DaoPregunta();
            DaoOpcionSimpleMultiple dao2 = new DaoOpcionSimpleMultiple();
            DaoOpcionSimpleMultiplePregunta dao3 = new DaoOpcionSimpleMultiplePregunta();
            Pregunta pregunta = new Pregunta();
            pregunta.set_descripcion(preguntaDto.getDescripcion());
            pregunta.set_tipopregunta(preguntaDto.getTipopregunta());
            pregunta.set_estado("activo");
            if (preguntaDto.getTipopregunta().equals("Rango")) {
                pregunta.set_valormax(preguntaDto.getValormax());
                pregunta.set_valormin(preguntaDto.getValormin());
            }
            Pregunta resul = dao.insert(pregunta);
            this.preguntaDto= PreguntaMapper.mapEntityToDto(resul);

            JsonObject p = Json.createObjectBuilder().add("id", resul.get_id())
                    .build();

            System.out.println("Id: " + resul.get_id());
            System.out.println("Descripcion: " + preguntaDto.getDescripcion());
            System.out.println("Tipo de pregunta: " + preguntaDto.getTipopregunta());
            if (preguntaDto.getValormax() != 0) {
                System.out.println("Rango minimo: " + preguntaDto.getValormin());
                System.out.println("Rango maximo: " + preguntaDto.getValormax());
            }


            if (preguntaDto.getOpciones() != null) {

                List<OpcionSimpleMultipleDto> opcion = preguntaDto.getOpciones();

                for (OpcionSimpleMultipleDto obj : opcion) {
                    OpcionSimpleMultipleDto resultado2 = new OpcionSimpleMultipleDto();
                    OpcionSimpleMultiple opcionSimpleMultiple = new OpcionSimpleMultiple();
                    opcionSimpleMultiple.set_estado("activo");
                    opcionSimpleMultiple.set_opcion(obj.getOpcion());

                    OpcionSimpleMultiple resul2 = dao2.insert(opcionSimpleMultiple);
                    this.OpcionSimpleMultipleDto= OpcionSimpleMultipleMapper.mapEntityToDto(resul2);

                    OpcionSimpleMultiplePreguntaDto resultado3 = new OpcionSimpleMultiplePreguntaDto();
                    OpcionSimpleMultiplePregunta opcion_Simple_Multiple_Pregunta = new OpcionSimpleMultiplePregunta();
                    opcion_Simple_Multiple_Pregunta.set_opcion_Simple_Multiple_Pregunta(resul2);
                    opcion_Simple_Multiple_Pregunta.set_pregunta(resul);

                    OpcionSimpleMultiplePregunta resul3 = dao3.insert(opcion_Simple_Multiple_Pregunta);
                    this.OpcionSimpleMultiplePreguntaDto= OpcionSimpleMultiplePreguntaMapper.mapEntityToDto(resul3);
                }
            }

        }catch (PruebaExcepcion pruebaExcepcion) {
            pruebaExcepcion.printStackTrace();
        }

    }

    @Override
    public JsonObject getResult() {
        ResponseDto responseDto =Fabrica.crear(ResponseDto.class);
        responseDto.mensaje="Pregunta Agregada";
        responseDto.estado="success";
        responseDto.objeto=this.preguntaDto.getId();

        JsonObject data= Json.createObjectBuilder()
                .add("estado","success")
                .add("mensaje","Pregunta Agregada")
                .add("pregunta",this.preguntaDto.getId()).build();

        return data;
    }
}
