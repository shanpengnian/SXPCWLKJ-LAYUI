package com.sxpcwlkj.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author http://www.sxpcwlkj.com
 * @version 1.0
 * @description com.sxpcwlkj.common
 * @date 2019/8/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Layuitree {

    private String id;

    private String title;

    private Boolean last;    //是否有下级  true  没有  false  有

    private String parentId;  //父 id

    private List<Layuitree> children;
}
