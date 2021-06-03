package top.parak.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author KHighness
 * @since 2021-06-01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("persistent_logins ")
public class PersistentLogins implements Serializable {
    private static final long serialVersionUID = 8750832478266802284L;

    @TableField("username")
    private String username;

    @TableField("series")
    private String series;

    @TableField("token")
    private String token;

    @TableField("")
    private Long lastUsed;
}
