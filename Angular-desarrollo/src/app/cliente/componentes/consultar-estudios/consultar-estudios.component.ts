import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { NgEventBus } from 'ng-event-bus';
import { MetaData } from 'ng-event-bus/lib/meta-data';
import { ToastrService } from 'ngx-toastr';
import { SolicitudEstudioService } from 'src/app/admin/Servicios/solicitud-estudio.service';
import { ConsultarEstudiosService } from '../../servicios/consultar-estudios/consultar-estudios.service';
import { SolicitudEstudiosService } from '../../servicios/solicitud_estudios/solicitud-estudios.service';

@Component({
  selector: 'app-consultar-estudios',
  templateUrl: './consultar-estudios.component.html',
  styleUrls: ['./consultar-estudios.component.css']
})
export class ConsultarEstudiosComponent implements OnInit {

  public cliente_id;
  public user_id;
  public estudios:any[];

  constructor(public dialog: MatDialog,
              private _consultaEstudios:ConsultarEstudiosService,
              private _toastrService: ToastrService,
              private eventBus: NgEventBus,
              private _solicitudService:SolicitudEstudiosService) { }

  ngOnInit(): void {
    this.init();

    this.eventBus.on('actualizar-estudios-solicitados').subscribe((meta: MetaData) => {
      console.log(meta.data); // will receive 'started' only
      this.getClienteId();
    });
  }

  init(){
    this.eventBus.cast('inicio-progress','hola');
    this.user_id=+localStorage.getItem('user_id');
    this.getClienteId();
  }

  getAllEstudios(){
    this._consultaEstudios.getAllEstudios(this.cliente_id).subscribe(
      (response)=>{
        console.log(response);
        this.estudios=response.estudios;
        this._toastrService.success("Exito", "Todas los estudios solicitados");
        this.eventBus.cast('fin-progress','chao');
      },
      (error)=>{
        console.log(error);
        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        this.eventBus.cast('fin-progress','chao');
      });
  }

  getClienteId(){
    this.user_id=+localStorage.getItem('user_id');
    this._solicitudService.getClienteIdByUsuario(this.user_id).subscribe(
      (response)=>{
        console.log(response);
        this.cliente_id=response.cliente_id;
        console.log('Cliente: '+this.cliente_id);
        this.getAllEstudios();
      },
      (error)=>{
        console.log(error);
        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
      });
  }
  

}
