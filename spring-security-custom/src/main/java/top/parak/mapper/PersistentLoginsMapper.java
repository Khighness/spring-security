package top.parak.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.parak.entity.PersistentLogins;

/**
 * @author KHighness
 * @since 2021-06-01
 */
@Mapper
public interface PersistentLoginsMapper extends BaseMapper<PersistentLogins> {
}
