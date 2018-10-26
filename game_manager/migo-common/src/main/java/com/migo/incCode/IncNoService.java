package com.migo.incCode;

public interface IncNoService {


     Long   nextIdByType(final IncTypeEnum typeEnum);


     Long nextId(final String inc_code, final String inc_name);
}
