import { AfterViewInit,Component, OnInit,ViewChild } from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table'
import {MatSort} from '@angular/material/sort';
import { ToastrService } from 'ngx-toastr';
import { NgEventBus } from 'ng-event-bus';
import { AnadirPresentacionComponent } from './anadir-presentacion/anadir-presentacion.component';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { MetaData } from 'ng-event-bus/lib/meta-data';
import { ModificarPresentacionComponent } from './modificar-presentacion/modificar-presentacion.component';
import { EliminarPresentacionComponent } from './eliminar-presentacion/eliminar-presentacion.component';
import { AdministrarPresentacionService } from '../../Servicios/administrar-presentacion/administrar-presentacion.service';


export interface Presentacion {
  id: number;
  nombre: string;
  tipo:string;
  tipo_id:number;
  estado: string;
  
}

const ELEMENT_DATA: Presentacion[] = [
  {id: 1, nombre: 'Hydrogen',  tipo:"a", tipo_id:3,estado: 'H'},
  {id: 2, nombre: 'Helium',  tipo:"b", tipo_id:3,estado: 'He'},
  {id: 3, nombre: 'Lithium', tipo:"z", tipo_id:3, estado: 'Li'},
  {id: 4, nombre: 'Beryllium', tipo:"s", tipo_id:3, estado: 'Be'},
  {id: 5, nombre: 'Boron',  tipo:"e", tipo_id:3,estado: 'B'},
  {id: 1, nombre: 'Hydrogen', tipo:"a", tipo_id:3, estado: 'H'},
  {id: 2, nombre: 'Helium', tipo:"g", tipo_id:3, estado: 'He'}
];

@Component({
  selector: 'app-administrar-presentaciones',
  templateUrl: './administrar-presentaciones.component.html',
  styleUrls: ['./administrar-presentaciones.component.css']
})
export class AdministrarPresentacionesComponent implements OnInit, AfterViewInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;

  displayedColumns: string[] = ['id', 'nombre', 'tipo','estado', 'acciones'];
  //dataSource = new MatTableDataSource<Presentacion>(ELEMENT_DATA); //Para pruebas sin backend
  dataSource = new MatTableDataSource<Presentacion>();
  public dialogRef;

  constructor(public dialog: MatDialog,
              private _adminPresentacionService:AdministrarPresentacionService,
              private _toastrService: ToastrService,
              private eventBus: NgEventBus
  ) { }

  
  ngOnInit(): void {
    this.init();

    this.eventBus.on('cerrar-presentacion-add').subscribe((meta: MetaData) => {
      console.log(meta.data); // will receive 'started' only
      this.dialogRef.close();
    });

    this.eventBus.on('actualizar-presentacion').subscribe((meta: MetaData) => {
      console.log(meta.data); // will receive 'started' only
      this.dialogRef.close();
      this.getAllPresentaciones();
    });
    
    
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
    this.dataSource.filter = filterValue.trim().toLowerCase()
    
  }

  init(){
    this._toastrService.info('Espero un momento, por favor.','Cargando...');
    this.eventBus.cast('inicio-progress','hola');
    this.getAllPresentaciones();
    
  }

  getAllPresentaciones(){
    //this.dataSource.data=ELEMENT_DATA;
    this._adminPresentacionService.getAllPresentaciones().subscribe(
      (response)=>{
        console.log(response);
        this.dataSource.data=response.presentaciones;
        this._toastrService.success("Exito", "Todas las presentaciones.");
        this.eventBus.cast('fin-progress','chao');
      },
      (error)=>{
        console.log(error);
        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        this.eventBus.cast('fin-progress','chao');
      });
  }

  openDialog(): void {
    this.dialogRef = this.dialog.open(AnadirPresentacionComponent, {
      width: '500px',
    });

    this.dialogRef .afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  openDialogUpdate(presentacion): void {
    this.dialogRef = this.dialog.open(ModificarPresentacionComponent, {
      width: '500px',
      data:{presentacion:presentacion}
    });

    this.dialogRef .afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  openDialogDelete(presentacion): void {
    this.dialogRef = this.dialog.open(EliminarPresentacionComponent, {
      width: '500px',
      data:{presentacion:presentacion}
    });

    this.dialogRef .afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  activarPresentacion(presentacion_id){
    this.eventBus.cast('inicio-progress','hola');
    this._adminPresentacionService.activarPresentacion(presentacion_id).subscribe(
      (response)=>{
        console.log(response);
        this._toastrService.success("Exito", "Presentacion Activada");
        this.getAllPresentaciones();
      },
      (error)=>{
        console.log(error);
        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        this.eventBus.cast('fin-progress','chao');
      });
  }
}
  

