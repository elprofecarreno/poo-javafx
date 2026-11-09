package cl.elprofecarreno.app.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter 
@Setter 
@NoArgsConstructor
@AllArgsConstructor 
@SuperBuilder 
public class Profesor extends Persona {
    
    private String especialidad;
    private String jornada;
    private long sueldoBruto;
}
