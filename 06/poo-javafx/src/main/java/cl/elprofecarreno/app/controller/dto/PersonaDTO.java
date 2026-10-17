package cl.elprofecarreno.app.controller.dto;
import java.util.Date;

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
public class PersonaDTO {
    
    private String runDv;
    private String nombreCompleto;
    private Date fechaNacimiento;
    private String direccionCompleto;
    private String comuna;
    private String region;

}
