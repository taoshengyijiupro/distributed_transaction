package com.xlinclass.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>Description: </p>
 * <p>blog: http://www.xlinclass.com</p>
 *
 * @author 涛声依旧
 * @date 2019/8/26 14:07
 */

@Mapper
public interface TestMapper {

    @Insert("insert into t_test(name) values(#{name})")
    public void insertTest(@Param("name") String name);
}
