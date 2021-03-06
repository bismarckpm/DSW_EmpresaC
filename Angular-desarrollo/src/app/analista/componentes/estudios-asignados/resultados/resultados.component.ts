import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { NgEventBus } from 'ng-event-bus';
import { ToastrService } from 'ngx-toastr';

//servicio
import { ConsultaEstudiosService } from "../../../servicios/consulta-estudios/consulta-estudios.service";
import { LoginService } from "../../../../comun/servicios/login/login.service";

export interface SolicitudEstudio {
  id:number;
}

@Component({
  selector: 'app-resultados',
  templateUrl: './resultados.component.html',
  styleUrls: ['./resultados.component.css']
})
export class ResultadosComponent implements OnInit {
  resultadoForm:FormGroup;
  public estudio_id:any;
  constructor(
    public dialogRef: MatDialogRef<ResultadosComponent>,
    @Inject(MAT_DIALOG_DATA) public data: SolicitudEstudio,private fb: FormBuilder,
    private _toastrService: ToastrService,
    private eventBus: NgEventBus,
    private servicio:ConsultaEstudiosService,
    private loginService:LoginService) {}

  ngOnInit(): void {
    this.Crearformulario()
  }

  Crearformulario(){
    this.resultadoForm=this.fb.group({
      resultado: ['', [Validators.required, Validators.minLength(2)] ],
    });
  }

  doResultados():void{
    console.log(this.data.id);
    

    if(this.resultadoForm.valid){
      var objeto={
        solicituEstudioDto:{id:this.data.id},
        respueta:this.resultadoForm.value.resultado
      }
      console.log(objeto)

      this.eventBus.cast('inicio-progress','hola');

      this.servicio.darResultados(objeto).subscribe(x=>{
        this._toastrService.success("Exito", "Resultado agregado");
        this.eventBus.cast('actualizar','hola');
      },error=>{
        
        if(error.error.estado=="unauthorized"){
          this.eventBus.cast('fin-progress','chao');
          this._toastrService.error("Ops! Hubo un problema.", "La sesion expiro.");
          this.loginService.logOut().subscribe(x=>{window.location.reload()}, err=>{window.location.reload()});
  
        }
        else{
          console.log(error);
          if(error.error.mensaje){
            this._toastrService.error("Ops! Hubo un problema.", error.error.mensaje)
          }
          else{
            this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
          }
          this.eventBus.cast('fin-progress','chao');
        }
      })

    }

  }

}
