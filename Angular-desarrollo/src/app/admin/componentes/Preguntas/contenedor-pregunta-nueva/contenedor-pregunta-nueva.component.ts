import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray } from '@angular/forms';

//Entidades
import { Pregunta } from "../../../../Entidades/pregunta";

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

  constructor(private fb: FormBuilder) {
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
    if(this.PreguntaForm.value.tipo=="Texto" || this.PreguntaForm.value.tipo=="Bolean" ){
      
      this.pregunta.preguntaSimple(this.PreguntaForm.value.pregunta,this.PreguntaForm.value.tipo);
      console.log(this.pregunta)
      this.PreguntaForm.reset();
    }
    else if(this.PreguntaForm.value.tipo=="Rango"){
      console.log("pregunta de rango")

      if(this.PreguntaForm.value.minimo==null || this.PreguntaForm.value.maximo==null){
        this.error="agrega un valor a minimo y maximo";
      }
      else{

        this.pregunta.preguntaRango(this.PreguntaForm.value.pregunta,this.PreguntaForm.value.tipo,Number(this.PreguntaForm.value.minimo), Number(this.PreguntaForm.value.maximo));
        console.log(this.pregunta)
        this.PreguntaForm.reset();
      }

    }
    else{

      if(this.PreguntaForm.value.opcion1==null || this.PreguntaForm.value.opcion2==null || this.PreguntaForm.value.opcion1=="" || this.PreguntaForm.value.opcion2==""){
        this.error="Agrega minimo 2 opciones correctas a tu pregunta";
      }
      else{
        console.log("pregunta correcta");
        this.opciones=this.PreguntaForm.value.opciones.filter(x=>x!="");
        this.opciones.push(this.PreguntaForm.value.opcion1);
        this.opciones.push(this.PreguntaForm.value.opcion2);


        this.pregunta.preguntaMultipleSimple(this.PreguntaForm.value.pregunta,this.PreguntaForm.value.tipo,this.opciones)
        this.PreguntaForm.reset();
        console.log(this.pregunta);
      }


      
    }

   
  }


}
