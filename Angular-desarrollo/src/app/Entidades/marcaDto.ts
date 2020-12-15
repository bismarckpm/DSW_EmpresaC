import { SubcategoriaDto } from './subcategoriaDto';
import { TipoDto } from './TipoDto';
import { MarcaTipoDto} from './MarcaTipoDto';

export class MarcaDto{
    id: number;
    nombre:string;
    subcategoriaDto:SubcategoriaDto;
    tipoDto: TipoDto[];
    marcaTipoDto: MarcaTipoDto;
}