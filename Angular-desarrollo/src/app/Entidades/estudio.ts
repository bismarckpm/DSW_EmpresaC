import { Participante } from "./participante";
import { Caracteristicas } from "./caracteristicas";

export class Estudio{

    id: number;
    fecha: string;
    estatus:string;
    estado:string;
    nombre_encuesta:string;
    caracteristicas: Caracteristicas;
    modo_encuesta:string;
    Participantes:Participante[]


}