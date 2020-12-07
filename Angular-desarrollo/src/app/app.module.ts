import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { NgProgressModule } from 'ngx-progressbar';
import { NgProgressHttpModule } from 'ngx-progressbar/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material.module';
import { ComunModule } from './comun/comun.module';
import { ToastrModule } from 'ngx-toastr';

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
    HttpClientModule,
    NgProgressModule,
<<<<<<< HEAD
    NgProgressHttpModule
=======
    NgProgressHttpModule,
    ToastrModule.forRoot({
      timeOut: 5000,
      positionClass: 'toast-top-center',
      progressBar:true,
      closeButton:true
    }),
  
>>>>>>> ec2028d05a0a9fba4df1fced2f844db80de97032
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
