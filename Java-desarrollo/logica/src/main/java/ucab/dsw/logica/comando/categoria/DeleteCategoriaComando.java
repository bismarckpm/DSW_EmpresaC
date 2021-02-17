package ucab.dsw.logica.comando.categoria;

import ucab.dsw.accesodatos.DaoCategoria;
import ucab.dsw.accesodatos.DaoMarca;
import ucab.dsw.accesodatos.DaoSubcategoria;
import ucab.dsw.dtos.CategoriaDto;
import ucab.dsw.dtos.ResponseDto;
import ucab.dsw.entidades.Categoria;
import ucab.dsw.entidades.Marca;
import ucab.dsw.entidades.Subcategoria;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.excepciones.PruebaExcepcion;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import ucab.dsw.mappers.CategoriaMapper;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.List;

public class DeleteCategoriaComando extends BaseComando {

    public long _id;
    public CategoriaDto categoriaDto;

    public DeleteCategoriaComando(long _id) {
        this._id = _id;
    }

    @Override
    public void execute() throws EmpresaException {
        try{
            DaoCategoria dao = Fabrica.crear(DaoCategoria.class);
            Categoria categoria = dao.find(this._id,Categoria.class);
            categoria.set_estado("inactivo");
            Categoria resul = dao.update(categoria);
            this.desactivar_dependencias(resul.get_id());
            this.categoriaDto= CategoriaMapper.mapEntityToDto(resul);

        }
        catch (PruebaExcepcion ex) {
            ex.printStackTrace();
            throw new EmpresaException("C-CA03-ZERO-ID",ex.getMessage(), "Intento asignar el valor de 0 a un ID");
        }

    }

    @Override
    public JsonObject getResult() throws EmpresaException {

        try{
            JsonObject data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("mensaje","Categoria inhabilitada")
                    .add("categoria_estado",this.categoriaDto.getEstado()).build();

            return data;
        }
        catch (NullPointerException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-CA03-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }

    }

    protected void desactivar_dependencias(long categoria_id){

        DaoSubcategoria daoSubcategoria = Fabrica.crear(DaoSubcategoria.class);
        List<Subcategoria>  subcategorias = daoSubcategoria.findAll(Subcategoria.class);

        for(Subcategoria obj: subcategorias) {

            if (obj.get_categoria().get_id() == categoria_id){

                Subcategoria subcategoria = daoSubcategoria.find(obj.get_id(), Subcategoria.class);
                subcategoria.set_estado("inactivo");

                Subcategoria disabledSubcategoria = daoSubcategoria.update(subcategoria);

                DaoMarca daoMarca = Fabrica.crear(DaoMarca.class);
                List<Marca> marcas =daoMarca.findAll(Marca.class);

                for(Marca obj2: marcas) {
                    if (obj2.get_subcategoria().get_id() == disabledSubcategoria.get_id()){

                        Marca marca = daoMarca.find(obj2.get_id(),Marca.class);
                        marca.set_estado("inactivo");

                        Marca disabledMarca = daoMarca.update(marca);
                    }
                }
            }

        }
    }

}
