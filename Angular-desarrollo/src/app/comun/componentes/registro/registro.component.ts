import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray, FormControl, FormArrayName} from '@angular/forms';
import { RegistroService } from '../../servicios/registro/registro.service';//to fix
import { usuarioLdap } from '../../../Entidades/usuarioLDAP'
import { usuario } from '../../../Entidades/usuario'
import { encuestado } from '../../../Entidades/encuestado'
import { hijo } from '../../../Entidades/hijo'
import { Observable } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
import { NgEventBus } from 'ng-event-bus';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registro',
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css']
})
export class RegistroComponent implements OnInit {
  registroData: FormGroup;
  
  constructor( private http: RegistroService, private _toastrService: ToastrService, private eventBus: NgEventBus,public router: Router, public fb: FormBuilder) {
    this.registroData = this.crearFormGroup();
  }

  crearFormGroup(){
    return new FormGroup({
      usuario: new FormControl(''),
      nombre: new FormControl(''),
      apellido: new FormControl(''),
      fecha_n: new FormControl(''),

      n_pers: new FormControl(''),
      email: new FormControl(''),
      telf: new FormControl(''),
      pais: new FormControl(''),
      estado: new FormControl(''),
      ciudad: new FormControl(''),
      parroquia: new FormControl(''),
      pass: new FormControl(''),
      repass: new FormControl(''),
      
      hijos: this.fb.array([])
    });
  }

  ngOnInit(): void {

  }
  //metodos hijos
  get hijos(){
    return this.registroData.get('hijos') as FormArray;
  }

  addHijo(){
    const grupoHijos = this.fb.group({
      nombreh: '',
      apellidoh: '',
      fec_nac_h: '',
      generoh: '',
    });
    this.hijos.push(grupoHijos);
  }

  remHijo(index: number){
    this.hijos.removeAt(index);
  }

  registro(){
    console.log(this.registroData)
    
  }
}
