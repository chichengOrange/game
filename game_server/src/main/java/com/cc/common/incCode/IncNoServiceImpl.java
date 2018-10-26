package com.cc.common.incCode;

import com.cc.mapper.IncMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service("incNoService")
public class IncNoServiceImpl implements IncNoService {

    @Autowired
    private IncMapper incMapper;

    @Transactional
    public Long nextIdByType(final IncTypeEnum typeEnum) {

        int id = nextId(typeEnum.getKey(), typeEnum.getName());


        String auto_id = getId(IncConst.NO_CIRCLE_LENGTH, id);



        return Long.valueOf(typeEnum.getPrefix()+ new SimpleDateFormat("yyMMdd").format(new Date())+auto_id);
    }


    @Transactional
    public int nextId(final String inc_code, final String inc_name) {
        int r = incMapper.updateIncAuto(inc_code);
        if (r == 0) {
            //说明这条数据不存在
            IncModel model = new IncModel();
            model.setInc_code(inc_code);
            model.setInc_name(inc_name);
            model.setCurrent_value(IncConst.INIT_VALUE);
            model.setIncrement_step(IncConst.INCREMENT_STEP);
            model.setCreate_time(new Date());
            model.setUpdate_time(model.getCreate_time());
            incMapper.insertInc(model);
        }
        IncModel rModel = incMapper.selectIncByCode(inc_code);
        return rModel.getCurrent_value();
    }


    /**
     * 根据前缀和序列号生成用户号   %d%010d 前面第一个0代表：数字前面补0；后面的10代表字符总长度为10，d代表整数类型。。
     *
     * @param index     序列
     * @return
     */
    public static String getId(int length,Integer index) {
        return String.format("%0" + length + "d", index);
    }


}
