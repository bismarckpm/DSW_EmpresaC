import { Component, OnInit } from '@angular/core';
import { NgEventBus } from 'ng-event-bus';
import { ToastrService } from 'ngx-toastr';
import { AdministrarUsuariosService } from 'src/app/admin/Servicios/administrar-usuarios/administrar-usuarios.service';
import { ClienteDto } from 'src/app/Entidades/clienteDto';
import { usuarioLdap } from 'src/app/Entidades/usuarioLDAP';

import { LoginService } from "../../../../../comun/servicios/login/login.service";

@Component({
  selector: 'app-anadir-cliente',
  templateUrl: './anadir-cliente.component.html',
  styleUrls: ['./anadir-cliente.component.css']
})
export class AnadirClienteComponent implements OnInit {

  public usuarioLdapDto:usuarioLdap;
  public clienteDto:ClienteDto;
  public cn: any;
  public sn: any; 
  public nombre: any ;
  public correoelectronico: any;
  public contrasena: any;

  public nombre_empresa:any;
  public razon_social:any;
  public rif:any;

  constructor(private _adminUsuarioService:AdministrarUsuariosService,
              private _toastrService: ToastrService,
              private eventBus: NgEventBus,
              private loginService:LoginService
  ) { }


  ngOnInit(): void {
    this.init();
  }

  init(){
    this.usuarioLdapDto=new usuarioLdap();
    this.clienteDto=new ClienteDto();
    this.clienteDto.usuarioLdapDto= this.usuarioLdapDto;
  }

  addUsuarioCliente(){
    this.eventBus.cast('inicio-progress','chao');
    this.asignarValores();
    console.log(this.clienteDto);

    this._adminUsuarioService.addUsuarioCliente(this.clienteDto).subscribe(
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
          this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
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
    this.usuarioLdapDto.tipo_usuario='cliente';

    this.clienteDto.usuarioLdapDto=this.usuarioLdapDto;
    this.clienteDto.rif=this.rif;
    this.clienteDto.nombre_empresa=this.nombre_empresa;
    this.clienteDto.razon_social=this.razon_social;
  }

}
