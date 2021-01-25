package ucab.dsw.logica.comando.marca;

import ucab.dsw.accesodatos.DaoMarca;
import ucab.dsw.accesodatos.DaoMarcaTipo;
import ucab.dsw.accesodatos.DaoTipo;
import ucab.dsw.dtos.MarcaDto;
import ucab.dsw.dtos.TipoDto;
import ucab.dsw.entidades.Marca;
import ucab.dsw.entidades.MarcaTipo;
import ucab.dsw.entidades.Tipo;
import ucab.dsw.excepciones.PruebaExcepcion;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import ucab.dsw.mappers.MarcaMapper;

import javax.json.Json;
import javax.json.JsonObject;

public class InsertMarcaComando extends BaseComando {

    public MarcaDto marcaDto;

    public InsertMarcaComando(MarcaDto marcaDto) {
        this.marcaDto = marcaDto;
    }

    @Override
    public void execute() {
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
        } catch (PruebaExcepcion pruebaExcepcion) {
            pruebaExcepcion.printStackTrace();
        }

    }

    @Override
    public JsonObject getResult() {
        JsonObject data= Json.createObjectBuilder()
                .add("estado","success")
                .add("mensaje","Marca agregada correctamente")
                .add("marca_id", marcaDto.getId()).build();

        return data;
    }
}
