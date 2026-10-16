package cl.elprofecarreno.app.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserDTO {
    
    private String username;

    public UserDTO(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
}
