package cn.ustc.easylabelshiro.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserLoginDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -1L;
    private String username;
    private String password;
}
