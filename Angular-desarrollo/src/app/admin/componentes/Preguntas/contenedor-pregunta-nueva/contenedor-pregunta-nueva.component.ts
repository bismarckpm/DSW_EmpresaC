import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray } from '@angular/forms';

//Entidades
import { Pregunta } from "../../../../Entidades/pregunta";

// Servicios
import { PreguntaService } from "../../../Servicios/pregunta.service";

@Component({
  selector: 'app-contenedor-pregunta-nueva',
  templateUrl: './contenedor-pregunta-nueva.component.html',
  styleUrls: ['./contenedor-pregunta-nueva.component.css']
})
export class ContenedorPreguntaNuevaComponent implements OnInit {
  @ViewChild('fform') PreguntaFormDirective;
  PreguntaForm:FormGroup;
  @Input() id:Number;
  pregunta:Pregunta;
  tipo:String;
  opciones:string[];

  error:String="";


  get Opciones(){
    return this.PreguntaForm.get("opciones") as FormArray;
  }

  addOpciones(){
    this.Opciones.push(this.fb.control(""));
    
  }

  deleteOpciones(){
    this.Opciones.removeAt(this.Opciones.length-1);
    console.log(this.Opciones.length);
  }

  constructor(private fb: FormBuilder,
    private preguntaServicio:PreguntaService) {
    this.pregunta= new Pregunta;
    this.opciones=[];
   }

  ngOnInit(): void {
    this.CrearAgregador();
  }

  CrearAgregador(){
    this.PreguntaForm=this.fb.group({
      pregunta: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(25)] ],
      tipo: ['Texto'],
      minimo:[0,[Validators.min(0)]],
      maximo:[5,[Validators.min(5),Validators.max(100)]],

      opcion1:[""],
      opcion2:[""],
      opciones:this.fb.array([])
    });

    this.PreguntaForm.valueChanges
      .subscribe(data => this.onValueChanged(data));

  }
  onValueChanged(data?: any){
    this.tipo=this.PreguntaForm.value.tipo
    
  }
  

  AceptarPregunta(){
    var res=0;
    var pre={};
    if(this.PreguntaForm.value.tipo=="Texto" || this.PreguntaForm.value.tipo=="Bolean" ){
      res=1;
      this.pregunta.preguntaSimple(this.PreguntaForm.value.pregunta,this.PreguntaForm.value.tipo);
      pre={
        "descripcion": this.PreguntaForm.value.pregunta,
        "tipopregunta": this.PreguntaForm.value.tipo,
      }

      console.log(pre)
      
    }
    else if(this.PreguntaForm.value.tipo=="Rango"){
      console.log("pregunta de rango")

      if(this.PreguntaForm.value.minimo==null || this.PreguntaForm.value.maximo==null){
        this.error="agrega un valor a minimo y maximo";
      }
      else{
        res=1;
        this.pregunta.preguntaRango(this.PreguntaForm.value.pregunta,this.PreguntaForm.value.tipo,Number(this.PreguntaForm.value.minimo), Number(this.PreguntaForm.value.maximo));
        pre={
          "descripcion": this.PreguntaForm.value.pregunta,
          "tipopregunta": this.PreguntaForm.value.tipo,
          "valormin":Number(this.PreguntaForm.value.minimo),
          "valormax":Number(this.PreguntaForm.value.maximo)
        }
  
        console.log(pre)
      }

    }
    else{

      if(this.PreguntaForm.value.opcion1==null || this.PreguntaForm.value.opcion2==null || this.PreguntaForm.value.opcion1=="" || this.PreguntaForm.value.opcion2==""){
        this.error="Agrega minimo 2 opciones correctas a tu pregunta";
      }
      else{
        res=1;
        var op=[];
        console.log("pregunta correcta");
        this.opciones=this.PreguntaForm.value.opciones.filter(x=>x!="");
        this.opciones.push(this.PreguntaForm.value.opcion1);
        this.opciones.push(this.PreguntaForm.value.opcion2);

        var o3={};


        for (let index = 0; index < this.opciones.length; index++) {
          o3={"opcion":this.opciones[index]};
          op.push(o3);
        }

        pre={
          "descripcion": this.PreguntaForm.value.pregunta,
          "tipopregunta": this.PreguntaForm.value.tipo,
          "opciones":op
        }
  
        console.log(pre)
      }


      
    }

    if(res==1){
      this.preguntaServicio.postPreguntas(pre).subscribe(x=>{
        console.log(x.codigo)


      })
      this.PreguntaForm.reset();
    }

   
  }


}
