import { SubcategoriaDto } from './subcategoriaDto';
import { TipoDto } from './TipoDto';

export class MarcaDto{
    id: number;
    nombre:string;
    subcategoriaDto:SubcategoriaDto;
    tipoDto: TipoDto[];
}