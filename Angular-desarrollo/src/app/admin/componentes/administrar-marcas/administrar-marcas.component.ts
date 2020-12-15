import {  AdminMarcasService } from "../../Servicios/administrar-marcas/admin-marcas.service";
import { ToastrService } from 'ngx-toastr';
import { NgEventBus } from 'ng-event-bus';
import { AfterViewInit,Component, OnInit,ViewChild } from '@angular/core';
import {MatTableDataSource} from '@angular/material/table'
import {MatSort} from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { AnadirMarcaComponent } from './anadir-marca/anadir.component';
import { MatDialog } from '@angular/material/dialog';
import { ModificarMarcaComponent } from './modificar-marca/modificar-marca.component';
import { EliminarMarcaComponent } from './eliminar-marca/eliminar-marca.component';
import { MetaData } from 'ng-event-bus/lib/meta-data';


export interface Marca {
  id: number;
  nombre: string;
  subcategoria_id:number;
  subcategoria: string;
  estado: string;
  
}

const ELEMENT_DATA: Marca[] = [
  {id: 1, nombre: 'Hydrogen',subcategoria_id:1, subcategoria:'Lacteos', estado: 'H'},
];


@Component({
  selector: 'app-administrar-marcas',
  templateUrl: './administrar-marcas.component.html',
  styleUrls: ['./administrar-marcas.component.css'],
  providers:[AdminMarcasService]
})
export class AdministrarMarcasComponent implements OnInit, AfterViewInit{
  
  @ViewChild(MatPaginator) paginator: MatPaginator;
  
  public displayedColumns: string[] = ['id', 'nombre', 'subcategoria', 'estado', 'acciones'];
  //public dataSource = new MatTableDataSource<Marca>(ELEMENT_DATA); //solo para probar sin backend
  public dataSource = new MatTableDataSource<Marca>();
  public dialogRef;
  
  

  constructor(public dialog: MatDialog,private _adminMarcas:AdminMarcasService,private _toastrService: ToastrService,private eventBus: NgEventBus) { }
 
  ngOnInit(): void {
    this.init();

    this.eventBus.on('cerrar-marca-add').subscribe((meta: MetaData) => {
      console.log(meta.data); // will receive 'started' only
      this.dialogRef.close();
    });

    this.eventBus.on('actualizar-marca').subscribe((meta: MetaData) => {
      console.log(meta.data); // will receive 'started' only
      this.dialogRef.close();
      this.getAllMarcas();
    });
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  init(){
    this._toastrService.info('Espero un momento, por favor.','Cargando...');
    this.getAllMarcas();
    this.eventBus.cast('inicio-progress','hola');
  }
  
  getAllMarcas(){
    //this.dataSource.data=ELEMENT_DATA;
    this._adminMarcas.getAllMarcas().subscribe(
      (response)=>{
        console.log(response);
        this.dataSource.data=response.marcas;
        this._toastrService.success("Exito", "Todas las marcas");
        this.eventBus.cast('fin-progress','chao');
      },
      (error)=>{
        console.log(error);
        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        this.eventBus.cast('fin-progress','chao');
      });
  }

   openDialog() {
     this.dialogRef = this.dialog.open(AnadirMarcaComponent, {
      width: '500px',
    });
    
    this.dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
    
  }

  openDialogUpdate(marca): void {
    this.dialogRef = this.dialog.open(ModificarMarcaComponent, {
      width: '500px',
      data:{marca:marca}
    });

    this.dialogRef .afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  openDialogDelete(marca): void {
    this.dialogRef = this.dialog.open(EliminarMarcaComponent, {
      width: '500px',
      data:{marca:marca}
    });

    this.dialogRef .afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }
}


  

