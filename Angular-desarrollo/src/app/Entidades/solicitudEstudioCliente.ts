import { MarcaDto } from './marcaDto';
import { ClienteDto } from './clienteDto';
import {CaracteristicaDemograficaDto} from './CaracteristicaDemograficaDto'

export class SolicitudEstudioCliente{
	modoencuesta: string;
    marcaDto: MarcaDto;
	clienteDto: ClienteDto;
	caracteristica_DemograficaDto: CaracteristicaDemograficaDto;

	constructor(marcaDto:MarcaDto, clienteDto:ClienteDto, caracteristica_DemograficaDto:CaracteristicaDemograficaDto){
		this.marcaDto=marcaDto;
		this.clienteDto=clienteDto;
		this.caracteristica_DemograficaDto=caracteristica_DemograficaDto;
	}
}