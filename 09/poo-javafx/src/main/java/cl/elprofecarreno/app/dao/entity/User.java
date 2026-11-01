package cl.elprofecarreno.app.dao.entity;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@Builder 
@NoArgsConstructor 
@AllArgsConstructor 
public class User {
    
    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

}
