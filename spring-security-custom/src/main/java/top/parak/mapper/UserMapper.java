package top.parak.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.parak.entity.MyUser;

/**
 * @author KHighness
 * @since 2020-12-21
 */
@Mapper
public interface UserMapper extends BaseMapper<MyUser> {
}
