package ucab.dsw.logica.comando.categoria;

import com.fasterxml.jackson.annotation.JsonAlias;
import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.*;
import ucab.dsw.entidades.*;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.excepciones.PruebaExcepcion;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import ucab.dsw.mappers.CategoriaMapper;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.List;

public class ActivateCategoriaComando extends BaseComando {

    public long _id;
    public CategoriaDto categoriaDto;

    public ActivateCategoriaComando(long _id) {
        this._id = _id;
    }

    @Override
    public void execute() throws EmpresaException {
        try{
            DaoCategoria dao = Fabrica.crear(DaoCategoria.class);
            Categoria categoria = dao.find(this._id,Categoria.class);
            categoria.set_estado("activo");
            Categoria resul = dao.update(categoria);
            this.activar_dependencias(resul.get_id());
            this.categoriaDto= CategoriaMapper.mapEntityToDto(resul);

        }
        catch (PruebaExcepcion ex) {
            ex.printStackTrace();
            throw new EmpresaException("C-CA01-ZERO-ID",ex.getMessage(), "Intento asignar el valor de 0 a un ID");
        }

    }

    @Override
    public JsonObject getResult() throws EmpresaException {

        try{
            JsonObject data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("mensaje","Categoria habilitada")
                    .add("categoria_estado",this.categoriaDto.getEstado()).build();

            return data;
        }
        catch (NullPointerException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-CA01-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }

    }

    protected void activar_dependencias(long categoria_id){

        DaoSubcategoria daoSubcategoria = Fabrica.crear(DaoSubcategoria.class);
        List<Subcategoria>  subcategorias = daoSubcategoria.findAll(Subcategoria.class);

        for(Subcategoria obj: subcategorias) {

            if (obj.get_categoria().get_id() == categoria_id){

                Subcategoria subcategoria = daoSubcategoria.find(obj.get_id(), Subcategoria.class);
                subcategoria.set_estado("activo");

                Subcategoria enabledSubcategoria = daoSubcategoria.update(subcategoria);

                DaoMarca daoMarca = Fabrica.crear(DaoMarca.class);
                List<Marca> marcas =daoMarca.findAll(Marca.class);

                for(Marca obj2: marcas) {
                    if (obj2.get_subcategoria().get_id() == enabledSubcategoria.get_id()){

                        Marca marca = daoMarca.find(obj2.get_id(),Marca.class);
                        marca.set_estado("activo");

                        Marca enabledMarca = daoMarca.update(marca);
                    }
                }
            }

        }
    }

}
