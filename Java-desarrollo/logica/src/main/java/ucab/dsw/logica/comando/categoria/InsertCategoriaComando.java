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


public class InsertCategoriaComando extends BaseComando {

    public CategoriaDto categoriaDto;

    public InsertCategoriaComando(CategoriaDto categoriaDto) {
        this.categoriaDto = categoriaDto;
    }

    @Override
    public void execute() {

        try {
            DaoCategoria dao = Fabrica.crear(DaoCategoria.class);
            Categoria categoria = CategoriaMapper.mapDtoToEntityInsert(this.categoriaDto);
            Categoria resul = dao.insert( categoria );
            this.categoriaDto=CategoriaMapper.mapEntityToDto(resul);

        } catch (PruebaExcepcion pruebaExcepcion) {
            pruebaExcepcion.printStackTrace();
        }

    }

    @Override
    public JsonObject getResult() {
        ResponseDto responseDto =Fabrica.crear(ResponseDto.class);
        responseDto.mensaje="Categoria añadida";
        responseDto.estado="success";
        responseDto.objeto=this.categoriaDto.getId();

        JsonObject data= Json.createObjectBuilder()
                .add("estado","success")
                .add("mensaje","Categoria añadida")
                .add("categoria_id",this.categoriaDto.getId()).build();

        return data;
    }
}
