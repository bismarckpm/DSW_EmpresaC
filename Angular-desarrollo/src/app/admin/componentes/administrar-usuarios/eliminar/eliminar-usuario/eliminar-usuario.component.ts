import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NgEventBus } from 'ng-event-bus';
import { ToastrService } from 'ngx-toastr';
import { AdministrarUsuariosService } from 'src/app/admin/Servicios/administrar-usuarios/administrar-usuarios.service';
import { usuario } from 'src/app/Entidades/usuario';

@Component({
  selector: 'app-eliminar-usuario',
  templateUrl: './eliminar-usuario.component.html',
  styleUrls: ['./eliminar-usuario.component.css']
})
export class EliminarUsuarioComponent implements OnInit {

  public user_id:any;
  public usuarioDto:usuario;
  constructor(
              public dialogRef: MatDialogRef<EliminarUsuarioComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private _adminUsuarioService:AdministrarUsuariosService,
              private _toastrService: ToastrService,
              private eventBus: NgEventBus
  ) {}

  ngOnInit(): void {
    this.user_id=this.data.user.uid;
    this.usuarioDto=new usuario();
  }

  deleteUsuario(){
    this.usuarioDto.estado='inactivo';
    this.usuarioDto.id=this.user_id;

    console.log(this.usuarioDto);
    
    this.eventBus.cast('inicio-progress','hola');
    this._adminUsuarioService.deleteUsuario(this.user_id,this.usuarioDto).subscribe(
      (response)=>{
		  console.log(response);
		  if(response.estado=='success'){
			    this._toastrService.success("Exito", "Usuario inactiva");
          this._toastrService.info('Espero un momento, por favor.','Actualizando...');
          this.eventBus.cast('actualizar-usuario','actualizar');
		  }
		  else{
			  this._toastrService.error("Esta usuario no se puede eliminar/inhabilitar", "Error");
			  this.eventBus.cast('fin-progress','chao');
		  }
		  

      },
      (error)=>{
        console.log(error);
        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        this.eventBus.cast('fin-progress','chao');
        this.eventBus.cast('cerrar-usuario-add','cerrar');
      });

  }

}
