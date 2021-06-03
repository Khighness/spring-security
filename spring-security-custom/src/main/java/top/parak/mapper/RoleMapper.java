package top.parak.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.parak.entity.MyRole;

/**
 * @author KHighness
 * @since 2020-12-21
 */
@Mapper
public interface RoleMapper extends BaseMapper<MyRole> {
}
