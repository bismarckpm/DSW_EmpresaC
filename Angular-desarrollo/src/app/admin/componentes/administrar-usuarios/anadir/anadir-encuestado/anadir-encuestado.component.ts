import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray, FormControl, FormArrayName} from '@angular/forms';
import { RegistroService } from 'src/app/comun/servicios/registro/registro.service';
import { nuevoEncuestado } from 'src/app/Entidades/nuevoEncuestado'
import { usuarioLdap } from 'src/app/Entidades/usuarioLDAP'
import { encuestado } from 'src/app/Entidades/encuestado'
import { telefono } from 'src/app/Entidades/telefono'
import { metodo_conexion } from 'src/app/Entidades/metodo_conexion'
import { nivel_academico } from 'src/app/Entidades/nivel_academico'
import { ocupacion } from 'src/app/Entidades/ocupacion'
import { hijo } from 'src/app/Entidades/hijo'
import { Observable } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
import { NgEventBus } from 'ng-event-bus';
import { Router } from '@angular/router';
import { NivelAcademicoDto } from 'src/app/Entidades/nivelAcademicoDto';
import { ParroquiaDto } from 'src/app/Entidades/parroquiaDto';

import { LoginService } from "../../../../../comun/servicios/login/login.service";

@Component({
  selector: 'app-anadir-encuestado',
  templateUrl: './anadir-encuestado.component.html',
  styleUrls: ['./anadir-encuestado.component.css']
})
export class AnadirEncuestadoComponent implements OnInit {

  registroData: FormGroup;
  nuevoEncuestado: nuevoEncuestado;

  //Datos para el get inicial
  public paises: any[];
  public estados: any[];
  public ciudades: any[];
  public parroquias: any[];
  public metodos_conexion: any[];
  public niveles_academicos: any[];
  public ocupaciones: any[];

  //Datos para mostrar filtrados
  public estados_filtered: any[];
  public ciudades_filtered: any[];
  public parroquias_filtered: any[];

  //Datos para retornar en filtro
  public pais_id:any;
  public estado_id:any;
  public ciudad_id:any;
  
  constructor( private http: RegistroService,
    private loginService:LoginService, private _toastrService: ToastrService, private eventBus: NgEventBus,public router: Router, public fb: FormBuilder) {
    this.registroData = this.crearFormGroup();
    this.nuevoEncuestado = new nuevoEncuestado(new usuarioLdap() ,new encuestado(new NivelAcademicoDto(),new ParroquiaDto) );
        
    this.getAllPaises();
    this.getAllEstados();
    this.getAllCiudades();
    this.getAllParroquias();
    this.getAllNivelesAcademicos();
    this.getAllOcupacion();
    this.getAllMetodoConexion();
  }

  crearFormGroup(){
    return new FormGroup({
      usuarioldap: new FormControl(''),
      usuario: new FormControl(''),
      nombre: new FormControl(''),
      apellido: new FormControl(''),
      doc_id: new FormControl(''),
      fecha_n: new FormControl(''),
      genero: new FormControl(''),
      n_pers: new FormControl(''),
      email: new FormControl(''),
      telf: new FormControl(''),
      parroquia: new FormControl(''),
      pass: new FormControl(''),
      repass: new FormControl(''),
      nivel_academico_id: new FormControl(''),
      ocupacion_id: new FormControl(''),
      hijos: this.fb.array([]),
      tlfns: this.fb.array([]),
      met_con: this.fb.array([]),
      ocupac: this.fb.array([])
    });
  }

  ngOnInit(): void {
    this.addMetCon();
    this.addOcupaciones();
    this.addTelf();
  }
  //metodos hijos
  get hijos(){
    return this.registroData.get('hijos') as FormArray;
  }

  addHijo(){
    const grupoHijos = this.fb.group({
      nombre: '',
      apellido: '',
      fecha_nacimiento: '',
      genero: '',
    });
    this.hijos.push(grupoHijos);
  }

  remHijo(index: number){
    this.hijos.removeAt(index);
  }

  //metodos tlfns
  get tlfns(){
    return this.registroData.get('tlfns') as FormArray;
  }

  addTelf(){
    const grupoTlfns = this.fb.group({
      codigo_area: '',
      numero: ''
    });
    this.tlfns.push(grupoTlfns);
  }

  remTlfns(index: number){
    this.tlfns.removeAt(index);
  }

