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
    this.route.params.pipe(switchMap((params: Params) => { return  this.preguntaServicio.getEstudioPreguntas(params['id']); })).subscribe(
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
      preguntas:this.fb.array([])
      
    });
  }

  CrearPregunta():FormGroup{
    return this.fb.group({
      pregunta: ['',[Validators.required]],
      opciones:[]

    });
  }


  get Preguntas(){
    return this.encuestaForm.get("preguntas") as FormArray;
    
  }



  addPregunta(){
    this.Preguntas.push(this.CrearPregunta());
  }


  verificar(x){
    console.log(x)
    this.stepper.next();

  }

  falseOpciones(){
    for (let index = 0; index < this.opciones.length; index++) {
      this.opciones[index].isSelec=false;
      
    }
  }
}
