package cl.elprofecarreno.app.dao.entity;

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
public class Direccion {
    
    private String calle;
    private String numero;
    private String comuna;
    private String region;
}