  //metodos conexion
  get met_con(){
    return this.registroData.get('met_con') as FormArray;
  }

  addMetCon(){
    const grupoMetCon = this.fb.group({
      id: ''
    });
    this.met_con.push(grupoMetCon);
  }

  remMetCon(index: number){
    this.met_con.removeAt(index);
  }

  //metodos ocupacion
  get ocupac(){
    return this.registroData.get('ocupac') as FormArray;
  }

  addOcupaciones(){
    const grupoOcupaciones = this.fb.group({
      id: ''
    });
    this.ocupac.push(grupoOcupaciones);
  }

  remOcupaciones(index: number){
    this.ocupac.removeAt(index);
  }

  //Obtencion de datos
  getAllPaises(){
    this.http.getAllPaises().subscribe(
      (response)=>{
        console.log(response);
        this.paises=response.paises;
        //this._toastrService.success("Exito", "Todas las paises");
      },
      (error)=>{
        if(error.error.estado=="unauthorized"){
          this._toastrService.error("Ops! Hubo un problema.", "La sesion expiro.");
          this.loginService.logOut().subscribe(x=>{window.location.reload()}, err=>{window.location.reload()});
  
        }
        else{
          console.log(error);
          this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        }
      });
  }

  getAllEstados(){
    this.http.getAllEstados().subscribe(
      (response)=>{
        console.log(response);
        this.estados=response.estados;
        //this._toastrService.success("Exito", "Todas las estados");
      },
      (error)=>{
        if(error.error.estado=="unauthorized"){
          this._toastrService.error("Ops! Hubo un problema.", "La sesion expiro.");
          this.loginService.logOut().subscribe(x=>{window.location.reload()}, err=>{window.location.reload()});
  
        }
        else{
          console.log(error);
          this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        }
      });
  }

  getAllCiudades(){
    this.http.getAllCiudades().subscribe(
      (response)=>{
        console.log(response);
        this.ciudades=response.ciudades;
        //this._toastrService.success("Exito", "Todas las ciudades");
      },
      (error)=>{
        if(error.error.estado=="unauthorized"){
          this._toastrService.error("Ops! Hubo un problema.", "La sesion expiro.");
          this.loginService.logOut().subscribe(x=>{window.location.reload()}, err=>{window.location.reload()});
  
        }
        else{
          console.log(error);
          this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        }
      });
  }

  getAllParroquias(){
    this.http.getAllParroquias().subscribe(
      (response)=>{
        console.log(response);
        this.parroquias=response.parroquias;
        //this._toastrService.success("Exito", "Todas las parroquias");
      },
      (error)=>{
        if(error.error.estado=="unauthorized"){
          this._toastrService.error("Ops! Hubo un problema.", "La sesion expiro.");
          this.loginService.logOut().subscribe(x=>{window.location.reload()}, err=>{window.location.reload()});
  
        }
        else{
          console.log(error);
          this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        }
      });
  }

  getAllNivelesAcademicos(){
    this.http.getAllNivelesAcademicos().subscribe(
      (response)=>{
        console.log(response);
        this.niveles_academicos=response.niveles_academicos;
        //this._toastrService.success("Exito", "Todas los niveles academicos");
      },
      (error)=>{
        if(error.error.estado=="unauthorized"){
          this._toastrService.error("Ops! Hubo un problema.", "La sesion expiro.");
          this.loginService.logOut().subscribe(x=>{window.location.reload()}, err=>{window.location.reload()});
  
        }
        else{
          console.log(error);
          this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        }
      });
  }

  getAllMetodoConexion(){
    this.http.getAllMetodoConexion().subscribe(
      (response)=>{
        console.log(response);
        this.metodos_conexion=response.metodos_conexion;
        //this._toastrService.success("Exito", "Todas los metodos de conexion");
      },
      (error)=>{
        if(error.error.estado=="unauthorized"){
          this._toastrService.error("Ops! Hubo un problema.", "La sesion expiro.");
          this.loginService.logOut().subscribe(x=>{window.location.reload()}, err=>{window.location.reload()});
  
        }
        else{
          console.log(error);
          this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        }
      });
  }

