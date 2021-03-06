//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.migo.dao;

import com.migo.entity.TbUserEntity;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TbUserDao {
    @Select({"<script>select * from tb_user <if test ='search!=null'>where mobile like concat('%',#{search},'%')  or username like concat('%',#{search},'%')</if></script>"})
    List<TbUserEntity> queryBySearch(@Param("search") String var1);

    @Update({"<script>update tb_user set status=#{status} where user_id=#{userId}</script>"})
    int changeStatus(@Param("userId") long var1, @Param("status") int var3);
}
