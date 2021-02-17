package ucab.dsw.logica.comando.presentacion;

import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.CategoriaDto;
import ucab.dsw.dtos.PresentacionDto;
import ucab.dsw.dtos.ResponseDto;
import ucab.dsw.entidades.*;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.excepciones.PruebaExcepcion;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import ucab.dsw.mappers.CategoriaMapper;
import ucab.dsw.mappers.PresentacionMapper;

import javax.json.Json;
import javax.json.JsonObject;

public class GetPresentacionComando extends BaseComando {
    public PresentacionDto presentacionDto;
    public JsonObject presentacionJson;

    public long _id;

    public GetPresentacionComando(long _id){
        this._id=_id;
    }

    @Override
    public void execute() throws EmpresaException  {
        try{
            DaoPresentacion dao= Fabrica.crear(DaoPresentacion.class);
            Presentacion presentacion = dao.find(_id,Presentacion.class);

            this.presentacionDto= PresentacionMapper.mapEntityToDto(presentacion);

            presentacionJson= Json.createObjectBuilder()
                    .add("id: ",presentacion.get_id())
                    .add("nombre: ",presentacion.get_nombre())
                    .add("tipo: ",presentacion.get_tipo().get_nombre()).build();

        }
        catch (NullPointerException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-PRE04-E-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
        catch (PruebaExcepcion ex) {
            ex.printStackTrace();
            throw new EmpresaException("C-PRE04-ZERO-ID",ex.getMessage(), "Intento asignar el valor de 0 a un ID");
        }

    }

    @Override
    public JsonObject getResult() throws EmpresaException {
        try{
            ResponseDto responseDto = Fabrica.crear(ResponseDto.class);
            responseDto.mensaje="Presentacion consultada";
            responseDto.estado="success";
            responseDto.objeto=this.presentacionDto;

            JsonObject data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("mensaje","Categoria consultada")
                    .add("categoria",presentacionJson).build();

            return data;
        }
        catch (NullPointerException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-PRE04-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }
}
