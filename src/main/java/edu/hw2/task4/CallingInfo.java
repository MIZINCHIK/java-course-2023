package edu.hw2.task4;

public record CallingInfo(String className, String methodName) {
    public static CallingInfo callingInfo() {
        StackTraceElement lastCall = new Throwable().getStackTrace()[1];
        return new CallingInfo(lastCall.getClassName(), lastCall.getMethodName());
    }
}
