import { Component, DoCheck, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { NgEventBus } from 'ng-event-bus';
import { MetaData } from 'ng-event-bus/lib/meta-data';

@Component({
  selector: 'comun-aside',
  templateUrl: './aside.component.html',
  styleUrls: ['./aside.component.css']
})
export class AsideComponent implements OnInit {

  rol:any;
  flag=true;
  

  constructor(private _toastrService: ToastrService,private eventBus: NgEventBus) { }

  ngOnInit(): void {
    this.checkLocalStorage();
  }

  checkLocalStorage(){
    this.rol=localStorage.getItem('rol');
    console.log(this.rol);
  }

  refresh(){
    this.checkLocalStorage();
    this.flag=false;
  }

}
