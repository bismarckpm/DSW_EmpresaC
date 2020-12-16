import { usuarioLdap } from './usuarioLDAP';

export class ClienteDto{
    id: number;
    rif:string;
    razon_social:string;
    nombre_empresa:string;
    usuarioLdapDto:usuarioLdap;


}