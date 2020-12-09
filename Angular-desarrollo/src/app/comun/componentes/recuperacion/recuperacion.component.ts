import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray, FormControl} from '@angular/forms';
import { usuarioLdap } from '../../../Entidades/usuarioLDAP'
import { RecupService } from '../../servicios/recuperacion/recup.service';

@Component({
  selector: 'app-recuperacion',
  templateUrl: './recuperacion.component.html',
  styleUrls: ['./recuperacion.component.css']
})
export class RecuperacionComponent implements OnInit {
  recuperacionData: FormGroup;
  usuario: usuarioLdap;

  constructor(private http: RecupService) {
    this.recuperacionData = this.crearFormGroup();
    this.usuario = new usuarioLdap();
  }

  ngOnInit(): void {
  }

  crearFormGroup(){
    return new FormGroup({
      email: new FormControl('')
    });
  }

  recuperacion(){
    this.usuario.correoelectronico = this.recuperacionData.value.email;
    this.http.recup( this.usuario ).subscribe( data =>{
      //aqui deberia haber una ventana emergente o algo
      console.log(data)
   });
    
  }

}
