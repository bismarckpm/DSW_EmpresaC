import { AfterViewInit,Component, OnInit,ViewChild } from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table'
import {MatSort} from '@angular/material/sort';
import { AdministrarCategoriasService } from '../../Servicios/administrar-categorias/administrar-categorias.service';
import { ToastrService } from 'ngx-toastr';
import { NgEventBus } from 'ng-event-bus';
import { AnadirModalComponent } from './anadir-categoria/anadir-modal.component';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { MetaData } from 'ng-event-bus/lib/meta-data';
import { ModificarCategoriaComponent } from './modificar-categoria/modificar-categoria.component';
import { EliminarCategoriaComponent } from './eliminar-categoria/eliminar-categoria.component';

import { LoginService } from "../../../comun/servicios/login/login.service";
import { Router } from '@angular/router';

export interface Categoria {
  id: number;
  nombre: string;
  estado: string;
  
}

const ELEMENT_DATA: Categoria[] = [
  {id: 1, nombre: 'Hydrogen',  estado: 'H'},
  {id: 2, nombre: 'Helium',  estado: 'He'},
  {id: 3, nombre: 'Lithium',  estado: 'Li'},
  {id: 4, nombre: 'Beryllium',  estado: 'Be'},
  {id: 5, nombre: 'Boron',  estado: 'B'},
  {id: 1, nombre: 'Hydrogen',  estado: 'H'},
  {id: 2, nombre: 'Helium',  estado: 'He'},
];

@Component({
  selector: 'app-administrar-categoria',
  templateUrl: './administrar-categoria.component.html',
  styleUrls: ['./administrar-categoria.component.css']
})
export class AdministrarCategoriaComponent implements OnInit, AfterViewInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;

  displayedColumns: string[] = ['id', 'nombre', 'estado', 'acciones'];
  //dataSource = new MatTableDataSource<Categoria>(ELEMENT_DATA); //Para pruebas sin backend
  dataSource = new MatTableDataSource<Categoria>();
  public dialogRef;
  @ViewChild(MatSort) sort: MatSort;

  constructor(public dialog: MatDialog,
              private _adminCategoriaService:AdministrarCategoriasService,
              private _toastrService: ToastrService,
              private eventBus: NgEventBus,
              private loginService:LoginService,
              private router:Router
  ) { }

  
  ngOnInit(): void {
    this.init();

    this.eventBus.on('cerrar-categoria-add').subscribe((meta: MetaData) => {
      console.log(meta.data); // will receive 'started' only
      this.dialogRef.close();
    });

    this.eventBus.on('actualizar-categoria').subscribe((meta: MetaData) => {
      console.log(meta.data); // will receive 'started' only
      this.dialogRef.close();
      this.getAllCategorias();
    });
    
    
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
    
    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  init(){
    this._toastrService.info('Espero un momento, por favor.','Cargando...');
    this.eventBus.cast('inicio-progress','hola');
    this.getAllCategorias();
    
  }

  getAllCategorias(){
    //this.dataSource.data=ELEMENT_DATA;
    this._adminCategoriaService.getAllCategorias().subscribe(
      (response)=>{
        console.log(response);
        this.dataSource.data=response.categorias;
        this._toastrService.success("Exito", "Todas las categorias");
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

  openDialog(): void {
    this.dialogRef = this.dialog.open(AnadirModalComponent, {
      width: '500px',
    });

    this.dialogRef .afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  openDialogUpdate(categoria): void {
    this.dialogRef = this.dialog.open(ModificarCategoriaComponent, {
      width: '500px',
      data:{categoria:categoria}
    });

    this.dialogRef .afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  openDialogDelete(categoria): void {
    this.dialogRef = this.dialog.open(EliminarCategoriaComponent, {
      width: '500px',
      data:{categoria:categoria}
    });

    this.dialogRef .afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  activarCategoria(categoria_id){
    this.eventBus.cast('inicio-progress','hola');
    this._adminCategoriaService.activarCategoria(categoria_id).subscribe(
      (response)=>{
        console.log(response);
        this._toastrService.success("Exito", "Categoria Activada");
        
        this.getAllCategorias();
      },
      (error)=>{
        console.log(error);
        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        this.eventBus.cast('fin-progress','chao');
      });
  }
}
  

