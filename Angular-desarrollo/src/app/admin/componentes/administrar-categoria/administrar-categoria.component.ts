import { AfterViewInit,Component, OnInit,ViewChild } from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table'
import {MatSort} from '@angular/material/sort';
import { AdministrarCategoriasService } from '../../Servicios/administrar-categorias/administrar-categorias.service';
import { ToastrService } from 'ngx-toastr';
import { NgEventBus } from 'ng-event-bus';
import { AnadirModalComponent } from './anadir/anadir-modal/anadir-modal.component';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { MetaData } from 'ng-event-bus/lib/meta-data';

export interface PeriodicElement {
  id: number;
  name: string;
  estado: string;
  
  
};

const CATEGORIAS: PeriodicElement[] = [
  {id:1, name: 'Hydrogen',  estado: 'Activo'},
  {id:2, name: 'Helium',  estado: 'inactivo',},
  {id:3, name: 'Lithium',  estado: 'inactivo'},
  {id:4, name: 'Beryllium',  estado: 'activo'},
  {id:5, name: 'Boron',  estado: 'activo'},
 
];

@Component({
  selector: 'app-administrar-categoria',
  templateUrl: './administrar-categoria.component.html',
  styleUrls: ['./administrar-categoria.component.css']
})
export class AdministrarCategoriaComponent implements OnInit {
  dataSource = new MatTableDataSource<PeriodicElement>(CATEGORIAS);
  @ViewChild(MatPaginator) paginator: MatPaginator;
  
  public categorias:any[];
  public dialogRef;

  constructor(public dialog: MatDialog,private _adminCategoriaService:AdministrarCategoriasService,private _toastrService: ToastrService,private eventBus: NgEventBus) { }

  ngOnInit(): void {
    this.init();

    this.eventBus.on('cerrar-categoria-add').subscribe((meta: MetaData) => {
      console.log(meta.data); // will receive 'started' only
      this.dialogRef.close();
    });

    this.eventBus.on('actualizar').subscribe((meta: MetaData) => {
      console.log(meta.data); // will receive 'started' only
      this.dialogRef.close();
      this.getAllCategorias();
    });
    
    
  }
  displayedColumns: string[] = ['id','name', 'estado'];
  //dataSource = CATEGORIAS;
 
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
    this.getAllCategorias();
    
  }

  getAllCategorias(){
    this._adminCategoriaService.getAllCategorias().subscribe(
      (response)=>{
        console.log(response);
        this.categorias=response.categorias;
        this._toastrService.success("Exito", "Todas las categorias");
        this.eventBus.cast('fin-progress','chao');
      },
      (error)=>{
        console.log(error);
        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        this.eventBus.cast('fin-progress','chao');
      });
  }

  openDialog(): void {
    this.dialogRef = this.dialog.open(AnadirModalComponent, {
      width: '500px',
    });

    this.dialogRef .afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  pruebaa(){
    console.log('Sirve');
  }
}
