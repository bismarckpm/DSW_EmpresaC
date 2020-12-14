import { Estudio } from "./estudio";
import { Pregunta } from "./pregunta";
import { Participante } from "./participante"

export class Respuesta{

    codigo: number;
    estado: string;
    estudios:Estudio[];
    estudio:Estudio;
    Preguntas:Pregunta[];
    Pregunta:Pregunta;
    Participantes:Participante[];

}