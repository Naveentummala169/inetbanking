package com.inetbanking.utilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Reporting extends TestListenerAdapter {

    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest logger;

    public void onStart(ITestContext testContext) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); // Timestamp
        String repName = "Test-Report-" + timeStamp + ".html";
        String reportPath = System.getProperty("user.dir") + File.separator + "test-output" + File.separator + repName;

        // Initialize SparkReporter
        sparkReporter = new ExtentSparkReporter(reportPath);

        // Load external config if you have one (optional)
        // sparkReporter.loadXMLConfig(System.getProperty("user.dir") + "/extent-config.xml");

        // Report Configuration
        sparkReporter.config().setDocumentTitle("InetBanking Test Project");
        sparkReporter.config().setReportName("Functional Test Report");
        sparkReporter.config().setTheme(Theme.DARK);
        //sparkReporter.config().setTestViewChartLocation(ChartLocation.TOP);

        // Attach to main ExtentReports object
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // System info
        extent.setSystemInfo("Host name", "localhost");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("User", System.getProperty("user.name"));
    }

    public void onTestSuccess(ITestResult tr) {
        logger = extent.createTest(tr.getName());
        logger.log(Status.PASS, MarkupHelper.createLabel(tr.getName(), ExtentColor.GREEN));
    }

    public void onTestFailure(ITestResult tr) {
        logger = extent.createTest(tr.getName());
        logger.log(Status.FAIL, MarkupHelper.createLabel(tr.getName(), ExtentColor.RED));
        logger.log(Status.FAIL, tr.getThrowable()); // Log the exception

        String screenshotPath = System.getProperty("user.dir") + File.separator + "Screenshots" + File.separator + tr.getName() + ".png";
        File f = new File(screenshotPath);

        if (f.exists()) {
            logger.fail("Screenshot is below:");
			logger.addScreenCaptureFromPath(screenshotPath);
        }
    }

    public void onTestSkipped(ITestResult tr) {
        logger = extent.createTest(tr.getName());
        logger.log(Status.SKIP, MarkupHelper.createLabel(tr.getName(), ExtentColor.ORANGE));
        logger.log(Status.SKIP, tr.getThrowable()); // Optional: log why it was skipped
    }

    public void onFinish(ITestContext testContext) {
        extent.flush(); // Write everything to the report
    }
}
