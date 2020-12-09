import { ParroquiaDto } from './parroquiaDto';
import { NivelAcademicoDto } from './nivelAcademicoDto';

export class CaracteristicaDemograficaDto{
    edad_min:number;
	edad_max:number;
	nivel_socioeconomico:string;
	nacionalidad:string;
	cantidad_hijos:number;
	genero:string;
	parroquiaDto: ParroquiaDto;
	nivel_AcademicoDto: NivelAcademicoDto; 

	constructor(parroquiaDto: ParroquiaDto, nivel_academicoDto: NivelAcademicoDto){
		this.parroquiaDto=parroquiaDto;
		this.nivel_AcademicoDto=nivel_academicoDto;
	}
}