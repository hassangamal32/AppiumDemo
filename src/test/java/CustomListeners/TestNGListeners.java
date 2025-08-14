package CustomListeners;

import org.testng.*;

public class TestNGListeners implements IInvokedMethodListener, ITestListener, IExecutionListener, IRetryAnalyzer {
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if(method.isTestMethod())
            System.out.println(method.getTestMethod().getMethodName()+"Started");
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if(method.isTestMethod())
            System.out.println(method.getTestMethod().getMethodName()+"Finished");
    }

    public void onTestSuccess(ITestResult result) {
        System.out.println(result.getMethod().getMethodName()+" Passed");
    }

    public void onTestFailure(ITestResult result) {
        System.out.println(result.getMethod().getMethodName()+" Failed");
    }

    public void onTestSkipped(ITestResult result) {
        System.out.println(result.getMethod().getMethodName()+" Skipped");
    }

    public void onExecutionStart() {
        System.out.println("Execution Started");
    }

    public void onExecutionFinish() {
        System.out.println("Execution Finished");
    }

    private int attemps = 0;
    @Override
    public boolean retry(ITestResult iTestResult){
        if (iTestResult.getStatus()==ITestResult.FAILURE && attemps==0)
        {
            attemps++;
            return true;
        }
            return false;
    }

}
