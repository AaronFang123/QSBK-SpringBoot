package fun.aaronfang.qsbk.demo.validation.phone;

import fun.aaronfang.qsbk.demo.common.ApiValidationException;
import fun.aaronfang.qsbk.demo.util.PhoneValidationUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneValidationImpl implements ConstraintValidator<PhoneValidation, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (PhoneValidationUtil.isPhone(value)) {
            return true;
        } else {
            throw new ApiValidationException("Phone Validation Failed", 10001);
        }
    }
}
