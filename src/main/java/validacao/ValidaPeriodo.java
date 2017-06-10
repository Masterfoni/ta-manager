package validacao;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target( {ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidadorPeriodo.class)
@Documented
public @interface ValidaPeriodo 
{
	String message() default "{br.edu.ifpe.monitoria.entidades.CC.periodo}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
