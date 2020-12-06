package ucab.dsw.dtos;

public class Marca_TipoDto {


        private MarcaDto marcaDto;

        public MarcaDto getMarcaDto()
        {
            return marcaDto;
        }

        public void setMarcaDto( MarcaDto marcaDto )
        {
            this.marcaDto = marcaDto;
        }

        private TipoDto tipoDto;

        public TipoDto getTipoDto()
        {
            return tipoDto;
        }

        public void setTipoDto( TipoDto tipoDto )
        {
            this.tipoDto = tipoDto;
        }


}
