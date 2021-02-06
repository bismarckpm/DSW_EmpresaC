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

public class UpdateMarcaComando extends BaseComando {

    public long _id;
    public MarcaDto marcaDto;

    public UpdateMarcaComando(long _id, MarcaDto marcaDto) {
        this._id = _id;
        this.marcaDto = marcaDto;
    }

    @Override
    public void execute() {
        try {

            DaoMarcaTipo daoMarca_tipo= Fabrica.crear(DaoMarcaTipo.class);
            DaoTipo daoTipo= Fabrica.crear(DaoTipo.class);
            DaoMarca dao = Fabrica.crear(DaoMarca.class);


            MarcaTipo marca_tipo=daoMarca_tipo.find(marcaDto.getMarcaTipo_Dto().getId(), MarcaTipo.class);

            for(TipoDto obj: marcaDto.getTipo_Dto()){

                Tipo tipo= daoTipo.find(obj.getId(),Tipo.class);
                marca_tipo.set_tipo(tipo);
                MarcaTipo resul2 = daoMarca_tipo.update(marca_tipo);
            }

            daoMarca_tipo.update(marca_tipo);

            Marca marca =MarcaMapper.mapDtoToEntityUpdate(_id,marcaDto);
            Marca resul = dao.update(marca);

            marcaDto= MarcaMapper.mapEntityToDto(resul);
        } catch (PruebaExcepcion pruebaExcepcion) {
            pruebaExcepcion.printStackTrace();
        }

    }

    @Override
    public JsonObject getResult() {
        JsonObject data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("mensaje","Marca actualizada correctamente")
                    .add("nombre_marca",marcaDto.getNombre()).build();

        return data;
    }
}
