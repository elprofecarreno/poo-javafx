package cl.elprofecarreno.app.dao.entity;

import java.util.Date;
import cl.elprofecarreno.app.dao.entity.Direccion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Persona {

    private int run;
    private String dv;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private Direccion direccion;
    private Date fechaNacimiento;
}
