package org.vl.bytebuddy;

import static net.bytebuddy.matcher.ElementMatchers.any;

import java.lang.instrument.Instrumentation;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.agent.builder.AgentBuilder.InitializationStrategy.SelfInjection.Eager;
import net.bytebuddy.asm.Advice;
import org.vl.bytebuddy.experimental.Test1;
import org.vl.bytebuddy.experimental.Test2;

public class Main {

  public static void premain(String agentOps, Instrumentation inst) {
    instrument(agentOps, inst);
  }

  public static void agentmain(String agentOps, Instrumentation inst) {
    instrument(agentOps, inst);
  }

  private static void instrument(String agentOps, Instrumentation inst) {
    new AgentBuilder.Default()
        .with(new Eager())
//        .disableClassFormatChanges()
        .type((any()))
        .transform((builder, typeDescription, classLoader, module) ->
            builder
                .method(any())
                .intercept(Advice.to(LoggingAdvice.class)))
        .installOn(inst);
  }

  public static class LoggingAdvice {

    @Advice.OnMethodEnter
    static void enter(@Advice.Origin String method) {
      System.out.println("enter");
    }

    @Advice.OnMethodExit
    static void exit(@Advice.Origin String method) {
      System.out.println("exit");
    }
  }

  public static void main(String[] args) {
    premain("", ByteBuddyAgent.install());

    new Test1().test();
    new Test2().test();
  }
}
