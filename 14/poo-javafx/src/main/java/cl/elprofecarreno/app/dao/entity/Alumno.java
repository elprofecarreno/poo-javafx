package cl.elprofecarreno.app.dao.entity;

import com.google.gson.annotations.SerializedName;

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
public class Alumno extends Persona {
    
    @SerializedName("carrera")
    private String carrera;

    @SerializedName("semestre")
    private String semestre;


}
