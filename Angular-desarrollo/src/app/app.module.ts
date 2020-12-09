  import { BrowserModule } from '@angular/platform-browser';
  import { NgModule } from '@angular/core';
  import { HttpClientModule } from '@angular/common/http';
  import { NgProgressModule } from 'ngx-progressbar';
  import { NgProgressHttpModule } from 'ngx-progressbar/http';
  import { AppRoutingModule } from './app-routing.module';
  import { AppComponent } from './app.component';
  import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
  import { ToastrModule } from 'ngx-toastr';
  import { NgEventBus } from 'ng-event-bus';


  /*----------Submodulos-----------*/

  import { MaterialModule } from './material.module';
  import { ComunModule } from './comun/comun.module';
  import { ClienteModule } from './cliente/cliente.module';
  import { AdminModule } from './admin/admin.module';
  import { EncuestadoModule } from './encuestado/encuestado.module';
  import { AnalistaModule } from './analista/analista.module';

  @NgModule({
    declarations: [
      AppComponent
    ],
    imports: [
      BrowserModule,
      AppRoutingModule,
      BrowserAnimationsModule,
      MaterialModule,
      ComunModule,
      ClienteModule,
      AdminModule,
      EncuestadoModule,
      AnalistaModule,
      HttpClientModule,
      NgProgressModule,
      NgProgressHttpModule,
      ToastrModule.forRoot({
        timeOut: 5000,
        positionClass: 'toast-top-center',
        progressBar:true,
        closeButton:true
      }),
    
    ],
    providers: [NgEventBus],
    bootstrap: [AppComponent]
  })
  export class AppModule { }
