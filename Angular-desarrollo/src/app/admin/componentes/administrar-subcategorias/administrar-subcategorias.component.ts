import { AfterViewInit,Component, OnInit,ViewChild } from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import {MatSort} from '@angular/material/sort';
import { ToastrService } from 'ngx-toastr';
import { NgEventBus } from 'ng-event-bus';
import { MatDialog } from '@angular/material/dialog';
import {  AdministrarSubcategoriasService } from "../../Servicios/administrar-subcategorias/administrar-subcategorias.service";
import { AnadirComponent } from './anadir/anadir.component';
import { MetaData } from 'ng-event-bus/lib/meta-data';
import { EliminarSubcategoriaComponent } from './eliminar-subcategoria/eliminar-subcategoria.component';
import { ModifSubcategoriaComponent } from '../../componentes/administrar-subcategorias/modif-subcategoria/modif-subcategoria.component';

import { LoginService } from "../../../comun/servicios/login/login.service";

export interface Subcategoria {
  id: number;
  nombre: string;
  categoria_id:number;
  categoria:string;
  estado: string;
  
}
const ELEMENT_DATA: Subcategoria[] = [
  {id: 1, nombre: 'Hydrogen',categoria_id:1, categoria:'Lacteos' , estado: 'H'},
];

@Component({
  selector: 'app-administrar-subcategorias',
  templateUrl: './administrar-subcategorias.component.html',
  styleUrls: ['./administrar-subcategorias.component.css']
})
export class AdministrarSubcategoriasComponent implements OnInit,AfterViewInit {
  public displayedColumns: string[] = ['id', 'nombre','categoria', 'estado', 'acciones'];
  //public dataSource = new MatTableDataSource<Subcategoria>(ELEMENT_DATA); //solo para probar
  public dataSource = new MatTableDataSource<Subcategoria>(); //solo para probar
  public dialogRef;
  @ViewChild(MatSort) sort: MatSort;
  constructor(public dialog: MatDialog,
    private _adminSubcategoriaService:AdministrarSubcategoriasService,
    private _toastrService: ToastrService,
    private eventBus: NgEventBus,
    private loginService:LoginService) { }

  @ViewChild(MatPaginator) paginator: MatPaginator;
  ngOnInit(): void {
    this.init();

    this.eventBus.on('cerrar-subcategoria-add').subscribe((meta: MetaData) => {
      console.log(meta.data); // will receive 'started' only
      this.dialogRef.close();
    });

    this.eventBus.on('actualizar-subcategoria').subscribe((meta: MetaData) => {
      console.log(meta.data); // will receive 'started' only
      this.dialogRef.close();
      this.getAllSubcategorias();
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
    this.getAllSubcategorias();
    this.eventBus.cast('inicio-progress','hola');
  }

  getAllSubcategorias(){
    //this.dataSource.data=ELEMENT_DATA;
    this._adminSubcategoriaService.getAllSubcategorias().subscribe(
      (response)=>{
        console.log(response);
        this.dataSource.data=response.subcategorias;
        this._toastrService.success("Exito", "Todas las subcategorias");
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

  openDialog() {
    
    this.dialogRef = this.dialog.open(AnadirComponent, {
      width: '500px',
    });
    
    this.dialogRef .afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }
  openDialogUpdate(subcategoria): void {
    this.dialogRef = this.dialog.open(ModifSubcategoriaComponent, {
      width: '500px',
      data:{subcategoria:subcategoria}
    });

    this.dialogRef .afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  openDialogDelete(subcategoria): void {
    this.dialogRef = this.dialog.open(EliminarSubcategoriaComponent, {
      width: '500px',
      data:{subcategoria:subcategoria}
    });

    this.dialogRef .afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  activarSubcategoria(subcategoria_id){
    this.eventBus.cast('inicio-progress','hola');
    this._adminSubcategoriaService.activarSubcategoria(subcategoria_id).subscribe(
      (response)=>{
        console.log(response);
        this._toastrService.success("Exito", "Subcategoria activada");
        this.getAllSubcategorias();
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

}