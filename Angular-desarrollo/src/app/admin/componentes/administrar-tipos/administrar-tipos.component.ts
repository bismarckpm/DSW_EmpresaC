import { AfterViewInit,Component, OnInit,ViewChild } from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table'
import {MatSort} from '@angular/material/sort';
import { AdministrarTiposService } from '../../Servicios/administrar-tipos/administrar-tipos.service';
import { ToastrService } from 'ngx-toastr';
import { NgEventBus } from 'ng-event-bus';
import { AnadirTipoComponent } from './anadir-tipo/anadir-tipo.component';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { MetaData } from 'ng-event-bus/lib/meta-data';
import { ModificarTipoComponent} from './modificar-tipo/modificar-tipo.component';
import { EliminarTipoComponent } from './eliminar-tipo/eliminar-tipo.component';


export interface Tipo {
  id: number;
  nombre: string;
  estado: string;
  
}

const ELEMENT_DATA: Tipo[] = [
  {id: 1, nombre: 'Hydrogen',  estado: 'H'},
  {id: 2, nombre: 'Helium',  estado: 'He'},
  {id: 3, nombre: 'Lithium',  estado: 'Li'},
  {id: 4, nombre: 'Beryllium',  estado: 'Be'},
  {id: 5, nombre: 'Boron',  estado: 'B'},
  {id: 1, nombre: 'Hydrogen',  estado: 'H'},
  {id: 2, nombre: 'Helium',  estado: 'He'},
];

@Component({
  selector: 'app-administrar-tipos',
  templateUrl: './administrar-tipos.component.html',
  styleUrls: ['./administrar-tipos.component.css']
})
export class AdministrarTiposComponent implements OnInit, AfterViewInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;

  displayedColumns: string[] = ['id', 'nombre', 'estado', 'acciones'];
  //dataSource = new MatTableDataSource<Tipo>(ELEMENT_DATA); //Para pruebas sin backend
  dataSource = new MatTableDataSource<Tipo>();
  public dialogRef;

  constructor(public dialog: MatDialog,
              private _adminTiposService: AdministrarTiposService,
              private _toastrService: ToastrService,
              private eventBus: NgEventBus
  ) { }

  
  ngOnInit(): void {
    this.init();

    this.eventBus.on('cerrar-tipo-add').subscribe((meta: MetaData) => {
      console.log(meta.data); // will receive 'started' only
      this.dialogRef.close();
    });

    this.eventBus.on('actualizar-tipo').subscribe((meta: MetaData) => {
      console.log(meta.data); // will receive 'started' only
      this.dialogRef.close();
      this.getAllTipos();
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
    this.getAllTipos();
    
  }

  getAllTipos(){
    //this.dataSource.data=ELEMENT_DATA;
    this._adminTiposService.getAllTipos().subscribe(
      (response)=>{
        console.log(response);
        this.dataSource.data=response.tipos;
        this._toastrService.success("Exito", "Todas las tipos");
        this.eventBus.cast('fin-progress','chao');
      },
      (error)=>{
        console.log(error);
        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        this.eventBus.cast('fin-progress','chao');
      });
  }

  openDialog(): void {
    this.dialogRef = this.dialog.open(AnadirTipoComponent, {
      width: '500px',
    });

    this.dialogRef .afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  openDialogUpdate(tipo): void {
    this.dialogRef = this.dialog.open(ModificarTipoComponent, {
      width: '500px',
      data:{tipo:tipo}
    });

    this.dialogRef .afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  openDialogDelete(tipo): void {
    this.dialogRef = this.dialog.open(EliminarTipoComponent, {
      width: '500px',
      data:{tipo:tipo}
    });

    this.dialogRef .afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  activarTipo(tipo_id){
    this.eventBus.cast('inicio-progress','hola');
    this._adminTiposService.activarTipo(tipo_id).subscribe(
      (response)=>{
        console.log(response);
        this._toastrService.success("Exito", "Tipo Activada");
        this.getAllTipos();
      },
      (error)=>{
        console.log(error);
        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        this.eventBus.cast('fin-progress','chao');
      });
  }

}
  

