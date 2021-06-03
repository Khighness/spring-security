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
@TableName("user_role")
public class UserToRole implements Serializable {
    private static final long serialVersionUID = 252063821059124209L;

    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    @TableField(value = "user_id")
    private int userId;

    @TableField(value = "role_id")
    private int roleId;
}
