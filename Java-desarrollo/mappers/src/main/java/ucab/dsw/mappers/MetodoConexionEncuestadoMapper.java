package ucab.dsw.mappers;

import ucab.dsw.entidades.Encuestado;
import ucab.dsw.entidades.MetodoConexion;
import ucab.dsw.entidades.MetodoConexionEncuestado;

public class MetodoConexionEncuestadoMapper {
    public static MetodoConexionEncuestado mapDtoToEntityInsert(MetodoConexion metodoConexion, Encuestado encuestado){
        MetodoConexionEncuestado entity = new MetodoConexionEncuestado();

        entity.set_metodo_conexion( metodoConexion );
        entity.set_encuestado_metodo_conexion( encuestado );

        return entity;
    }
}
