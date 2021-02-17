package ucab.dsw.logica.comando.marca;

import org.eclipse.persistence.exceptions.DatabaseException;
import ucab.dsw.accesodatos.DaoMarca;
import ucab.dsw.accesodatos.DaoMarcaTipo;
import ucab.dsw.accesodatos.DaoTipo;
import ucab.dsw.dtos.MarcaDto;
import ucab.dsw.dtos.TipoDto;
import ucab.dsw.entidades.Marca;
import ucab.dsw.entidades.MarcaTipo;
import ucab.dsw.entidades.Tipo;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.excepciones.PruebaExcepcion;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import ucab.dsw.mappers.MarcaMapper;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.PersistenceException;

public class InsertMarcaComando extends BaseComando {

    public MarcaDto marcaDto;

    public InsertMarcaComando(MarcaDto marcaDto) {
        this.marcaDto = marcaDto;
    }

    @Override
    public void execute() throws EmpresaException{
        try {

            DaoMarca DaoMarca = Fabrica.crear(DaoMarca.class);
            DaoMarcaTipo daoMarcatipo= Fabrica.crear(DaoMarcaTipo.class);
            DaoTipo daoTipo= Fabrica.crear(DaoTipo.class);

            Marca marca= MarcaMapper.mapDtoToEntityInsert(marcaDto);
            Marca resul = DaoMarca.insert(marca);

            for(TipoDto obj: marcaDto.getTipo_Dto()){

                MarcaTipo marca_tipo=new MarcaTipo();
                Tipo tipo= daoTipo.find(obj.getId(),Tipo.class);
                marca_tipo.set_tipo(tipo);
                marca_tipo.set_marca(resul);

                MarcaTipo resul2 = daoMarcatipo.insert(marca_tipo);
            }

            this.marcaDto=MarcaMapper.mapEntityToDto(resul);
        }
        catch (PruebaExcepcion ex) {
            ex.printStackTrace();
            throw new EmpresaException("C-MA05-ZERO-ID",ex.getMessage(), "Intento asignar el valor de 0 a un ID");
        }
        catch (PersistenceException | DatabaseException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-MA05-DUP",ex.getMessage(), "La marca ya se encuestra registrada");
        }

    }

    @Override
    public JsonObject getResult()throws EmpresaException {

        try {
            JsonObject data = Json.createObjectBuilder()
                    .add("estado", "success")
                    .add("mensaje", "Marca agregada correctamente")
                    .add("marca_id", marcaDto.getId()).build();

            return data;
        }
        catch (NullPointerException ex){
            throw new EmpresaException("C-MA05-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }
}
