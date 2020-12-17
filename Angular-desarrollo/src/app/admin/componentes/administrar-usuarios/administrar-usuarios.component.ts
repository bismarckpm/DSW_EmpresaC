import { AfterViewInit,Component, OnInit,ViewChild } from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table'
import {MatSort} from '@angular/material/sort';
import { MatDialog } from '@angular/material/dialog';
import { AdministrarUsuariosService } from '../../Servicios/administrar-usuarios/administrar-usuarios.service';
import { ToastrService } from 'ngx-toastr';
import { NgEventBus } from 'ng-event-bus';
import { MetaData } from 'ng-event-bus/lib/meta-data';
import { AnadirAdminAnalistaComponent } from './anadir/anadir-admin-analista/anadir-admin-analista.component';
import { AnadirClienteComponent } from './anadir/anadir-cliente/anadir-cliente.component';
import { EliminarUsuarioComponent } from './eliminar/eliminar-usuario/eliminar-usuario.component';
import { ModificarComponent } from './modificar/modificar/modificar.component';
import { ModificarClienteComponent } from './modificar/modificar-cliente/modificar-cliente.component';
@Component({
  selector: 'app-administrar-usuarios',
  templateUrl: './administrar-usuarios.component.html',
  styleUrls: ['./administrar-usuarios.component.css']
})
export class AdministrarUsuariosComponent implements OnInit,AfterViewInit {
  displayedColumns: string[] = ['uid', 'cn', 'tipo_usuario', 'nombre', 'sn','correoelectronico','estado','acciones'];
  dataSource = new MatTableDataSource<PeriodicElement>(ELEMENT_DATA);
  public dialogRef;

  constructor(public dialog: MatDialog,
    private _adminUsuarioService:AdministrarUsuariosService,
    private _toastrService: ToastrService,
    private eventBus: NgEventBus
  ) { }


  @ViewChild(MatPaginator) paginator: MatPaginator;
  ngOnInit(): void {
    this.init();

    this.eventBus.on('cerrar-usuario-add').subscribe((meta: MetaData) => {
      console.log(meta.data); // will receive 'started' only
      this.dialogRef.close();
    });

    this.eventBus.on('actualizar-usuario').subscribe((meta: MetaData) => {
      console.log(meta.data); // will receive 'started' only
      this.dialogRef.close();
      this.getAllUsuarios();
    });
  }

  init(){
    this._toastrService.info('Espero un momento, por favor.','Cargando...');
    this.eventBus.cast('inicio-progress','hola');
    this.getAllUsuarios();
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }


  getAllUsuarios(){
    //this.dataSource.data=ELEMENT_DATA;
    this._adminUsuarioService.getAllUsuarios().subscribe(
      (response)=>{
        console.log(response);
        this.dataSource.data=response.usuarios;
        this._toastrService.success("Exito", "Todas los usuarios");
        this.eventBus.cast('fin-progress','chao');
      },
      (error)=>{
        console.log(error);
        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        this.eventBus.cast('fin-progress','chao');
      });
  }

  openDialog(rol): void {
    this.dialogRef = this.dialog.open(AnadirAdminAnalistaComponent, {
      width: '500px',
      data:{rol:rol}
    });

    this.dialogRef .afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  openDialogCliente(): void {
    this.dialogRef = this.dialog.open(AnadirClienteComponent, {
      width: '500px'
    });

    this.dialogRef .afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  openDialogDelete(user){
    this.dialogRef = this.dialog.open(EliminarUsuarioComponent, {
      width: '500px',
      data:{user:user}
    });

    this.dialogRef .afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  openDialogModificar(user){
    if(user.tipo_usuario=='cliente'){
      this.dialogRef = this.dialog.open(ModificarClienteComponent, {
        width: '500px',
        data:{user:user}
      });
      this.dialogRef .afterClosed().subscribe(result => {
        console.log('The dialog was closed');
      });
    }
    else{
      this.dialogRef = this.dialog.open(ModificarComponent, {
        width: '500px',
        data:{user:user}
      });
      this.dialogRef .afterClosed().subscribe(result => {
        console.log('The dialog was closed');
      });
    }
  }
}

export interface PeriodicElement {
  uid: number;
  cn: string;
  tipo_usuario: string;
  nombre: string;
  sn:string;
  correoelectronico:string;
  estado:string;
  
}
const ELEMENT_DATA: PeriodicElement[] = [
  {uid: 1, cn:'admin1', tipo_usuario:'admin',nombre: 'Gabriel', sn:'Romero',correoelectronico: 'gabriel@gmail.com', estado: 'activo'},
  {uid: 2, cn:'admin1', tipo_usuario:'analista',nombre: 'Gabriel', sn:'Romero',correoelectronico: 'gabriel@gmail.com', estado: 'activo'},
  {uid: 3, cn:'admin1', tipo_usuario:'encuestado',nombre: 'Gabriel', sn:'Romero',correoelectronico: 'gabriel@gmail.com', estado: 'activo'},
  {uid: 4, cn:'admin1', tipo_usuario:'cliente',nombre: 'Gabriel', sn:'Romero',correoelectronico: 'gabriel@gmail.com', estado: 'activo'},
  
];
  
