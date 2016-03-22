package kr.pe.lahuman;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

/**
 * Created by lahuman on 2016. 2. 4..
 */
public class KnouQnA {
    public static String CNSL_DC = "604" ;
    public static String QUES_NO = "903524";
    private int MAX_PAGE_NO =4;
    public static final String baseUrl = "http://portal.knou.ac.kr/portal/eco/cnslusr/retrieveCnslDetailContentScreen.do?cnsl_dc="+ CNSL_DC;

//    http://portal.knou.ac.kr/portal/eco/cnslusr/retrieveCnslDetailContentScreen.do?cnsl_dc=604&ques_no=903524&dpgubun=&dp=&category=&epTicket=LOG


    private static WebDriver driver = null;
    private static JavascriptExecutor js = null;



    private WritableWorkbook workbook = null;
    private WritableSheet sheet = null;
    private int rowCount = 0;

    @BeforeClass
    public static void login(){
//        System.setProperty("webdriver.ie.driver", "d:\\IEDriverServer.exe");
        System.setProperty("webdriver.firefox.bin","D:\\Program Files (x86)\\Mozilla Firefox\\Firefox.exe");
//        driver = new InternetExplorerDriver();
//        driver.manage().window().maximize();
        open(baseUrl+ "&ques_no="+QUES_NO+"&pageNo=1");
        driver = WebDriverRunner.getWebDriver();
//        driver.navigate().to(baseUrl+ "&ques_no=908734&pageNo=1");
        js = (JavascriptExecutor)driver;

    }

    private Queue<String> array = new LinkedList<>();

    @Test
    public void getContents() throws InterruptedException, IOException, WriteException {

        //초기화
        SelenideElement list = $("div.boardleftbox", 2);
        int listCount = list.$$("table > tbody > tr").size();
        workbook = Workbook.createWorkbook(new File("d:\\rnouQnA.xls"));
        sheet = workbook.createSheet("입학관련", 1);
        makeExcelRow("분류", "신청인", "신청일", "제목", "조회수", "내용", "답변자/소속","답변일", "제목", "내용");

        for(int f=1; f<=MAX_PAGE_NO; f++) {//페이지 로테이션
            driver.get(baseUrl+"&ques_no="+QUES_NO+"&pageNo="+f);
            for (int i = 0; i < listCount; i++) {
                try {
                    array.add(list.$("table > tbody > tr", i).$("td > a").attr("href").replace("javascript:go_fn(" + CNSL_DC + ",", "").replace(")", ""));
                    saveContents(f);
                }catch (Exception e){}
            }
        }

        workbook.write();
        workbook.close();
    }

    private void makeExcelRow(String... param) throws WriteException {

        for(int i=0; i< param.length; i++){

            Label label = new Label( i,rowCount,  param[i]);
            sheet.addCell(label);
        }

        ++rowCount;
    }

    private void saveContents(int f) throws WriteException {
        driver.get(baseUrl+"&ques_no="+array.poll()+"&pageNo="+f);

        SelenideElement questionContents = $("div.boardleftbox", 0).waitUntil(Condition.visible, 2000); //질문
        SelenideElement answerContents = $("div.boardleftbox", 1); //답변
        if(!"".equals(answerContents.getText())) {//답변이 있을 경우
            makeExcelRow(questionContents.$("table > tbody > tr", 0).$("td", 1).getText(),
                    questionContents.$("table > tbody > tr", 1).$("td", 1).getText(), questionContents.$("table > tbody > tr", 1).$("td", 3).getText(),
                    questionContents.$("table > tbody > tr", 2).$("td", 1).getText(),
                    questionContents.$("table > tbody > tr", 2).$("td", 3).getText(),
                    questionContents.$("table > tbody > tr", 3).$("td", 0).getText(),

                    answerContents.$("table > tbody > tr", 0).$("td", 1).getText(),
                    answerContents.$("table > tbody > tr", 0).$("td", 3).getText(),
                    answerContents.$("table > tbody > tr", 1).$("td", 1).getText(),
                    answerContents.$("table > tbody > tr", 2).$("td", 0).getText());
        }else{
            makeExcelRow(questionContents.$("table > tbody > tr", 0).$("td", 1).getText(),
                    questionContents.$("table > tbody > tr", 1).$("td", 1).getText(), questionContents.$("table > tbody > tr", 1).$("td", 3).getText(),
                    questionContents.$("table > tbody > tr", 2).$("td", 1).getText(),
                    questionContents.$("table > tbody > tr", 2).$("td", 3).getText(),
                    questionContents.$("table > tbody > tr", 3).$("td", 0).getText());
        }
    }
}
