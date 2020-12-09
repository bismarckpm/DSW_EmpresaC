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
  res: any;
  res$: Observable<any>;

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
    this.http.loginLdap( this.usuario ).subscribe( data =>{
       this.res = data;
       console.log(this.res);
       localStorage.setItem("user_id", this.res.user_id );
       localStorage.setItem("rol", this.res.rol );
       localStorage.setItem("token", this.res.token );
    });
    
  }
}
