import { Component, OnInit } from '@angular/core';
import {MatCardModule} from '@angular/material/card';
import { NgEventBus } from 'ng-event-bus';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { MetaData } from 'ng-event-bus/lib/meta-data';
import { ToastrService } from 'ngx-toastr';
import { ConsultaEstudiosService } from '../../servicios/consulta-estudios/consulta-estudios.service';
import { MuestraComponent } from '../../componentes/estudios-asignados/muestra/muestra.component';
import { CaracteristicasComponent } from '../../componentes/estudios-asignados/caracteristicas/caracteristicas.component';
import { ResultadosComponent } from '../../componentes/estudios-asignados/resultados/resultados.component';
import { ResponderComponent } from '../../componentes/estudios-asignados/responder/responder.component';

@Component({
  selector: 'app-estudios-asignados',
  templateUrl: './estudios-asignados.component.html',
  styleUrls: ['./estudios-asignados.component.css'],
  providers:[ConsultaEstudiosService]
})
export class EstudiosAsignadosComponent implements OnInit {

  public analista_id:any;
  public estudios:any[];
  public dialogRef;

  constructor(public dialog: MatDialog,private _consultaEstudios:ConsultaEstudiosService,private _toastrService: ToastrService,private eventBus: NgEventBus) { }

  ngOnInit(): void {
    this.init();

    this.eventBus.on('actualizar').subscribe((meta: MetaData) => {
      console.log(meta.data); // will receive 'started' only
      this.dialogRef.close();
      this.getAllEstudios();
    });
  }

  init(){
    this.eventBus.cast('inicio-progress','hola');
    this.analista_id=localStorage.getItem('user_id');
    this.getAllEstudios();
  }

  getAllEstudios(){
    this._consultaEstudios.getAllEstudios(this.analista_id).subscribe(
      (response)=>{
        console.log(response);
        this.estudios=response.estudios;
        this._toastrService.success("Exito", "Todas los estudios asignados");
        this.eventBus.cast('fin-progress','chao');
        
      },
      (error)=>{
        console.log(error);
        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        this.eventBus.cast('fin-progress','chao');
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
  openDialogResultados(estudio_id){
    console.log(estudio_id);
    this.dialogRef = this.dialog.open(ResultadosComponent, {
      width: '500px',
      //data:{id: ESTUDIO.id} para probar
      data:{id:estudio_id}
      
    });

    this.dialogRef .afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  openDialogResponder(estudio_muestra,estudio_id){
    console.log(estudio_muestra);
    console.log(estudio_id);
    this.dialogRef = this.dialog.open(ResponderComponent, {
      width: '600px',
      height:'400px',
      //data:{participaciones:MUESTRA, estudio_id:1} para probar
      data:{participaciones:estudio_muestra, id:estudio_id}
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

  go(id){

  }

  stop(id){

  }

}


const DATAC={
  edad_min:30,
  edad_max:45,
  nivel_socioeconomico:"Bajo",
  nacionalidad:"Extranjero",
  cantidad_hijos:1,
  genero:"F",
  parroquia:"La Candelaria",
  ciudad: "Caracas",
  estado:"Distrito Capital",
  pais:"Venezuela",
  nivel_academico:"Bachiller"
}

const ESTUDIO={
  id:1,
}

const MUESTRA= [{
  participacion_id:1,
  doc_id:12345,
  usuario:"usuario1",
  correo:"uncorreo@gmail.com",
  nombre:"Gabriel",
  apellido:"Romero",
  estado:"activo"
},{
  participacion_id:1,
  doc_id:12345,
  usuario:"usuario1",
  correo:"uncorreo@gmail.com",
  nombre:"Gabriel",
  apellido:"Romero",
  estado:"activo"
},{
  participacion_id:1,
  doc_id:12345,
  usuario:"usuario1",
  correo:"uncorreo@gmail.com",
  nombre:"Gabriel",
  apellido:"Romero",
  estado:"activo"
},
{
  participacion_id:1,
  doc_id:12345,
  usuario:"usuario1",
  correo:"uncorreo@gmail.com",
  nombre:"Gabriel",
  apellido:"Romero",
  estado:"activo"
},{
  participacion_id:1,
  doc_id:12345,
  usuario:"usuario1",
  correo:"uncorreo@gmail.com",
  nombre:"Gabriel",
  apellido:"Romero",
  estado:"activo"
},{
  participacion_id:1,
  doc_id:12345,
  usuario:"usuario1",
  correo:"uncorreo@gmail.com",
  nombre:"Gabriel",
  apellido:"Romero",
  estado:"activo"
},{
  participacion_id:1,
  doc_id:12345,
  usuario:"usuario1",
  correo:"uncorreo@gmail.com",
  nombre:"Gabriel",
  apellido:"Romero",
  estado:"activo"
},{
  participacion_id:1,
  doc_id:12345,
  usuario:"usuario1",
  correo:"uncorreo@gmail.com",
  nombre:"Gabriel",
  apellido:"Romero",
  estado:"activo"
},
{
  participacion_id:1,
  doc_id:12345,
  usuario:"usuario1",
  correo:"uncorreo@gmail.com",
  nombre:"Gabriel",
  apellido:"Romero",
  estado:"activo"
},{
  participacion_id:1,
  doc_id:12345,
  usuario:"usuario1",
  correo:"uncorreo@gmail.com",
  nombre:"Gabriel",
  apellido:"Romero",
  estado:"activo"
}];
