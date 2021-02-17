package ucab.dsw.logica.comando;

import ucab.dsw.excepciones.EmpresaException;

import javax.json.JsonObject;

public abstract class BaseComando {

    public abstract void execute() throws EmpresaException;

    public abstract JsonObject getResult() throws EmpresaException;
}
