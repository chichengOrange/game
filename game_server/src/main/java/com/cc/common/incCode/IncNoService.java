package com.cc.common.incCode;

public interface IncNoService {


     Long nextIdByType(final IncTypeEnum typeEnum);


     int nextId(final String inc_code, final String inc_name);
}
