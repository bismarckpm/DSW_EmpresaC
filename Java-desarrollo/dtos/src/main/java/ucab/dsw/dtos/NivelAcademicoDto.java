package ucab.dsw.dtos;

public class NivelAcademicoDto extends BaseDto {

        private String nombre;

        public String getNombre()
        {
            return nombre;
        }

        public void setNombre( String nombre )
        {
            this.nombre = nombre;
        }

        public NivelAcademicoDto(long id) throws Exception {
            super(id);
        }

        public NivelAcademicoDto() {
        }
}
