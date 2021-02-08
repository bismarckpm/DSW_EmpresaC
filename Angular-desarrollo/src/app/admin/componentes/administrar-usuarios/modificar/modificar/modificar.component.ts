import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NgEventBus } from 'ng-event-bus';
import { ToastrService } from 'ngx-toastr';
import { AdministrarUsuariosService } from 'src/app/admin/Servicios/administrar-usuarios/administrar-usuarios.service';
import { usuarioLdap } from 'src/app/Entidades/usuarioLDAP';

import { LoginService } from "../../../../../comun/servicios/login/login.service";

@Component({
  selector: 'app-modificar',
  templateUrl: './modificar.component.html',
  styleUrls: ['./modificar.component.css']
})
export class ModificarComponent implements OnInit {

  public uid:string;
  public cn:string;
  public sn:string;
  public correoelectronico:string;
  public nombre:string;
  public usuarioLdapDto:usuarioLdap;

  constructor(public dialogRef: MatDialogRef<ModificarComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private _adminUsuarioService:AdministrarUsuariosService,
              private _toastrService: ToastrService,
              private eventBus: NgEventBus,
              private loginService:LoginService) { }

  ngOnInit(): void {
    this.usuarioLdapDto=new usuarioLdap();
    this.setUsuarioForm();

  }

  setUsuarioForm(){
    this.cn=this.data.user.cn;
    this.correoelectronico=this.data.user.correoelectronico;
    this.nombre=this.data.user.nombre;
    this.sn=this.data.user.sn;
  }

  setUsuarioLdap(){
    this.usuarioLdapDto.uid=this.uid=this.data.user.uid;
    this.usuarioLdapDto.cn=this.data.user.cn;
    this.usuarioLdapDto.correoelectronico=this.data.user.correoelectronico;
    this.usuarioLdapDto.nombre=this.nombre;
    this.usuarioLdapDto.sn=this.sn;
  }

  updateUsuario(){
    
    this.setUsuarioLdap();
    console.log(this.usuarioLdapDto);
  
    this.eventBus.cast('inicio-progress','hola');
    this._adminUsuarioService.updateUsuario(this.uid,this.usuarioLdapDto).subscribe(
      (response)=>{
		  console.log(response);
		  if(response.estado=='success'){
			    this._toastrService.success("Exito", "Usuario actualizada");
          this._toastrService.info('Espero un momento, por favor.','Actualizando...');
          this.eventBus.cast('actualizar-usuario','actualizar');
		  }
		  else{
			  this._toastrService.error("Esta usuario ya se encuentra en el sistema", "Error");
			  this.eventBus.cast('fin-progress','chao');
		  }
		  

      },
      (error)=>{

        this.eventBus.cast('cerrar-usuario-add','cerrar');
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
      });

  }







}
