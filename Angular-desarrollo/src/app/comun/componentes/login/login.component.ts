import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray, FormControl} from '@angular/forms';
import { LoginService } from '../../servicios/login/login.service';
import { usuarioLdap } from '../../../Entidades/usuarioLDAP'
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  usuario: usuarioLdap;
  loginData: FormGroup;
  res: {};
  constructor( private http: LoginService) {
    this.loginData = this.crearFormGroup();
    this.usuario = new usuarioLdap();
  }
  
  crearFormGroup(){
    return new FormGroup({
      usuario: new FormControl(''),
      pass: new FormControl('')
    });
  }

  ngOnInit(): void { }

  iniciarSesion(){
    this.usuario.cn = this.loginData.value.usuario;
    this.usuario.contrasena = this.loginData.value.pass;
    this.res = this.http.loginLdap( this.usuario );
    //ENTRE ESTOS 2 PASOS HAY QUE
    console.log(this.res);
    //localStorage.setItem("usuario", JSON.stringify( this.usuario) );
  }
}
