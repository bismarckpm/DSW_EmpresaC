import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray, FormControl} from '@angular/forms';
import { usuarioLdap } from '../../../Entidades/usuarioLDAP'
import { RecupService } from '../../servicios/recuperacion/recup.service';
import { ToastrService } from 'ngx-toastr';
import { NgEventBus } from 'ng-event-bus';
import { Router } from '@angular/router';

@Component({
  selector: 'app-recuperacion',
  templateUrl: './recuperacion.component.html',
  styleUrls: ['./recuperacion.component.css']
})
export class RecuperacionComponent implements OnInit {
  recuperacionData: FormGroup;
  usuario: usuarioLdap;

  constructor(private http: RecupService, private _toastrService: ToastrService, private eventBus: NgEventBus, public router: Router) {
    this.recuperacionData = this.crearFormGroup();
    this.usuario = new usuarioLdap();
  }

  ngOnInit(): void { }

  crearFormGroup(){
    return new FormGroup({
      email: new FormControl('')
    });
  }

  recuperacion(){
    this.usuario.correoelectronico = this.recuperacionData.value.email;
    this._toastrService.info('Espere un momento un momento, por favor', 'Verificando correo...');
    this.http.recup( this.usuario ).subscribe( data =>{
      console.log(data)
      this._toastrService.success("Correo enviado a: "+ this.usuario.correoelectronico, "Correo encontrado");
      this.router.navigate(['login']);
   },
    (error)=>{
      console.log(error);
      if(error.status == 401 ){
        this._toastrService.success("Intente de nuevo","Correo no encontrado");
      }else{
        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
      }
      this.eventBus.cast('fin-progress','chao');  
    });
    
  }

}
