package com.zods.plugins.db.annotation.valid.em;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
/**
 * @author jianglong
 * @version 1.0
 * @Description
 * @createDate 2022-06-20
 */
public class AssertEnumValidator implements ConstraintValidator<AssertEnum, Object> {
    private Class enumClass;

    public AssertEnumValidator() {
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        } else {
            try {
                EnumInterface[] values = (EnumInterface[])((EnumInterface[])this.enumClass.getDeclaredMethod("values").invoke((Object)null));
                Boolean exist = false;
                EnumInterface[] var5 = values;
                int var6 = values.length;

                for(int var7 = 0; var7 < var6; ++var7) {
                    EnumInterface enumInterface = var5[var7];
                    exist = enumInterface.exist(value);
                    if (exist) {
                        break;
                    }
                }

                return exist;
            } catch (Exception var9) {
                return false;
            }
        }
    }

    public void initialize(AssertEnum constraintAnnotation) {
        this.enumClass = constraintAnnotation.value();
    }
}
