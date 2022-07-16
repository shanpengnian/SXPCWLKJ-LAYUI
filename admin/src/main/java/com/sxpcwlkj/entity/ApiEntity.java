package com.sxpcwlkj.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//类名和数据库的表映射不一致时可以用此注解指定
@TableName(value = "p_api")
public class ApiEntity {
    private Integer apiId;

    private Integer userId;

    private String taskCode;

    private String apiName;

    private String apiUrl;

    private String apiSite;

    private String apiKey;

    private String apiOne;

    private String apiTwo;

    private String apiThree;

    private String apiFour;

    private String apiFive;

    private String apiSix;

    private String apiSeven;

    private String apiEight;

    private String apiNine;

    private String apiTen;

    private String apiTenOne;

    private String apiTenTwo;

    private String apiTenThree;

    private String apiTenFour;

    private String apiTenFive;

    private String apiTenSix;

    private String apiTenSeven;

    private String apiTenEight;

    private String apiTenNine;

    private String apiTwenty;

    private String apiTwentyOne;

    private String apiTwentyTwo;

    private String apiTwentyThree;

    private String apiTwentyFour;

    private String apiTwentyFive;

    private String apiTwentySix;

    private String apiTwentySeven;

    private String apiTwentyEight;

    private String apiTwentyNine;

    private String apiThirty;
}
