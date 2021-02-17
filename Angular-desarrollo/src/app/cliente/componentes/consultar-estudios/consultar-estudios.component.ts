import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { NgEventBus } from 'ng-event-bus';
import { MetaData } from 'ng-event-bus/lib/meta-data';
import { ToastrService } from 'ngx-toastr';
import { SolicitudEstudioService } from 'src/app/admin/Servicios/solicitud-estudio.service';
import { CaracteristicasComponent } from 'src/app/cliente/componentes/consultar-estudios/caracteristicas/caracteristicas.component';
import { GraficosComponent } from 'src/app/cliente/componentes/consultar-estudios/graficos/graficos.component';
import { IndividualComponent } from 'src/app/cliente/componentes/consultar-estudios/individual/individual.component';
import { MuestraComponent } from 'src/app/cliente/componentes/consultar-estudios/muestra/muestra.component';
import { ConsultarEstudiosService } from '../../servicios/consultar-estudios/consultar-estudios.service';
import { SolicitudEstudiosService } from '../../servicios/solicitud_estudios/solicitud-estudios.service';
import { ResultadosAnalistaComponent } from './resultados-analista/resultados-analista.component';

import { LoginService } from "../../../comun/servicios/login/login.service";

@Component({
  selector: 'app-consultar-estudios',
  templateUrl: './consultar-estudios.component.html',
  styleUrls: ['./consultar-estudios.component.css']
})
export class ConsultarEstudiosComponent implements OnInit {

  public cliente_id;
  public user_id;
  public estudios:any[];
  public estudios_filtered:any[];

  public dialogRef;

  constructor(public dialog: MatDialog,
              private _consultaEstudios:ConsultarEstudiosService,
              private _toastrService: ToastrService,
              private eventBus: NgEventBus,
              private _solicitudService:SolicitudEstudiosService,
              private loginService:LoginService) { }

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
        this.estudios_filtered = this.estudios;
        this._toastrService.success("Exito", "Todas los estudios solicitados");
        this.eventBus.cast('fin-progress','chao');
      },
      (error)=>{
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
        if(error.error.estado=="unauthorized"){
          this.eventBus.cast('fin-progress','chao');
          this._toastrService.error("Ops! Hubo un problema.", "La sesion expiro.");
          this.loginService.logOut().subscribe(x=>{window.location.reload()}, err=>{window.location.reload()});
  
        }
        else{
          console.log(error);
          this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        }
      });
  }
  
  openDialogCaracteriticas(estudio_caracteristicas){
    console.log(estudio_caracteristicas);
    this.dialogRef = this.dialog.open(CaracteristicasComponent, {
      width: '500px',
      //data:{caracteristicas: DATAC} para probar
      data:{caracteristicas: estudio_caracteristicas}
    });

    this.dialogRef .afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });

  }
  
  openDialogMuestra(estudio_muestra, estudio_id): void {
    console.log(estudio_muestra);
    this.dialogRef = this.dialog.open(MuestraComponent, {
      width: '600px',
      height:'400px',
      //data:{participaciones:MUESTRA}  para probar
      data:{participaciones:estudio_muestra,estudio:estudio_id},
     

    });

    this.dialogRef .afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }
  
  openDialogIndividual(estudio_id): void {
    console.log(estudio_id);
    this.dialogRef = this.dialog.open(IndividualComponent, {
      width: '600px',
      height:'400px',
      //data:{estudio:1}  //para probar
      data:{estudio:estudio_id}
    });

    this.dialogRef .afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  openDialogGraficos(estudio_id): void {
    console.log(estudio_id);
    this.dialogRef = this.dialog.open(GraficosComponent, {
      width: '700px',
      height:'700px',
      //data:{estudio:1}  //para probar
      data:{estudio:estudio_id},
    });

    this.dialogRef .afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  openDialogResultadosAnalista(estudio_id):void {
    console.log(estudio_id);
    this.dialogRef = this.dialog.open(ResultadosAnalistaComponent, {
      width: '400px',
      
      //data:{estudio:1}  //para probar
      data:{estudio:estudio_id},
    });

    this.dialogRef .afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }
  
  estudiosFiltered(estado){   
    if (estado == 'all') {
      this.estudios_filtered = this.estudios;
    }else{
      this.estudios_filtered = this.estudios.filter( estudio => estudio.estado === estado);
    }
  }
}
