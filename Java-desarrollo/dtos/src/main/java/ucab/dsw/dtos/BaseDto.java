package ucab.dsw.dtos;


import ucab.dsw.excepciones.PruebaExcepcion;

public class BaseDto
{
    private long _id;


    public BaseDto(long id ) throws Exception
    {
        setId( id );
    }

    public BaseDto()
    {
    }

    public long getId()
    {
        return _id;
    }

    public void setId( long id ) throws PruebaExcepcion
    {
        if ( id >= 0 )
        {
            _id = id;
        }
        else
        {
            throw new PruebaExcepcion( );
        }
    }
}
