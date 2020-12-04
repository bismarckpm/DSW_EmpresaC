import { Pregunta } from "../pregunta";

export const PREGUNTAS:Pregunta[]=[
{
    Id: 1,
    Descripcion: "¿ Esto es una Pregunta 1?",
    Tipo: "Rango",
    minimo:1,
    maximo:5,
    opciones:[],
    preguntaMultipleSimple:null,
    preguntaRango:null,
    preguntaSimple:null,
},
{
    Id: 2,
    Descripcion: "¿ Esto es una Pregunta 2?",
    Tipo: "Texto",
    minimo:null,
    maximo:null,
    opciones:[],
    preguntaMultipleSimple:null,
    preguntaRango:null,
    preguntaSimple:null,
},
{
    Id: 3,
    Descripcion: "¿ Esto es una Pregunta 3?",
    Tipo: "Boolean",
    minimo:null,
    maximo:null,
    opciones:[],
    preguntaMultipleSimple:null,
    preguntaRango:null,
    preguntaSimple:null,
},
{
    Id: 4,
    Descripcion: "¿ Esto es una Pregunta 4?",
    Tipo: "Texto",
    minimo:null,
    maximo:null,
    opciones:[],
    preguntaMultipleSimple:null,
    preguntaRango:null,
    preguntaSimple:null,
},
{
    Id: 5,
    Descripcion: "¿ Esto es una Pregunta 5?",
    Tipo: "Multiple",
    minimo:null,
    maximo:null,
    opciones:["opcion1","opcion 2", "opcion 3"],
    preguntaMultipleSimple:null,
    preguntaRango:null,
    preguntaSimple:null,
}



]