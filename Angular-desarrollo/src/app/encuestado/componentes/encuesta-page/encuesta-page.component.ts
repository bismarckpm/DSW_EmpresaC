import { Component, OnInit, ViewChild } from '@angular/core';

import { NgEventBus } from 'ng-event-bus';
import { ToastrService } from 'ngx-toastr';
import { Params, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { switchMap } from 'rxjs/operators';

import { FormBuilder, FormGroup, Validators, FormArray, FormControl } from '@angular/forms';

//entidades
import { Pregunta } from "../../../Entidades/pregunta";
import { Opcion } from "../../../Entidades/opciones";

//services
import { PreguntaService } from "../../servicios/pregunta.service";

@Component({
  selector: 'app-encuesta-page',
  templateUrl: './encuesta-page.component.html',
  styleUrls: ['./encuesta-page.component.css']
})
export class EncuestaPageComponent implements OnInit {
  isLinear = true;
  encuestaForm:FormGroup;
  preguntas:Pregunta[]
  public encuestado_id:any
  opciones:Opcion[];
  estudioid:number;
  

  @ViewChild('stepper') stepper;




  constructor(private fb: FormBuilder,
    private preguntaServicio:PreguntaService,
    private _toastrService: ToastrService,
    private eventBus: NgEventBus,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.opciones=[];
    this.CrearInicial()
    this.init();

    this.route.params.pipe(switchMap((params: Params) => { this.estudioid=params['id']; return  this.preguntaServicio.getEstudioPreguntas(params['id'],1); })).subscribe(
      x=>{
        console.log(x)
        
      this.preguntas=x.Preguntas;
      console.log(this.preguntas)
      this._toastrService.success("Exito", "Preguntas recopiladas");
      this.eventBus.cast('fin-progress','chao');
      this.crearFormulario();

    },err=>{
      this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
      this.eventBus.cast('fin-progress','chao');

    })

    




  }



  init(){
    this.eventBus.cast('inicio-progress','hola');
    this.encuestado_id=localStorage.getItem('user_id');
   
  }

  crearFormulario(){

    for (let index = 0; index < this.preguntas.length; index++) {
     this.addPregunta();
     if(this.preguntas[index].tipopregunta=="Opcion multiple"){
      for (let l = 0; l < this.preguntas[index].opciones.length; l++) {
        this.opciones.push(this.preguntas[index].opciones[l])
        
      }

     }
      
    }
    this.falseOpciones();
    console.log(this.opciones)

  }


  CrearInicial(){
    this.encuestaForm=this.fb.group({
      preguntas:this.fb.array([]),

      
    });
  }

  CrearPregunta():FormGroup{
    return this.fb.group({
      pregunta: ['',[Validators.required]],


    });
  }


  get Preguntas(){
    return this.encuestaForm.get("preguntas") as FormArray;
    
  }



  addPregunta(){
    this.Preguntas.push(this.CrearPregunta());
  }




  falseOpciones(){
    for (let index = 0; index < this.opciones.length; index++) {
      this.opciones[index].isSelec=false;
      
    }
  }

  verificar(x){
    var pre={}
    var comando=0;

    if(this.preguntas[x].tipopregunta=="Opcion multiple"){
      var contador=0;
      for (let index = 0; index < this.preguntas[x].opciones.length; index++) {
        
        if(this.preguntas[x].opciones[index].isSelec==false){
          contador=contador+1;

        }
        
      }

      if(contador==this.preguntas[x].opciones.length){
        this._toastrService.error("Ops! Hubo un problema.", "Debes marcar una opcion minimo");
      }
      else{
        comando=1;
        
        var opciones=[]

        for (let index = 0; index < this.preguntas[x].opciones.length; index++) {
        
          if(this.preguntas[x].opciones[index].isSelec==true){
            var op={
              "id":Number(this.preguntas[x].opciones[index].id)
            }
            opciones.push(op)
  
          }
          
        }


        pre={
          "opciones":{"preguntaDto":{"id":this.preguntas[x].id},
                      "opcion_Simple_MultipleDto":opciones
        },
          "pregunta_EncuestaDto":{
                      "encuestaDto":{"id":this.estudioid},
                      "preguntaDto":{"id":this.preguntas[x].id}
          }
        }
        


      }



    }
    else{
      if(this.encuestaForm.value.preguntas[x].pregunta==""){
        this._toastrService.error("Ops! Hubo un problema.", "Debes responder la pregunta");
      }
      else{
        comando=1;
        if(this.preguntas[x].tipopregunta=="Rango"){
          pre={
            "respuestarango":this.encuestaForm.value.preguntas[x].pregunta,
            "pregunta_EncuestaDto":{
                        "encuestaDto":{"id":this.estudioid},
                        "preguntaDto":{"id":this.preguntas[x].id}
            }
          }


        }
        if(this.preguntas[x].tipopregunta=="desarrollo"){
          pre={
            "respuestadesarrollo":this.encuestaForm.value.preguntas[x].pregunta,
            "pregunta_EncuestaDto":{
                        "encuestaDto":{"id":this.estudioid},
                        "preguntaDto":{"id":this.preguntas[x].id}
            }
          }
        }
        if(this.preguntas[x].tipopregunta=="booleana"){

          pre={
            "respuestaboolean":Number(this.encuestaForm.value.preguntas[x].pregunta),
            "pregunta_EncuestaDto":{
                        "encuestaDto":{"id":this.estudioid},
                        "preguntaDto":{"id":this.preguntas[x].id}
            }
          }

        }

        if(this.preguntas[x].tipopregunta=="Opcion Simple"){
          pre={
            "opciones":{"preguntaDto":{"id":this.preguntas[x].id},
                        "opcion_Simple_MultipleDto":[{id:Number(this.encuestaForm.value.preguntas[x].pregunta)}]
          },
            "pregunta_EncuestaDto":{
                        "encuestaDto":{"id":this.estudioid},
                        "preguntaDto":{"id":this.preguntas[x].id}
            }
          }


        }



        
      }
    }


    if(comando==1){

      console.log(pre)
      this.stepper.next();
    }

  }

  check(z, i){
    console.log(z+"Pregunta" + i);

    for (let index = 0; index < this.preguntas[i].opciones.length; index++) {
      if(this.preguntas[i].opciones[index].id==z){
        var sel= this.preguntas[i].opciones[index].isSelec
        if(sel==true){
          this.preguntas[i].opciones[index].isSelec=false
        }
        else{
          this.preguntas[i].opciones[index].isSelec=true
        }

      }
      
    }

    console.log(this.preguntas);


  }


}
