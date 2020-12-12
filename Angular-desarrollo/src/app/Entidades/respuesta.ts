import { Estudio } from "./estudio";
import { Pregunta } from "./pregunta";

export class Respuesta{

    codigo: number;
    estado: string;
    estudios:Estudio[];
    estudio:Estudio;
    Preguntas:Pregunta[];
    Pregunta:Pregunta;

}