package com.migo.dao;

import com.migo.incCode.IncModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


public interface IncDao {

    @Select({"<script>select current_value from auto_increment where inc_code=#{inc_code}</script>"})
    Long selectIncByCode(@Param("inc_code") String inc_code);


    @Update({"<script>update auto_increment set current_value = (current_value+increment_step),update_time=NOW() where inc_code=#{inc_code}</script>"})
    int updateIncAuto(String inc_code);


    @Insert({"<script>insert into  auto_increment (inc_code,inc_name,current_value,increment_step,create_time,update_time) " +
            "values(#{inc_code},#{inc_name},#{current_value},#{increment_step},#{create_time},#{update_time})</script>"})
    int insertInc(IncModel inc);

}
