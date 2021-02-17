package ucab.dsw.logica.comando.admin;

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

public class PreguntasCategoriaComando extends BaseComando {

    public JsonArrayBuilder preguntas= Json.createArrayBuilder();
    public long _id;

    public PreguntasCategoriaComando(long _id){
        this._id=_id;
    }

    @Override
    public void execute() throws EmpresaException{
        try{
            DaoPregunta daoPregunta = new DaoPregunta();
            DaoEncuesta daoEncuesta = new DaoEncuesta();
            DaoMarca daoMarca = new DaoMarca ();
            DaoPreguntaEncuesta dao = new DaoPreguntaEncuesta();
            DaoSubcategoria daoSubcategoria = new DaoSubcategoria();
            DaoCategoria daoCategoria = new DaoCategoria();

            List<PreguntaEncuesta> resultado = null;
            List<Pregunta> resultado2 = null;
            Class<PreguntaEncuesta> type = PreguntaEncuesta.class;
            Class<Pregunta> type2 = Pregunta.class;

            resultado = dao.findAll(type);
            resultado2 = daoPregunta.findAll(type2);
            for (Pregunta obj2 : resultado2) {
                int cont = 0;
                Pregunta pregunta2 = daoPregunta.find(obj2.get_id(), Pregunta.class);
                for (PreguntaEncuesta obj : resultado) {

                    PreguntaEncuesta preguntaEncuesta = dao.find(obj.get_id(), PreguntaEncuesta.class);
                    Pregunta pregunta = daoPregunta.find(preguntaEncuesta.get_pregunta().get_id(), Pregunta.class);
                    Encuesta encuesta = daoEncuesta.find(preguntaEncuesta.get_encuesta().get_id(), Encuesta.class);
                    Marca marca = daoMarca.find(encuesta.get_marca().get_id(), Marca.class);
                    Subcategoria subcategoria = daoSubcategoria.find(marca.get_subcategoria().get_id(),Subcategoria.class);
                    Categoria categoria = daoCategoria.find(subcategoria.get_categoria().get_id(),Categoria.class);

                    if (categoria.get_id() == _id && pregunta.get_id() == pregunta2.get_id()) {
                        cont=cont+1;
                    }
                }

                if (cont !=0 || pregunta2.get_preguntaencuesta().isEmpty()) {
                    JsonObject p = Json.createObjectBuilder().add("id", pregunta2.get_id())
                            .add("descripcion", pregunta2.get_descripcion())
                            .add("tipopregunta", pregunta2.get_tipopregunta())
                            .build();


                    preguntas.add(p);
                }

            }
        }
        catch (NullPointerException ex){
        throw new EmpresaException("C-ADM08-E-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }

    @Override
    public JsonObject getResult() throws EmpresaException{
        try{
            JsonObject data= Json.createObjectBuilder().add("mensaje","Preguntas por categoria")
                    .add("estado","success")
                    .add("Preguntas",preguntas).build();

            return data;
        }
        catch (NullPointerException ex){
            throw new EmpresaException("C-ADM08-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }

}