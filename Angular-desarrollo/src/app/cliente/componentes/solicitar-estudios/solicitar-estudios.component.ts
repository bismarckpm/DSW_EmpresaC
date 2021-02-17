import { Component, OnInit } from '@angular/core';
import { SolicitudEstudiosService } from '../../servicios/solicitud_estudios/solicitud-estudios.service';
import {NgProgress, NgProgressRef} from 'ngx-progressbar';
import { ToastrService } from 'ngx-toastr';
import { SolicitudEstudioCliente } from 'src/app/Entidades/solicitudEstudioCliente';
import { MarcaDto } from 'src/app/Entidades/marcaDto';
import { ClienteDto } from 'src/app/Entidades/clienteDto';
import { CaracteristicaDemograficaDto } from 'src/app/Entidades/CaracteristicaDemograficaDto';
import { ParroquiaDto } from 'src/app/Entidades/parroquiaDto';
import { NivelAcademicoDto } from 'src/app/Entidades/nivelAcademicoDto';
import { NgEventBus } from 'ng-event-bus';
import { Router } from '@angular/router';

import { LoginService } from "../../../comun/servicios/login/login.service";

@Component({
  selector: 'app-solicitar-estudios',
  templateUrl: './solicitar-estudios.component.html',
  styleUrls: ['./solicitar-estudios.component.css'],
  providers:[SolicitudEstudiosService]
})
export class SolicitarEstudiosComponent implements OnInit {

  /*Data*/
  public cliente_id:any;
  public user_id:any;
  public marcas: any[];
  public categorias: any[];
  public subcategorias:any[];
  public hijos:any[];
  public paises:any[];
  public estados:any[];
  public ciudades:any[];
  public parroquias:any[];
  public edades:any[];
  public niveles_socioeconomico:any[];
  public niveles_academicos:any[];

  /*Data Filtered*/
  public marcas_filtered:any[];
  public categorias_filtered:any[];
  public subcategorias_filtered:any[];
  public estados_filtered:any[];
  public ciudades_filtered:any[];
  public parroquias_filtered:any[];

  /*Values needed to filter*/
  public categoria_id:any;
  public subcategoria_id:any;
  public pais_id:any;
  public estado_id:any;
  public ciudad_id:any;
  
  /*Selecciones*/
  public modo_encuestaSelected:any;
  public marcaSelected:any;
  public edad_minimaSelected:any;
  public edad_maximaSelected:any;
  public parroquiaSelected:any;
  public nacionalidadSelected:any;
  public nivel_socioeconomicoSelected:any;
  public nivel_academicoSelected:any;
  public hijoSelected:any;
  public generoSelected:any;


  /*Entidad DTO*/
  public caracteristicaDemograficaDto:CaracteristicaDemograficaDto;
  public solicitudEstudioCliente: SolicitudEstudioCliente;


  constructor(private route: Router,private _solicitudService:SolicitudEstudiosService, private progress: NgProgress, private _toastrService: ToastrService,private eventBus: NgEventBus,
    private loginService:LoginService) {}

  ngOnInit(): void {
    this._toastrService.info('Espere unos segundos, por favor. Un momento!');
    this.init();
    this._toastrService.info('Listo!');
  }


  /*Servicios*/

  getAllMarcas(){
    this._solicitudService.getAllMarcas().subscribe(
      (response)=>{
        console.log(response);
        this.marcas=response.marcas;
        this._toastrService.success("Exito", "Todas las marcas");
      },
      (error)=>{
        if(error.error.estado=="unauthorized"){
          this.eventBus.cast('fin-progress','chao');
          this._toastrService.error("Ops! Hubo un problema.", "La sesion expiro.");
          this.loginService.logOut().subscribe(x=>{window.location.reload()}, err=>{window.location.reload()});
  
        }
        else{
          console.log(error);
          this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");

        }
      });
  }

  getAllCategorias(){
    this._solicitudService.getAllCategorias().subscribe(
      (response)=>{
        console.log(response);
        this.categorias=response.categorias;
        this.categorias_filtered=this.categorias.filter( categoria => categoria.estado === 'activo');
        this._toastrService.success("Exito", "Todas las categorias");
      },
      (error)=>{
        if(error.error.estado=="unauthorized"){
          this.eventBus.cast('fin-progress','chao');
          this._toastrService.error("Ops! Hubo un problema.", "La sesion expiro.");
          this.loginService.logOut().subscribe(x=>{window.location.reload()}, err=>{window.location.reload()});
  
        }
        else{
          console.log(error);
          this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");

        }
      });
  }

