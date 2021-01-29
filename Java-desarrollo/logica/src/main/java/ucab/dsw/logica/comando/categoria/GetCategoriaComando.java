package ucab.dsw.logica.comando.categoria;

import ucab.dsw.accesodatos.DaoCategoria;
import ucab.dsw.dtos.CategoriaDto;
import ucab.dsw.dtos.ResponseDto;
import ucab.dsw.entidades.Categoria;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.excepciones.PruebaExcepcion;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import ucab.dsw.mappers.CategoriaMapper;

import javax.json.Json;
import javax.json.JsonObject;


public class GetCategoriaComando extends BaseComando {

    public CategoriaDto categoriaDto;
    public JsonObject categoriaJson;
    public long _id;

    public GetCategoriaComando(long _id){
        this._id=_id;
    }

    @Override
    public void execute() throws EmpresaException {
        try{
            DaoCategoria dao= Fabrica.crear(DaoCategoria.class);
            Categoria categoria = dao.find(_id,Categoria.class);
            this.categoriaDto= CategoriaMapper.mapEntityToDto(categoria);

            categoriaJson= Json.createObjectBuilder()
                                .add("id",categoria.get_id())
                                .add("nombre",categoria.get_nombre())
                                .add("estado",categoria.get_estado()).build();

        }
        catch (NullPointerException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-CA04-E-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
        catch (PruebaExcepcion ex) {
            ex.printStackTrace();
            throw new EmpresaException("C-CA04-ZERO-ID",ex.getMessage(), "Intento asignar el valor de 0 a un ID");
        }

    }

    @Override
    public JsonObject getResult() throws EmpresaException {

        try{
            JsonObject data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("mensaje","Categoria consultada")
                    .add("categoria",categoriaJson).build();

            return data;

        }
        catch (NullPointerException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-CA04-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }
}
