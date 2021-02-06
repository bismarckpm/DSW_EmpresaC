import { Component, DoCheck, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { NgEventBus } from 'ng-event-bus';
import { MetaData } from 'ng-event-bus/lib/meta-data';
import { ModalPasswordComponent } from './modal-password/modal-password.component';
import { MatDialog } from '@angular/material/dialog';
import { LoginService } from '../../servicios/login/login.service';

@Component({
  selector: 'comun-aside',
  templateUrl: './aside.component.html',
  styleUrls: ['./aside.component.css']
})
export class AsideComponent implements OnInit {

  rol:any;
  flag=true;
  public dialogRef;
  

  constructor(private _toastrService: ToastrService,
              private eventBus: NgEventBus,
              public dialog: MatDialog,
              private loginservice: LoginService
  
  ) { }

  ngOnInit(): void {
    this.checkLocalStorage();
    this.loginservice.verificartoken().subscribe(x=>{
      console.log(x)
    })

    this.eventBus.on('cerrar-modal-password').subscribe((meta: MetaData) => {
      console.log(meta.data); // will receive 'started' only
      this.dialogRef.close();
    });

  }

  checkLocalStorage(){
    this.rol=localStorage.getItem('rol');
    console.log(this.rol);
  }

  refresh(){
    this.checkLocalStorage();
    this.flag=false;
  }

  changePassword(): void {
    this.dialogRef = this.dialog.open(ModalPasswordComponent, {
      width: '500px',
    });

    this.dialogRef .afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

}
