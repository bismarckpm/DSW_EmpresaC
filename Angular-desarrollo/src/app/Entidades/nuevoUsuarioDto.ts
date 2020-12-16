import { usuario } from './usuario';
import { usuarioLdap } from './usuarioLDAP';


export class NuevoUsuarioDto{
    usuarioDto: usuario;
    usuarioLdapDto:usuarioLdap;
}