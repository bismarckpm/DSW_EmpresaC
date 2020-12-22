package ucab.dsw.dtos;

public class Nivel_AcademicoDto extends DtoBase{

        private String nombre;

        public String getNombre()
        {
            return nombre;
        }

        public void setNombre( String nombre )
        {
            this.nombre = nombre;
        }

        public Nivel_AcademicoDto(long id) throws Exception {
            super(id);
        }

        public Nivel_AcademicoDto() {
        }
}
