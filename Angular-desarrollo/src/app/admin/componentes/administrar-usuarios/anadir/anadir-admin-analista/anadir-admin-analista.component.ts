import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NgEventBus } from 'ng-event-bus';
import { ToastrService } from 'ngx-toastr';
import { AdministrarUsuariosService } from 'src/app/admin/Servicios/administrar-usuarios/administrar-usuarios.service';
import { usuarioLdap } from 'src/app/Entidades/usuarioLDAP';

@Component({
  selector: 'app-anadir-admin-analista',
  templateUrl: './anadir-admin-analista.component.html',
  styleUrls: ['./anadir-admin-analista.component.css']
})
export class AnadirAdminAnalistaComponent implements OnInit {

  public usuarioLdapDto:usuarioLdap;
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
  ) { }

  ngOnInit(): void {
    this.init();
  }

  init(){
    this.usuarioLdapDto=new usuarioLdap();
    this.rol=this.data.rol;
  }

  

  addUsuario(){
    this.eventBus.cast('inicio-progress','chao');
    this.asignarValores();
    console.log(this.usuarioLdapDto);

    this._adminUsuarioService.addUsuarioAdminAnalista(this.usuarioLdapDto).subscribe(
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
        console.log(error);
        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        this.eventBus.cast('fin-progress','chao');
        this.eventBus.cast('cerrar-usuario-add','cerrar');
      });
  }

  asignarValores(){
    this.usuarioLdapDto.cn=this.cn;
    this.usuarioLdapDto.sn=this.sn;
    this.usuarioLdapDto.nombre=this.nombre;
    this.usuarioLdapDto.correoelectronico=this.correoelectronico;
    this.usuarioLdapDto.contrasena=this.contrasena;
    this.usuarioLdapDto.tipo_usuario=this.rol;
  }

}
