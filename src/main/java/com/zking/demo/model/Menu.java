package com.zking.demo.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class Menu implements Serializable {
    private static final long serialVersionUID = -1937509966443047470L;
    private Integer mid;
    private String title;
    private String url;
    private Integer pid;
    private Integer qx_dj;
    private String  perms;
    private Integer status;
    private Integer type;
    private Integer sort;
    private String  remark;
    @JsonIgnore
    private Map<Long,Menu> children = new HashMap<>();

}
