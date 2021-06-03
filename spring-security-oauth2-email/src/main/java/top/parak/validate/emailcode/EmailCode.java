package top.parak.validate.emailcode;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author KHighness
 * @since 2021-06-02
 * @apiNote 邮箱验证码
 */
@Data
@AllArgsConstructor
public class EmailCode {
    private String code;
}
