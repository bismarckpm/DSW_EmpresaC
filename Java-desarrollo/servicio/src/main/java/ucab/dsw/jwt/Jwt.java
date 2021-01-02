package ucab.dsw.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ucab.dsw.dtos.UsuarioLdapDto;

import java.util.Date;

/**
 * Una clase para la gestion del token jwt
 * @version 1.0, 02/01/2021
 * @author Gabriel Romero
 */
public class Jwt {
    private String KEY;

    public Jwt() {
        this.KEY = "desarrollo-de-software"; //Ver como mejorar la seguridad aca
    }


    /**
    * Esta funcion consiste en generar un token jwt con un tiempo de expiracion, y el cual sera proximamente
    * enviado por las cabeceras de cada peticion, para validar en capa web si el usuario puede realizar o no la operacion.
    * @author Gabriel Romero
    * @param usuarioLdapDto corresponde al objeto de la capa web que contiene los datos a validar (usuario/correo y contraseña)
    * @throws Exception si ocurre cualquier excepcion general no controlada previamente
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado, token, rol, user_id
    *         y mensaje en caso de ocurrir alguna de las excepciones
    */
    public String generarToken(UsuarioLdapDto usuarioLdapDto){


        try{
            String token=Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,KEY)
                .setSubject(usuarioLdapDto.getCn())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+10800000))
                .compact();

            return token;

        }catch(Exception ex){
            return ex.getMessage();
        }

    }
}
