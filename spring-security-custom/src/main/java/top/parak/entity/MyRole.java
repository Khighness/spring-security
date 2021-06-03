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
@TableName("role")
public class MyRole implements Serializable {
    private static final long serialVersionUID = 7565000980394639717L;

    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    @TableField(value = "role_name")
    private String roleName;
}
