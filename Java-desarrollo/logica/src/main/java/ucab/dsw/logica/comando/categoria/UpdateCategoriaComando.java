package ucab.dsw.logica.comando.categoria;
import ucab.dsw.dtos.ResponseDto;
import ucab.dsw.excepciones.PruebaExcepcion;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.*;
import ucab.dsw.entidades.*;
import ucab.dsw.logica.fabrica.Fabrica;
import ucab.dsw.mappers.CategoriaMapper;

import javax.json.Json;
import javax.json.JsonObject;


public class UpdateCategoriaComando extends BaseComando {

    public long _id;
    public CategoriaDto categoriaDto;

    public UpdateCategoriaComando(long _id, CategoriaDto categoriaDto) {
        this._id = _id;
        this.categoriaDto = categoriaDto;
    }

    @Override
    public void execute() {
        try{
            DaoCategoria dao = Fabrica.crear(DaoCategoria.class);
            Categoria categoria= CategoriaMapper.mapDtoToEntityUpdate(_id,categoriaDto);
            Categoria resul = dao.update(categoria);
            this.categoriaDto=CategoriaMapper.mapEntityToDto(resul);
        }
        catch (PruebaExcepcion pruebaExcepcion) {
            pruebaExcepcion.printStackTrace();
        }



    }

    @Override
    public JsonObject getResult() {
        ResponseDto responseDto =Fabrica.crear(ResponseDto.class);
        responseDto.mensaje="Categoria actualizada";
        responseDto.estado="success";
        responseDto.objeto=this.categoriaDto.getNombre();

        JsonObject data= Json.createObjectBuilder()
                .add("estado","success")
                .add("mensaje","Categoria actualizada")
                .add("categoria_nombre",this.categoriaDto.getNombre()).build();

        return data;
    }
}
