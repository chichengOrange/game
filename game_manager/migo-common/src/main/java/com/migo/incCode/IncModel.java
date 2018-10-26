package com.migo.incCode;

import java.util.Date;


public class IncModel {
    private String inc_code;
    private String inc_name;
    private Integer current_value;
    private Integer increment_step;
    private Date create_time;
    private Date update_time;

    public String getInc_code() {
        return inc_code;
    }

    public void setInc_code(String inc_code) {
        this.inc_code = inc_code;
    }

    public String getInc_name() {
        return inc_name;
    }

    public void setInc_name(String inc_name) {
        this.inc_name = inc_name;
    }

    public Integer getCurrent_value() {
        return current_value;
    }

    public void setCurrent_value(Integer current_value) {
        this.current_value = current_value;
    }

    public Integer getIncrement_step() {
        return increment_step;
    }

    public void setIncrement_step(Integer increment_step) {
        this.increment_step = increment_step;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
}