  getAllSubcategorias(){
    this._solicitudService.getAllSubcategorias().subscribe(
      (response)=>{
        console.log(response);
        this.subcategorias=response.subcategorias;
        
        this._toastrService.success("Exito", "Todas las subcategorias");
      },
      (error)=>{
        if(error.error.estado=="unauthorized"){
          this.eventBus.cast('fin-progress','chao');
          this._toastrService.error("Ops! Hubo un problema.", "La sesion expiro.");
          this.loginService.logOut().subscribe(x=>{window.location.reload()}, err=>{window.location.reload()});
  
        }
        else{
          console.log(error);
          this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");

        }
      });
  }

  getAllPaises(){
    this._solicitudService.getAllPaises().subscribe(
      (response)=>{
        console.log(response);
        this.paises=response.paises;
        this._toastrService.success("Exito", "Todas las paises");
      },
      (error)=>{
        if(error.error.estado=="unauthorized"){
          this.eventBus.cast('fin-progress','chao');
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
    this._solicitudService.getAllEstados().subscribe(
      (response)=>{
        console.log(response);
        this.estados=response.estados;
        this._toastrService.success("Exito", "Todas las estados");
      },
      (error)=>{
        if(error.error.estado=="unauthorized"){
          this.eventBus.cast('fin-progress','chao');
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
    this._solicitudService.getAllCiudades().subscribe(
      (response)=>{
        console.log(response);
        this.ciudades=response.ciudades;
        this._toastrService.success("Exito", "Todas las ciudades");
      },
      (error)=>{
        if(error.error.estado=="unauthorized"){
          this.eventBus.cast('fin-progress','chao');
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
    this._solicitudService.getAllParroquias().subscribe(
      (response)=>{
        console.log(response);
        this.parroquias=response.parroquias;
        this._toastrService.success("Exito", "Todas las parroquias");
      },
      (error)=>{
        if(error.error.estado=="unauthorized"){
          this.eventBus.cast('fin-progress','chao');
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
    this._solicitudService.getAllNivelAcademicos().subscribe(
      (response)=>{
        console.log(response);
        this.niveles_academicos=response.niveles_academicos;
        this._toastrService.success("Exito", "Todas las niveles academicos");
      },
      (error)=>{
        if(error.error.estado=="unauthorized"){
          this.eventBus.cast('fin-progress','chao');
          this._toastrService.error("Ops! Hubo un problema.", "La sesion expiro.");
          this.loginService.logOut().subscribe(x=>{window.location.reload()}, err=>{window.location.reload()});
  
        }
        else{
          console.log(error);
          this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        }
      });
  }

  getClienteId(){
    this.user_id=+localStorage.getItem('user_id');
    this._solicitudService.getClienteIdByUsuario(this.user_id).subscribe(
      (response)=>{
        console.log(response);
        this.cliente_id=response.cliente_id;
        console.log('Cliente: '+this.cliente_id);
      },
      (error)=>{
        if(error.error.estado=="unauthorized"){
          this.eventBus.cast('fin-progress','chao');
          this._toastrService.error("Ops! Hubo un problema.", "La sesion expiro.");
          this.loginService.logOut().subscribe(x=>{window.location.reload()}, err=>{window.location.reload()});
  
        }
        else{
          console.log(error);
          this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
        }
      });
  }
  


  /*Filters*/
  filterCategorias(e){
    this.categorias_filtered=this.categorias.filter( categoria => categoria.estado === 'activo');
    console.log(this.categorias_filtered);
  }

  filterSubcategorias(e){
    console.log(this.categoria_id);
    this.subcategorias_filtered=this.subcategorias.filter( subcategoria => subcategoria.categoria_id === this.categoria_id && subcategoria.estado === 'activo');
    
    this.subcategoria_id=0;
    this.marcas_filtered=this.marcas.filter( marca => marca.subcategoria_id === this.subcategoria_id);
    console.log(this.subcategorias_filtered);
  }

  filterMarcas(e){
    console.log(this.subcategoria_id);
    this.marcas_filtered=this.marcas.filter( marca => marca.subcategoria_id === this.subcategoria_id && marca.estado === 'activo');
    console.log(this.marcas_filtered);
  }

  filterEstados(e){
    console.log(this.estados);
    this.estados_filtered=this.estados.filter(estado=>estado.pais_id === this.pais_id);
    console.log(this.estados_filtered);

    this.estado_id=0;
    this.ciudades_filtered=this.ciudades.filter(ciudad=>ciudad.estado_id === this.estado_id);
    
    this.ciudad_id=0;
    this.parroquias_filtered=this.parroquias.filter(parroquia=>parroquia.ciudad_id === this.ciudad_id);

    this.parroquiaSelected=0;
  }

  filterCiudades(e){
    console.log(this.ciudades);
    this.ciudades_filtered=this.ciudades.filter(ciudad=>ciudad.estado_id === this.estado_id);
    console.log(this.ciudades_filtered);


    this.ciudad_id=0;
    this.parroquias_filtered=this.parroquias.filter(parroquia=>parroquia.ciudad_id === this.ciudad_id);
    
    this.parroquiaSelected=0;
  }
  
  filterParroquias(e){
    console.log(this.parroquias);
    this.parroquias_filtered=this.parroquias.filter(parroquia=>parroquia.ciudad_id === this.ciudad_id);
    console.log(this.parroquias_filtered);
  }



  /* Post */

  enviarSolicitud(){

	if(this.marcaSelected!=undefined){
		this.eventBus.cast('inicio-progress','hola');
			
		this.dataToSend();
		console.log(this.solicitudEstudioCliente);
		
		this._toastrService.info('Espere unos segundos', 'por favor. Un momento!');
		this._solicitudService.doSolicitudEstudio(this.solicitudEstudioCliente).subscribe(
		  (response)=>{
			console.log(response);
			if(response.estado=='success'){
			  this._toastrService.success("Todo salio bien!", "Solicitud Procesada");
			  this.route.navigate(['/cliente/consultar-estudios']);
			}else{
			  this._toastrService.error(response.mensaje, "Ops! Hubo un problema");
			}

			this.eventBus.cast('fin-progress','chao');

		  },
		  (error)=>{
        if(error.error.estado=="unauthorized"){
          this.eventBus.cast('fin-progress','chao');
          this._toastrService.error("Ops! Hubo un problema.", "La sesion expiro.");
          this.loginService.logOut().subscribe(x=>{window.location.reload()}, err=>{window.location.reload()});
  
        }
        else{
          console.log(error);
          if(error.error.mensaje){
            this._toastrService.error("Ops! Hubo un problema.", error.error.mensaje)
          }
          else{
            this._toastrService.error("Ops! Hubo un problema.", "Error del servidor. Intente mas tarde.");
          }
          this.eventBus.cast('fin-progress','chao');
        }
		  });
	}else{
		this._toastrService.error("Debe seleccioanar una marca", "Recuerde");
	}

}

  /* Data to send */
  
  dataToSend(){
    this.solicitudEstudioCliente.caracteristica_DemograficaDto.edad_min=this.edad_minimaSelected;
    this.solicitudEstudioCliente.caracteristica_DemograficaDto.edad_max=this.edad_maximaSelected;
    this.solicitudEstudioCliente.caracteristica_DemograficaDto.cantidad_hijos=this.hijoSelected;
    this.solicitudEstudioCliente.caracteristica_DemograficaDto.genero=this.generoSelected;
    this.solicitudEstudioCliente.caracteristica_DemograficaDto.nacionalidad=this.nacionalidadSelected;
    this.solicitudEstudioCliente.caracteristica_DemograficaDto.nivel_socioeconomico=this.nivel_socioeconomicoSelected;
    this.solicitudEstudioCliente.caracteristica_DemograficaDto.parroquiaDto.id=this.parroquiaSelected;
    this.solicitudEstudioCliente.caracteristica_DemograficaDto.nivel_AcademicoDto.id=this.nivel_academicoSelected;

    this.solicitudEstudioCliente.clienteDto.id=this.cliente_id;
    this.solicitudEstudioCliente.marcaDto.id=this.marcaSelected[0];
    this.solicitudEstudioCliente.modoencuesta=this.modo_encuestaSelected;
  }

  /*Init*/
  init(){
    this.getAllMarcas();
    this.getAllSubcategorias();
    this.getAllCategorias();
    this.getAllPaises();
    this.getAllEstados();
    this.getAllCiudades();
    this.getAllParroquias();
    this.getAllNivelesAcademicos();
    this.getClienteId();

    this.edades=[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55,
      56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100];
    this.hijos=[0,1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
    this.niveles_socioeconomico=['Alta','Media','Baja'];
    this.caracteristicaDemograficaDto= new CaracteristicaDemograficaDto(new ParroquiaDto,new NivelAcademicoDto);
    this.solicitudEstudioCliente=new SolicitudEstudioCliente(new MarcaDto,new ClienteDto, this.caracteristicaDemograficaDto);
    
  }
}
