package top.parak.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author KHighness
 * @since 2020-12-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class MyUser implements Serializable {
    private static final long serialVersionUID = 1753000091845565507L;

    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    @TableField(value = "username")
    private String username;

    @TableField(value = "password")
    private String password;

    @TableField(exist = false)
    private boolean accountNonExpired = true;

    @TableField(exist = false)
    private boolean accountNonLocked= true;

    @TableField(exist = false)
    private boolean credentialsNonExpired= true;

    @TableField(exist = false)
    private boolean enabled= true;
}
