import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { NgEventBus } from 'ng-event-bus';


//Entidades
import { Estudio } from "../../../Entidades/estudio";

//Servicios
import { ConsultaEstudiosService } from "../../servicios/consulta-estudios/consulta-estudios.service";
import { LoginService } from "../../../comun/servicios/login/login.service";

@Component({
  selector: 'app-estudios-telefonicos',
  templateUrl: './estudios-telefonicos.component.html',
  styleUrls: ['./estudios-telefonicos.component.css']
})
export class EstudiosTelefonicosComponent implements OnInit {
  estudios:Estudio[]

  constructor(private estudioService:ConsultaEstudiosService,
    private loginService:LoginService,private _toastrService: ToastrService,private eventBus: NgEventBus) { }

  ngOnInit(): void {
    this.estudioService.getEstudiosTelefonicos(11).subscribe(x=>{
      this.estudios=x.estudios;
      console.log(this.estudios)


    },error=>{
      if(error.error.estado=="unauthorized"){
        this.eventBus.cast('fin-progress','chao');
        this._toastrService.error("Ops! Hubo un problema.", "La sesion expiro.");
        this.loginService.logOut().subscribe(x=>{window.location.reload()}, err=>{window.location.reload()});

      }
      else{
        console.log(error);
        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        this.eventBus.cast('fin-progress','chao');
      }
    })


  }

}
