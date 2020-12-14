import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray, FormControl} from '@angular/forms';
import { LoginService } from '../../servicios/login/login.service';
import { usuarioLdap } from '../../../Entidades/usuarioLDAP'
import { Observable } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
import { NgEventBus } from 'ng-event-bus';
import { Router } from '@angular/router';

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
  entrada: string;
  constructor( private http: LoginService, private _toastrService: ToastrService, private eventBus: NgEventBus,public router: Router) {
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
    //this.dataLogin();
  }

  iniciarSesion(){
    this.eventBus.cast('inicio-progress','hola');
    this.entrada = this.loginData.value.usuario;
    if(this.entrada.includes('@') && this.entrada.includes('.') ){
      this.usuario.correoelectronico = this.loginData.value.usuario;
    }else{
      this.usuario.cn = this.loginData.value.usuario;
    }

    this.usuario.contrasena = this.loginData.value.pass;
    this._toastrService.info('Espere un momento un momento, por favor', 'Validando....');
    this.http.loginLdap( this.usuario ).subscribe( data =>{
      this.res = data;
      console.log(this.res);
      if(this.res.estado ==='success'){
        localStorage.setItem("user_id", this.res.user_id );
        localStorage.setItem("rol", this.res.rol );
        localStorage.setItem("token", this.res.token );
        this._toastrService.success("Bienvenido a MercadeoUCAB", "Credenciales correctas!");
        this.eventBus.cast('inicio-sesion','ok');
        this.redireccionar( this.res.rol );
      }
      this.eventBus.cast('fin-progress','chao');
    },
    (error)=>{
      console.log(error);

      if(error.status == 401 ){
        this._toastrService.success("Intente de nuevo", "Credenciales incorrectas!");
      }else{
        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
      }
      this.eventBus.cast('fin-progress','chao');  
    });
  }

  dataLogin(){
    //Quitar;
    this.eventBus.cast('inicio-sesion','ok');
    localStorage.setItem("user_id", '1' );
    localStorage.setItem("rol", 'admin' );
    localStorage.setItem("token", '111111111' );
  }

  redireccionar( tipo_usuario: string ){
    if (tipo_usuario == 'administrador'){
      tipo_usuario = 'admin'
    }
    this.router.navigate([tipo_usuario]);
  }

}
