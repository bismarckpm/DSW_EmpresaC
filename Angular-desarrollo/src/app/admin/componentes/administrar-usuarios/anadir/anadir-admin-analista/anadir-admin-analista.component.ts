import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NgEventBus } from 'ng-event-bus';
import { ToastrService } from 'ngx-toastr';
import { AdministrarUsuariosService } from 'src/app/admin/Servicios/administrar-usuarios/administrar-usuarios.service';
import { NuevoUsuarioDto } from 'src/app/Entidades/nuevoUsuarioDto';
import { usuario } from 'src/app/Entidades/usuario';
import { usuarioLdap } from 'src/app/Entidades/usuarioLDAP';

import { LoginService } from "../../../../../comun/servicios/login/login.service";

@Component({
  selector: 'app-anadir-admin-analista',
  templateUrl: './anadir-admin-analista.component.html',
  styleUrls: ['./anadir-admin-analista.component.css']
})
export class AnadirAdminAnalistaComponent implements OnInit {

  public nuevoUsuarioDto: NuevoUsuarioDto;
  public usuarioLdapDto: usuarioLdap;
  public usuarioDto:usuario;
  public cn: any;
  public sn: any; 
  public nombre: any ;
  public correoelectronico: any;
  public contrasena: any;
  public rol:any;
  constructor(public dialogRef: MatDialogRef<AnadirAdminAnalistaComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private _adminUsuarioService:AdministrarUsuariosService,
              private _toastrService: ToastrService,
              private eventBus: NgEventBus,
              private loginService:LoginService
  ) { }

  ngOnInit(): void {
    this.init();
  }

  init(){
    this.usuarioLdapDto=new usuarioLdap();
    this.usuarioDto= new usuario();
    this.nuevoUsuarioDto=new NuevoUsuarioDto();
    this.nuevoUsuarioDto.usuarioLdapDto=this.usuarioLdapDto;
    this.nuevoUsuarioDto.usuarioDto=this.usuarioDto;
    this.rol=this.data.rol;
  }

  

  addUsuario(){
    this.eventBus.cast('inicio-progress','chao');
    this.asignarValores();
    console.log(this.nuevoUsuarioDto);

    this._adminUsuarioService.addUsuarioAdminAnalista(this.nuevoUsuarioDto).subscribe(
      (response)=>{
		  console.log(response);
		  if(response.estado=='success'){
			    this._toastrService.success("Exito", "Usuario añadido");
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
          if(error.error.mensaje){
            this._toastrService.error("Ops! Hubo un problema.", error.error.mensaje)
          }
          else{
            this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
          }
          this.eventBus.cast('fin-progress','chao');
        }
      });
  }

  asignarValores(){
    this.usuarioLdapDto.cn=this.cn;
    this.usuarioLdapDto.sn=this.sn;
    this.usuarioLdapDto.nombre=this.nombre;
    this.usuarioLdapDto.correoelectronico=this.correoelectronico;
    this.usuarioLdapDto.contrasena=this.contrasena;
    this.usuarioLdapDto.tipo_usuario=this.rol;
    this.usuarioDto.usuario=this.cn;

    this.nuevoUsuarioDto.usuarioLdapDto=this.usuarioLdapDto;
    this.nuevoUsuarioDto.usuarioDto=this.usuarioDto;
  }

}
