package ucab.dsw.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ucab.dsw.dtos.UsuarioLdapDto;

import java.util.Date;

public class Jwt {
    private String KEY;

    public Jwt() {
        this.KEY = "desarrollo-de-software"; //Ver como mejorar la seguridad aca
    }

    public String generarToken(UsuarioLdapDto usuarioLdapDto){


        try{
            String token=Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,KEY)
                .setSubject(usuarioLdapDto.getUsuario())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+10800000))
                .compact();

            return token;

        }catch(Exception ex){
            return ex.getMessage();
        }

    }
}
