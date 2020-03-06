package com.gundom.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckBox implements Serializable {
    private static final long serialVersionUID = 2031967800425337153L;
    private Integer id;
    private String name;
}
