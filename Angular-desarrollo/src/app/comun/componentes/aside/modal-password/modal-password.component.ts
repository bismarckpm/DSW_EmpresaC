import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { NgEventBus } from 'ng-event-bus';
import { ToastrService } from 'ngx-toastr';
import { LoginService } from 'src/app/comun/servicios/login/login.service';
import { ChangePasswordDto } from 'src/app/Entidades/ChangePasswordDto';

@Component({
  selector: 'app-modal-password',
  templateUrl: './modal-password.component.html',
  styleUrls: ['./modal-password.component.css']
})
export class ModalPasswordComponent implements OnInit {

  public actual:any;
  public nueva:any;
  public user_id:any;
  public changePasswordDto:ChangePasswordDto;

  constructor(
              private _loginService:LoginService,
              private eventBus: NgEventBus,
              private _toastrService: ToastrService) { }

  ngOnInit(): void {
    this.changePasswordDto=new ChangePasswordDto();
  }

  change(){
    this.eventBus.cast('inicio-progress','hola');

    this.asignarValores();
    console.log(this.changePasswordDto);
  
    this._loginService.changePassword(this.changePasswordDto).subscribe(
      (response)=>{
		  console.log(response);
		  if(response.estado=='success'){
			  this._toastrService.success("Exito", "Contraseña cambiada");
        this._toastrService.info('Espero un momento, por favor.','Actualizando...');
        this.eventBus.cast('fin-progress','chao');
				this.eventBus.cast('cerrar-modal-password','cerrar');
		  }
		  else{
			  this._toastrService.error("La contraseña actual no coincide", "Error");
        this.eventBus.cast('fin-progress','chao');
        this.eventBus.cast('cerrar-modal-password','cerrar');
		  }
		  

      },
      (error)=>{
        console.log(error);
        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        this.eventBus.cast('fin-progress','chao');
        this.eventBus.cast('cerrar-modal-password','cerrar');
      });
  }

  asignarValores(){
    this.changePasswordDto.user_id=+localStorage.getItem('user_id');
    this.changePasswordDto.contrasena_actual=this.actual;
    this.changePasswordDto.contrasena_nueva=this.nueva;
  }

}