  getAllOcupacion(){
    this.http.getAllOcupacion().subscribe(
      (response)=>{
        console.log(response);
        this.ocupaciones=response.ocupaciones;
        //this._toastrService.success("Exito", "Todas las ocupaciones");
      },
      (error)=>{
        if(error.error.estado=="unauthorized"){
          this._toastrService.error("Ops! Hubo un problema.", "La sesion expiro.");
          this.loginService.logOut().subscribe(x=>{window.location.reload()}, err=>{window.location.reload()});
  
        }
        else{
          console.log(error);
          this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        }
      });
  }

  //Filtros de localización
  filtroEstados(e){
    console.log(this.estados);
    this.estados_filtered=this.estados.filter(estado=>estado.pais_id === this.pais_id);
    console.log(this.estados_filtered);

    this.estado_id=0;
    this.ciudades_filtered=this.ciudades.filter(ciudad=>ciudad.estado_id === this.estado_id);
    
    this.ciudad_id=0;
    this.parroquias_filtered=this.parroquias.filter(parroquia=>parroquia.ciudad_id === this.ciudad_id);

    this.nuevoEncuestado.encuestado.parroquiaDto.id=0;
  }
  filtroCiudades(e){
    this.ciudades_filtered=this.ciudades.filter(ciudad=>ciudad.estado_id === this.estado_id);

    this.ciudad_id=0;
    this.parroquias_filtered=this.parroquias.filter(parroquia=>parroquia.ciudad_id === this.ciudad_id);
    
    this.nuevoEncuestado.encuestado.parroquiaDto.id=0;
  }
  filtroParroquias(e){
    console.log(this.parroquias);
    this.parroquias_filtered=this.parroquias.filter(parroquia=>parroquia.ciudad_id === this.ciudad_id);
    console.log(this.parroquias_filtered);
  }
  
  addUsuarioEncuestado(){
    this.eventBus.cast('inicio-progress','chao');
    this.nuevoEncuestado.usuarioLdap.cn = this.registroData.value.usuario;
    this.nuevoEncuestado.usuarioLdap.nombre = this.registroData.value.nombre;
    this.nuevoEncuestado.usuarioLdap.sn = this.registroData.value.apellido;
    this.nuevoEncuestado.usuarioLdap.correoelectronico = this.registroData.value.email;
    this.nuevoEncuestado.usuarioLdap.contrasena = this.registroData.value.pass;
    this.nuevoEncuestado.usuarioLdap.tipo_usuario = "encuestado";
    this.nuevoEncuestado.encuestado.nombre = this.registroData.value.nombre;
    this.nuevoEncuestado.encuestado.apellido = this.registroData.value.apellido;
    this.nuevoEncuestado.encuestado.correo = this.registroData.value.email;
    this.nuevoEncuestado.encuestado.cant_personas_vivienda = this.registroData.value.n_pers;
    this.nuevoEncuestado.encuestado.genero = this.registroData.value.genero;
    this.nuevoEncuestado.encuestado.doc_id = this.registroData.value.doc_id;
    this.nuevoEncuestado.encuestado.fecha_nacimiento = this.registroData.value.fecha_n;
    this.nuevoEncuestado.encuestado.parroquiaDto.id = this.registroData.value.parroquia;
    this.nuevoEncuestado.encuestado.nivel_AcademicoDto.id = this.registroData.value.nivel_academico_id

    this.nuevoEncuestado.hijo = this.registroData.value.hijos;
    this.nuevoEncuestado.metodo_conexion = this.registroData.value.met_con;
    this.nuevoEncuestado.telefono = this.registroData.value.tlfns;
    this.nuevoEncuestado.ocupacion = this.registroData.value.ocupac;

    console.log(this.nuevoEncuestado);
    this._toastrService.info('Espere un momento un momento, por favor', 'Ingresando datos...');
    this.http.registro( this.nuevoEncuestado ).subscribe( 
      (response) =>{
        console.log(response)
        if(response.estado=='success'){
          this._toastrService.success("Exito", "Usuario encuestado añadido");
          this._toastrService.info('Espero un momento, por favor.','Actualizando...');
          this.eventBus.cast('actualizar-usuario','actualizar');
        }else{
          this._toastrService.error("Esta usuario ya se encuentra en el sistema", "Error");
          this.eventBus.cast('fin-progress','chao');
        }
      },
      (error)=>{
      console.log(error);
      if(error.status == 401 ){
        this._toastrService.success("Intente de nuevo","Correo o email duplicado");
      }else{
        this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
      }
      this.eventBus.cast('fin-progress','chao');  
    });
  }

}
