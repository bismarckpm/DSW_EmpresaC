package ucab.dsw.logica.comando.categoria;

import ucab.dsw.accesodatos.DaoCategoria;
import ucab.dsw.dtos.CategoriaDto;
import ucab.dsw.dtos.ResponseDto;
import ucab.dsw.entidades.Categoria;
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
    public void execute() {
        try{
            DaoCategoria dao = new DaoCategoria();
            Categoria categoria = dao.find(_id,Categoria.class);
            this.categoriaDto= CategoriaMapper.mapEntityToDto(categoria);

            categoriaJson= Json.createObjectBuilder()
                                .add("id",categoria.get_id())
                                .add("nombre",categoria.get_nombre())
                                .add("estado",categoria.get_estado()).build();

        }catch (PruebaExcepcion pruebaExcepcion) {
            pruebaExcepcion.printStackTrace();
        }

    }

    @Override
    public JsonObject getResult() {
        ResponseDto responseDto = Fabrica.crear(ResponseDto.class);
        responseDto.mensaje="Categoria consultada";
        responseDto.estado="success";
        responseDto.objeto=this.categoriaDto;

        JsonObject data= Json.createObjectBuilder()
                .add("estado","success")
                .add("mensaje","Categoria consultada")
                .add("categoria",categoriaJson).build();

        return data;
    }
}
