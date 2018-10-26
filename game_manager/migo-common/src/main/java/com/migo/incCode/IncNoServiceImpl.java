package com.migo.incCode;

import com.migo.dao.IncDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service("incNoService")
public class IncNoServiceImpl implements IncNoService {

   private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd");

    private static SimpleDateFormat hourFormat = new SimpleDateFormat("HHmmss");

    @Autowired
    private IncDao incDao;

    @Transactional
    public Long nextIdByType(final IncTypeEnum typeEnum) {

        Long id = nextId(typeEnum.getKey(), typeEnum.getName());


        String auto_id = getId(IncConst.NO_CIRCLE_LENGTH, id);

        return Long.valueOf(typeEnum.getPrefix()+ simpleDateFormat.format(new Date())+auto_id);
    }



    public Long nextId(final String inc_code, final String inc_name) {
        int r = incDao.updateIncAuto(inc_code);
        if (r == 0) {
            //说明这条数据不存在
            IncModel model = new IncModel();
            model.setInc_code(inc_code);
            model.setInc_name(inc_name);
            model.setCurrent_value(IncConst.INIT_VALUE);
            model.setIncrement_step(IncConst.INCREMENT_STEP);
            model.setCreate_time(new Date());
            model.setUpdate_time(model.getCreate_time());
            incDao.insertInc(model);
        }
        Long currentVal = incDao.selectIncByCode(inc_code);
        return currentVal==null? Long.valueOf(hourFormat.format(new Date())):currentVal;
    }


    /**
     * 根据前缀和序列号生成用户号   %d%010d 前面第一个0代表：数字前面补0；后面的10代表字符总长度为10，d代表整数类型。。
     *
     * @param index     序列
     * @return
     */
    public static String getId(int length,Long index) {
        return String.format("%0" + length + "d", index);
    }


}
