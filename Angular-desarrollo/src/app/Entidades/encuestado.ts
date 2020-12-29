import { NivelAcademicoDto } from "./nivelAcademicoDto";
import { ParroquiaDto } from "./parroquiaDto";

export class encuestado{
    id: number;
    doc_id: number;
    nombre: string;
    apellido: string;
    username:string;
    
    correo: string;
    fecha_nacimiento: Date;
    cant_personas_vivienda: number;
    genero: string;
    
    nivel_AcademicoDto: NivelAcademicoDto;
    parroquiaDto: ParroquiaDto;

    constructor(nivel_AcademicoDto: NivelAcademicoDto, parroquiaDto: ParroquiaDto){
        this.nivel_AcademicoDto = nivel_AcademicoDto;
        this.parroquiaDto = parroquiaDto;
	}
}
