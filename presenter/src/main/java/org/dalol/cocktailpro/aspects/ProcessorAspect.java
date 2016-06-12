package org.dalol.cocktailpro.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.dalol.cocktailpro.utilities.ThreadUtil;

import java.util.logging.Logger;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 6/4/2016
 */
@Aspect
public class ProcessorAspect {

    @Pointcut("execution(@org.dalol.cocktailpro.processor.BackgroundTask * *(..))")
    public void backgroundTask() {}

    @Around("backgroundTask()")
    public void doInBackground(final ProceedingJoinPoint jp) {
        //CocktailPresenterImpl.debug("FILIPPO" + jp.getTarget().toString());
        ThreadUtil.doInBackground(new Runnable() {
            @Override
            public void run() {
                try {
                    jp.proceed();
                } catch (Throwable e) {
                    Logger.getLogger("AspectJ").warning(
                            "THE INJECTED CODE SAYS: the method " +
                                    jp.getSignature().getName() + " failed for the input '" +
                                    jp.getArgs()[0] + "'. Original exception: " + e);
                }
            }
        });
    }

    @Pointcut("execution( OnMainCallback *(..))")
    public void runOnUI() {}

    @Around("runOnUI()")
    public void runInUIThread(final ProceedingJoinPoint jp)  {
        ThreadUtil.postOnMain(new Runnable() {
            @Override
            public void run() {
                try {
                    jp.proceed();
                } catch (Throwable e) {
                    //throw new DalolException(e);
                }
            }
        });
    }

    @Around("allMethodsPointcut()")
    public void allServiceMethodsAdvice(final ProceedingJoinPoint jp){
        System.out.println(jp.getSignature().getName()+" Before executing service method");
    }

    //Pointcut to execute on all the methods of classes in a package
    @Pointcut("execution( org.dalol.cocktailpro.processor *(..))")
    public void allMethodsPointcut(){}
}
