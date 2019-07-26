package com.zking.demo.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Component
public class User  implements Serializable {
    private static final long serialVersionUID = -3916120406383308129L;
    private Integer id;
    private String name ;
    private String password;
    private String nickname;
    private Integer state;
    private Set<Role> roles =new HashSet<>();
}
