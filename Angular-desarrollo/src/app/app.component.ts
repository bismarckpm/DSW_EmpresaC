import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MetaData } from 'ng-event-bus/lib/meta-data';
import { NgProgress } from 'ngx-progressbar';
import {NgProgressRef} from 'ngx-progressbar';
import { ToastrService } from 'ngx-toastr';
import { NgEventBus } from 'ng-event-bus';
import { Router } from '@angular/router';



@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{

  title = 'Angular-desarrollo';
  progressRef: NgProgressRef;

  public token:any;
  public rol:any;
  public user_id:any;

  constructor(private route: Router,private progress: NgProgress, private _http:HttpClient, private _toastrService: ToastrService,private eventBus: NgEventBus) {
    this.progressRef = this.progress.ref('myProgress');
  }
  
  ngOnInit() {

    this.dataLogin();
    this.checkLocalStorage();

    this.eventBus.on('inicio-progress').subscribe((meta: MetaData) => {
      console.log(meta.data); // will receive 'started' only
      this.startLoading();
    });

    this.eventBus.on('fin-progress').subscribe((meta: MetaData) => {
      console.log(meta.data); // will receive 'started' only
      this.completeLoading();
    });

    this.eventBus.on('inicio-sesion').subscribe((meta: MetaData) => {
      console.log(meta.data); // will receive 'started' only
      this.checkLocalStorage();
      this.checkDashboardRol();
    });

    this.eventBus.on('cerrar-sesion').subscribe((meta: MetaData) => {
      console.log(meta.data); // will receive 'started' only
      this.cleanLocalstorage();
    });


  }

  startLoading() {
    this.progressRef.start();
  }

  completeLoading() {
    this.progressRef.complete();
  }

  checkLocalStorage(){
    this.token=localStorage.getItem('token');
    this.rol=localStorage.getItem('rol');
    this.user_id=+localStorage.getItem('user_id');
  }

  checkDashboardRol(){
      if(this.rol=='admin'){
        this.route.navigate(['/admin']);
      }else if(this.rol=='analista'){
        this.route.navigate(['/analista']);
      }
      else if(this.rol=='cliente'){
        this.route.navigate(['/cliente']);
      }
      else if(this.rol=='encuestado'){
        this.route.navigate(['/encuestado']);
      }
  }

  cleanLocalstorage(){
    localStorage.clear();
    this.checkLocalStorage();
    this.route.navigate(['/login']);
  }

  dataLogin(){
       //Quitar;
       localStorage.setItem("user_id", '1' );
       localStorage.setItem("rol", 'analista' );
       localStorage.setItem("token", '111111111' );
  }

}
