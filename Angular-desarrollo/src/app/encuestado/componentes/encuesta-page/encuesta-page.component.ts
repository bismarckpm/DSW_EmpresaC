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
import { Estudio } from "../../../Entidades/estudio";

//services
import { PreguntaService } from "../../servicios/pregunta.service";
import { EstudiosService } from "../../servicios/estudios.service";

@Component({
  selector: 'app-encuesta-page',
  templateUrl: './encuesta-page.component.html',
  styleUrls: ['./encuesta-page.component.css']
})
export class EncuestaPageComponent implements OnInit {
  public estudio:Estudio;
  public isLinear = true;
  public encuestaForm:FormGroup;
  public preguntas:Pregunta[];
  public encuestado_id:any;
  public user_id:any;
  public opciones:Opcion[];
  public estudioid:number;

  

  @ViewChild('stepper') stepper;




  constructor(private fb: FormBuilder,
    private preguntaServicio:PreguntaService,
    private _toastrService: ToastrService,
    private eventBus: NgEventBus,
    private route: ActivatedRoute,
    private servicioEstudio:EstudiosService,
    private location: Location) { }

  ngOnInit(): void {
    this.opciones=[];
    this.CrearInicial()
    this.init();

  }



  init(){
    this.eventBus.cast('inicio-progress','hola');
    this.getEncuestadoId();
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
          "opciones":opciones,
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
            "opciones":[{"id":Number(this.encuestaForm.value.preguntas[x].pregunta)}],
            "pregunta_EncuestaDto":{
                        "encuestaDto":{"id":this.estudioid},
                        "preguntaDto":{"id":this.preguntas[x].id}
            }
          }


        }



        
      }
    }


    if(comando==1){
      comando=0;
      this.eventBus.cast('inicio-progress','hola');
      this.preguntaServicio.Responder(this.preguntas[x].id,this.estudioid,this.encuestado_id,pre).subscribe(z=>{
        console.log(z)
        
        this._toastrService.success("Exito", "Respuesta exitosa");
        this.eventBus.cast('fin-progress','chao');

        if(this.preguntas.length-1==x){

          this.preguntaServicio.cerrarParticipacion(this.estudioid,this.encuestado_id).subscribe(x=>{

            this._toastrService.success("Exito", "Estudio finalizado");
            this.location.back();

          })

          
          

        }



        this.stepper.next();

      },err=>{

        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        this.eventBus.cast('fin-progress','chao');
        
        
      })

      console.log(pre)
      
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

  getEncuestadoId(){
    this.user_id=+localStorage.getItem('user_id');
    this.servicioEstudio.getEncuestadoId(this.user_id).subscribe(
      (response)=>{
        console.log(response);
        this.encuestado_id=response.encuestado_id;
        console.log('Encuestado: '+this.encuestado_id);
        this.dataEncuesta();
      },
      (error)=>{
        console.log(error);
        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
      });
  }


  dataEncuesta(){
    this.route.params.pipe(switchMap((params: Params) => { this.estudioid=params['id']; return  this.preguntaServicio.getEstudioPreguntas(params['id'],this.encuestado_id); })).subscribe(
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

    });

    this.servicioEstudio.getEstudio(this.estudioid).subscribe(x=>{
      this.estudio=x.estudio;
    },err=>{
      this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
      this.eventBus.cast('fin-progress','chao');
    });
  }


}
