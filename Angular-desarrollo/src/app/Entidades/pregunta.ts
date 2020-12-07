

export class Pregunta{

    Id: number;
    Descripcion: string;
    Tipo: string;
    minimo:number;
    maximo:number;
    opciones:string[];

    public preguntaSimple(d:string, t:string){
        this.Id=null;
        this.Descripcion=d;
        this.Tipo=t;
        this.minimo=null;
        this.maximo=null;
        this.opciones=null;

    }

    public preguntaRango(d:string, t:string, min:number, max:number){
        this.Id=null;
        this.Descripcion=d;
        this.Tipo=t;
        this.minimo=min;
        this.maximo=max;
        this.opciones=null;

    }

    public preguntaMultipleSimple(d:string, t:string, o:string[]){
        this.Id=null;
        this.Descripcion=d;
        this.Tipo=t;
        this.minimo=null;
        this.maximo=null;
        this.opciones=o;
    }




}