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

public class BuscarEstudioComando extends BaseComando {

    public JsonArrayBuilder estudio= Json.createArrayBuilder();
    public long _id;
    public JsonObject tipo;

    public BuscarEstudioComando(long _id){
        this._id=_id;
    }

    @Override
    public void execute() throws EmpresaException{
        try {
            DaoSolicitudEstudio dao = new DaoSolicitudEstudio();
            DaoMarca daoMarca = new DaoMarca();
            DaoSubcategoria daoSubcategoria = new DaoSubcategoria();
            DaoCategoria daoCategoria = new DaoCategoria();

            Class<SolicitudEstudio> type = SolicitudEstudio.class;

            SolicitudEstudio obj = null;
            obj = dao.find(_id, type);

            Marca marca = daoMarca.find(obj.get_marca().get_id(), Marca.class);
            Subcategoria subcategoria = daoSubcategoria.find(marca.get_subcategoria().get_id(), Subcategoria.class);
            Categoria categoria = daoCategoria.find(subcategoria.get_categoria().get_id(), Categoria.class);

            JsonObject encuesta = Json.createObjectBuilder().add("Marca", marca.get_nombre())
                    .add("Categoria", categoria.get_nombre())
                    .add("idcategoria", categoria.get_id())
                    .add("idMarca", marca.get_id())
                    .add("idsubcategoria", subcategoria.get_id())
                    .add("Subcategoria", subcategoria.get_nombre()).build();

            tipo = Json.createObjectBuilder().add("id", obj.get_id())
                    .add("fecha", obj.get_fecha_inicio().toString())
                    .add("estatus", obj.get_estado())
                    .add("caracteristicas", encuesta)
                    .build();

            estudio.add(tipo);
            }
            catch (NullPointerException ex){
            throw new EmpresaException("C-ADM07-E-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }

    @Override
    public JsonObject getResult() throws EmpresaException{
    try{
            JsonObject data= Json.createObjectBuilder().add("mensaje","Estudio")
                    .add("estado","success")
                    .add("estudio",tipo).build();

            return data;
        }
        catch (NullPointerException ex){
            throw new EmpresaException("C-ADM07-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }

}