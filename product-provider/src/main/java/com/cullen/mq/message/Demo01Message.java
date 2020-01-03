package com.cullen.mq.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 
 * @author wangyijun
 * @date 2020/1/3 18:01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Demo01Message {

    public static final String TOPIC = "DEMO_01";

    private Integer id;
}
