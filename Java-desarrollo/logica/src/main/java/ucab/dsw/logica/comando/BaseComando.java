package ucab.dsw.logica.comando;

import ucab.dsw.dtos.ResponseDto;
import ucab.dsw.excepciones.PruebaExcepcion;

import javax.json.JsonObject;

public abstract class BaseComando {

    public abstract void execute();

    public abstract JsonObject getResult();
}
