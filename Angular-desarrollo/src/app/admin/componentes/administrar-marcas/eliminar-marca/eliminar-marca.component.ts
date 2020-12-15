import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NgEventBus } from 'ng-event-bus';
import { ToastrService } from 'ngx-toastr';
import { AdminMarcasService } from 'src/app/admin/Servicios/administrar-marcas/admin-marcas.service';

@Component({
  selector: 'app-eliminar-marca',
  templateUrl: './eliminar-marca.component.html',
  styleUrls: ['./eliminar-marca.component.css']
})
export class EliminarMarcaComponent implements OnInit {


  public marca_id:any;
  constructor(
              public dialogRef: MatDialogRef<EliminarMarcaComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private _adminMarcasService: AdminMarcasService,
              private _toastrService: ToastrService,
              private eventBus: NgEventBus
    ) {}

  ngOnInit(): void {
    this.init();
  }

  init(){
    this.marca_id=this.data.marca.id;
  }

  deleteMarca(){

  }



}
