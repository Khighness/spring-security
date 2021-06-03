package top.parak.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author KHighness
 * @since 2021-06-02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyUser implements Serializable {
    private static final long serialVersionUID = -3088964382959687717L;

    private String userName;
    private String password;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked= true;
    private boolean credentialsNonExpired= true;
    private boolean enabled= true;
}
