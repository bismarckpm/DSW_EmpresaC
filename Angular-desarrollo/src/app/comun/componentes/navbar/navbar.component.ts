import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { NgEventBus } from 'ng-event-bus';

@Component({
  selector: 'comun-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  navBarData: FormGroup;
  constructor(private eventBus: NgEventBus,private route: Router) { }

  ngOnInit(): void {
  }


  cerrarSesion(){
    this.eventBus.cast('cerrar-sesion','chao');
  }

}
