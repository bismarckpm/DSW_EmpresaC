import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NgEventBus } from 'ng-event-bus';
import { ToastrService } from 'ngx-toastr';
import { AdministrarUsuariosService } from 'src/app/admin/Servicios/administrar-usuarios/administrar-usuarios.service';
import { ClienteDto } from 'src/app/Entidades/clienteDto';
import { usuarioLdap } from 'src/app/Entidades/usuarioLDAP';

@Component({
  selector: 'app-modificar-cliente',
  templateUrl: './modificar-cliente.component.html',
  styleUrls: ['./modificar-cliente.component.css']
})
export class ModificarClienteComponent implements OnInit {

  public rif:any;
  public nombre_empresa:any;
  public razon_social:any;

  public uid:string;
  public cn:string;
  public sn:string;
  public correoelectronico:string;
  public nombre:string;
  public usuarioLdapDto:usuarioLdap;
  public clienteDto:ClienteDto;

  constructor(public dialogRef: MatDialogRef<ModificarClienteComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private _adminUsuarioService:AdministrarUsuariosService,
              private _toastrService: ToastrService,
              private eventBus: NgEventBus) { }

  ngOnInit(): void {
    this.usuarioLdapDto=new usuarioLdap();
    this.clienteDto=new ClienteDto();
    this.clienteDto.usuarioLdapDto=this.usuarioLdapDto;
    this.usuarioLdapDto.uid=this.uid=this.data.user.uid;
    this.getDataCliente();
    
  }

  setUsuarioForm(){
    this.cn=this.data.user.cn;
    this.correoelectronico=this.data.user.correoelectronico;
    this.nombre=this.data.user.nombre;
    this.sn=this.data.user.sn;
  }

  setClienteDto(){
    this.usuarioLdapDto.cn=this.data.user.cn;
    this.usuarioLdapDto.correoelectronico=this.data.user.correoelectronico;
    this.usuarioLdapDto.nombre=this.nombre;
    this.usuarioLdapDto.sn=this.sn;

    this.clienteDto.usuarioLdapDto=this.usuarioLdapDto;
    this.clienteDto.rif=this.rif;
    this.clienteDto.nombre_empresa=this.nombre_empresa;
    this.clienteDto.razon_social=this.razon_social;
  }

  updateCliente(){
    
    this.setClienteDto();
    console.log(this.clienteDto);
  
    this.eventBus.cast('inicio-progress','hola');
    this._adminUsuarioService.updateCliente(this.uid,this.clienteDto).subscribe(
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
        console.log(error);
        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        this.eventBus.cast('fin-progress','chao');
        this.eventBus.cast('cerrar-usuario-add','cerrar');
      });

  }

  getDataCliente(){
    this.eventBus.cast('inicio-progress','chao');
    this._adminUsuarioService.getclienteData(this.uid).subscribe(
      (response)=>{
		  console.log(response);
		  if(response.estado=='success'){
          this.rif=response.cliente.rif;
          this.nombre_empresa=response.cliente.nombre_empresa;
          this.razon_social=response.cliente.razon_social;
          this.setUsuarioForm();
          this.eventBus.cast('fin-progress','chao');
		  }
		  else{
			  this._toastrService.error("Ha ocurrido un error", "Error");
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
