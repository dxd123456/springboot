package com.zking.demo.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class User  implements Serializable {
    private static final long serialVersionUID = -3916120406383308129L;
    private Integer userId;
    private String userName;
    private String password;
    private String qq_openID;
    private String Perms;

}
