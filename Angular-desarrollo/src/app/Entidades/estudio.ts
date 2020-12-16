import { Participante } from "./participante";
import { Caracteristicas } from "./caracteristicas";

export class Estudio{

    id: number;
    fecha: string;
    estatus:string;
    caracteristicas: Caracteristicas;
    modo_encuesta:string;
    Participantes:Participante[]


}