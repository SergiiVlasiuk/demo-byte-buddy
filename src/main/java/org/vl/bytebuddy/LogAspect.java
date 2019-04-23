package org.vl.bytebuddy;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.util.Arrays;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.agent.builder.AgentBuilder.Transformer;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.implementation.bind.annotation.Origin;

public class LogAspect {

  public static void main(String[] args) {
    premain("", ByteBuddyAgent.install());

    Calculator calculator = new Calculator();

    int sum = calculator.sum(10, 15, 20);
    System.out.println("sum is " + sum);
  }

  public static void premain(String agentOps, Instrumentation inst) {
    instrument(agentOps, inst);
  }

  public static void agentmain(String agentOps, Instrumentation inst) {
    instrument(agentOps, inst);
  }

  private static void instrument(String agentOps, Instrumentation inst) {
////    new AgentBuilder.Default()
//    new ByteBuddy()
////        .redefine(type -> type.getSimpleName().equals("Calculator"))
////        .rebase(type -> type.getSimpleName().equals("Calculator"))
//        .rebase(type -> type.getSimpleName().equals("Calculator"))
//        .transform((Transformer) (builder, typeDescription, classLoader, module) -> builder
//            .method(method -> method.getDeclaredAnnotations().isAnnotationPresent(Log.class))
//            .intercept(MethodDelegation.to(LogAspect.class).andThen(SuperMethodCall.INSTANCE)))
//        .installOn(inst);
////    new AgentBuilder.Default()
////        .with(new Eager())
////        .disableClassFormatChanges()
////        .type((any()))
////        .transform((builder, typeDescription, classLoader, module) ->
////            builder.method(any()).intercept(Advice.to(LoggingAdvice.class)))
////        .installOn(inst);
  }

  public static void intercept(@Origin Method method) {
    System.out.println(method.getName() + " was called.");
  }
}

@interface Log {

}

class Calculator {

  @Log
  public int sum(int... values) {
    return Arrays.stream(values).sum();
  }
}