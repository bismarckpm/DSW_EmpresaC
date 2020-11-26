import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
;
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MaterialModule} from './material.module';
import { NavbarComponent } from './navbar/navbar.component';
import { ClienteComponent } from './cliente/cliente.component';
import { AnalistaComponent } from './analista/analista.component';
import { EncuestadoComponent } from './encuestado/encuestado.component'

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    ClienteComponent,
    AnalistaComponent,
    EncuestadoComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule
    
   
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
