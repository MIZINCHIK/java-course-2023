package edu.hw2.task4;

public record CallingInfo(String className, String methodName) {
    public static CallingInfo callingInfo() {
        StackTraceElement lastCall;
        try {
            throw new Throwable();
        } catch (Throwable e) {
            lastCall = e.getStackTrace()[1];
        }
        return new CallingInfo(lastCall.getClassName(), lastCall.getMethodName());
    }
}
