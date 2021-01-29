package ucab.dsw.logica.comando.tipo;

import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.MarcaDto;
import ucab.dsw.dtos.PresentacionDto;
import ucab.dsw.dtos.SubcategoriaDto;
import ucab.dsw.dtos.TipoDto;
import ucab.dsw.entidades.*;
import ucab.dsw.excepciones.PruebaExcepcion;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import ucab.dsw.mappers.SubcategoriaMapper;
import ucab.dsw.mappers.TipoMapper;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.List;

public class ActivateTipoComando extends BaseComando {
    public long _id;
    public TipoDto tipoDto;

    public ActivateTipoComando(long _id) {
        this._id = _id;
    }
    @Override
    public void execute() {
        try {
            DaoTipo dao = Fabrica.crear(DaoTipo.class);
            DaoPresentacion daoPresentacion= Fabrica.crear(DaoPresentacion.class);
            DaoMarcaTipo daoMarcaTipo= Fabrica.crear(DaoMarcaTipo.class);
            DaoMarca daoMarca= Fabrica.crear(DaoMarca.class);

            Tipo tipo = dao.find(_id,Tipo.class);
            tipo.set_estado("activo");

            Tipo resul = dao.update(tipo);

            List<Presentacion> resultado2 =daoPresentacion.findAll(Presentacion.class);


            for(Presentacion obj: resultado2) {

                if (obj.get_tipo().get_id() == resul.get_id()){
                    PresentacionDto resultado3 = new PresentacionDto();
                    Presentacion presentacion = daoPresentacion.find(obj.get_id(), Presentacion.class);

                    presentacion.set_estado("activo");

                    Presentacion resul2 = daoPresentacion.update(presentacion);
                    resultado3.setId( resul2.get_id() );
                }



            }

            List<MarcaTipo> resultado4 =daoMarcaTipo.findAll(MarcaTipo.class);

            for(MarcaTipo obj: resultado4) {

                if (obj.get_tipo().get_id() == resul.get_id()){
                    MarcaDto resultado5 = new MarcaDto();
                    Marca marca = daoMarca.find(obj.get_marca().get_id(), Marca.class);

                    marca.set_estado("activo");

                    Marca resul3 = daoMarca.update(marca);
                    resultado5.setId( resul3.get_id() );
                }
            }
            tipoDto= TipoMapper.mapEntityToDto(resul);

        } catch (PruebaExcepcion pruebaExcepcion) {
            pruebaExcepcion.printStackTrace();
        }


    }

    @Override
    public JsonObject getResult() {
        JsonObject data= Json.createObjectBuilder()
                .add("estado","success")
                .add("mensaje","Tipo habilitada correctamente")
                .add("estado_tipo", tipoDto.getEstado()).build();

        return data;
    }
}
