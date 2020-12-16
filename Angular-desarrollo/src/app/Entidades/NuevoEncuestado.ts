import { usuarioLdap } from './usuarioLdap'
import { encuestado } from './encuestado'
import { telefono } from './telefono'
import { metodo_conexion } from './metodo_conexion'
import { hijo } from './hijo'
import { ocupacion } from './ocupacion'

export class nuevoEncuestado{
    usuarioLdap: usuarioLdap;
    encuestado: encuestado;
    telefono: telefono[];
    metodo_conexion: metodo_conexion[];
    hijo: hijo[];
    ocupacion: ocupacion[];
    
    constructor(  usuarioLdap: usuarioLdap, encuestado: encuestado ){
        this.usuarioLdap = usuarioLdap;
        this.encuestado = encuestado;
    }
}