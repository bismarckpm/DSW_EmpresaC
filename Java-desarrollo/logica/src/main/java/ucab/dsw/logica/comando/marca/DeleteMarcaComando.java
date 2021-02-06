package ucab.dsw.logica.comando.marca;

import ucab.dsw.accesodatos.DaoMarca;
import ucab.dsw.dtos.MarcaDto;
import ucab.dsw.entidades.Marca;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.excepciones.PruebaExcepcion;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import ucab.dsw.mappers.MarcaMapper;

import javax.json.Json;
import javax.json.JsonObject;

public class DeleteMarcaComando extends BaseComando {

    public long _id;
    public MarcaDto marcaDto;

    public DeleteMarcaComando(long _id) {
        this._id = _id;
    }

    @Override
    public void execute() throws EmpresaException{
        try {
            DaoMarca dao = Fabrica.crear(DaoMarca.class);
            Marca marca = dao.find(_id,Marca.class);
            marca.set_estado("inactivo");

            Marca resul = dao.update(marca);

            marcaDto= MarcaMapper.mapEntityToDto(resul);
        }
        catch (PruebaExcepcion ex) {
            ex.printStackTrace();
            throw new EmpresaException("C-MA03-ZERO-ID",ex.getMessage(), "Intento asignar el valor de 0 a un ID");
        }

    }

    @Override
    public JsonObject getResult() throws EmpresaException {

        try {
            JsonObject data = Json.createObjectBuilder()
                    .add("estado", "success")
                    .add("mensaje", "Marca inhabilitada correctamente")
                    .add("estado_marca", marcaDto.getEstado()).build();

            return data;
        }
        catch (NullPointerException ex){
            throw new EmpresaException("C-MA03-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }

    }
}
