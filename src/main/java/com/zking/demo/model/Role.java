package com.zking.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Component
public class Role implements Serializable {
    private static final long serialVersionUID = -3467240070133894340L;
    private Integer rid;
    private String  rname;
    private Date createDate;
    private Date updateDate;
    private Integer state;
    private Set<User> users = new HashSet<>();
    private Set<Menu> menus = new HashSet<>();

}
