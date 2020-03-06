package com.gundom.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Node implements Serializable {
    private static final long serialVersionUID = -6577397030669133046L;

    private Integer id;
    private String name;
    private Integer parentId;
}
