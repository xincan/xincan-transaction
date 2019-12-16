package com.xincan.transaction.server.demo.annotations;

import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Copyright (C), 2019,北京同创永益科技发展有限公司
 * @ProjectName: xincan-transaction
 * @Package: com.xincan.transaction.server.demo.annotations
 * @ClassName: HatechIdCardValidator
 * @author: wangmingshuai
 * @Description: 身份证号校验
 * @Date: 2019/12/12 15:19
 * @Version: 1.0
 */
public class HatechIdCardValidator implements ConstraintValidator<HatechIdCard,String> {

    //可以用来获取注解中的属性值
    @Override
    public void initialize(HatechIdCard constraintAnnotation) {

    }

    /**
     * 对输入的内容进行校验
     * @param value 输入的内容
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        return IdCardVerification.IDCardValidate(value.toUpperCase());
    }
}
