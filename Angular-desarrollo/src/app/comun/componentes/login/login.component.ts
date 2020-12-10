import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray, FormControl} from '@angular/forms';
import { LoginService } from '../../servicios/login/login.service';
import { usuarioLdap } from '../../../Entidades/usuarioLDAP'
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';
import { NgEventBus } from 'ng-event-bus';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  usuario: usuarioLdap;
  loginData: FormGroup;
  res: any;
  res$: Observable<any>;

  constructor( private http: LoginService, private _toastrService: ToastrService, private eventBus: NgEventBus) {
    this.loginData = this.crearFormGroup();
    this.usuario = new usuarioLdap();
  }
  
  crearFormGroup(){
    return new FormGroup({
      usuario: new FormControl(''),
      pass: new FormControl('')
    });
  }

  ngOnInit(): void { 
    
  }

  iniciarSesion(){
    this.dataLogin();
    this.eventBus.cast('inicio-sesion','ok');
    /*this.eventBus.cast('inicio-progress','hola');
    this.usuario.cn = this.loginData.value.usuario;
    this.usuario.contrasena = this.loginData.value.pass;
    this._toastrService.info('Espere un momento un momento, por favor', 'Validando....');
    this.http.loginLdap( this.usuario ).subscribe( data =>{
       this.res = data;
       console.log(this.res);
       if(this.res.estado='success'){
        localStorage.setItem("user_id", this.res.user_id );
        localStorage.setItem("rol", this.res.rol );
        localStorage.setItem("token", this.res.token );
        this._toastrService.success("Bienvenido", "Credenciales correctas!");
        this.eventBus.cast('inicio-sesion','ok');
       }
       else{
        this._toastrService.success("Intente de nuevo", "Credenciales incorrectas!");
       }
       this.eventBus.cast('fin-progress','chao');
    },
    (error)=>{
      console.log(error);
      this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
      this.eventBus.cast('fin-progress','chao');
    });*/
  }

  dataLogin(){
    //Quitar;
    localStorage.setItem("user_id", '1' );
    localStorage.setItem("rol", 'cliente' );
    localStorage.setItem("token", '111111111' );
  }
}
