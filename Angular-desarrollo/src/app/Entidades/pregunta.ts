import { Opcion } from "./opciones";

export class Pregunta{

    id: number;
    descripcion: string;
    tipopregunta: string;
    minimo:number;
    maximo:number;
    opciones:Opcion[];

    public preguntaSimple(d:string, t:string){
        this.id=null;
        this.descripcion=d;
        this.tipopregunta=t;
        this.minimo=null;
        this.maximo=null;
        this.opciones=null;

    }

    public preguntaRango(d:string, t:string, min:number, max:number){
        this.id=null;
        this.descripcion=d;
        this.tipopregunta=t;
        this.minimo=min;
        this.maximo=max;
        this.opciones=null;

    }

    public preguntaMultipleSimple(d:string, t:string, o:string[]){
        this.id=null;
        this.descripcion=d;
        this.tipopregunta=t;
        this.minimo=null;
        this.maximo=null;
        this.opciones=o;
    }




}