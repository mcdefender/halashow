package com.stepout.controller;

import java.util.ArrayList;
import java.util.List;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.RepositoryDirectory;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Line;


@RestController
public class HelloWorldController {
	private static String transName = "test";
	
    @RequestMapping("/hello")
    public String index() throws KettleException {
    	KettleEnvironment.init();
    	KettleDatabaseRepository repository = new KettleDatabaseRepository();


    	DatabaseMeta dataMeta = 
    			new DatabaseMeta("a","Oracle","Native","10.143.3.17","ora2","1521","ias","ias"); 
    	
    	KettleDatabaseRepositoryMeta kettleDatabaseMeta = 
    			new KettleDatabaseRepositoryMeta("a", "a", "king description",dataMeta);
    	repository.init(kettleDatabaseMeta);
    	repository.connect("admin","admin");
    	
    	
    	RepositoryDirectoryInterface directory = repository.loadRepositoryDirectoryTree();//new RepositoryDirectory(); //repository.findDirectory("/enfo_worker/wxj");
    	//directory.setObjectId(repository.getRootDirectoryID()); 
    	directory = directory.findDirectory("/");
    	TransMeta transformationMeta = ((Repository) repository).loadTransformation(transName, directory, null, true, null ) ;

    	Trans trans = new Trans(transformationMeta);
    	trans.setVariable("summ_date", "20170823");
    	trans.execute(null);
    	
    	trans.waitUntilFinished();

        return "Hello World";
    }
    
    @RequestMapping("/test")
    public String test() {
        //地址:http://echarts.baidu.com/doc/example/line5.html
    	GsonOption option = new GsonOption();
        option.legend("高度(km)与气温(°C)变化关系");

        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar), Tool.restore, Tool.saveAsImage);

        option.calculable(true);
        option.tooltip().trigger(Trigger.axis).formatter("Temperature : <br/>{b}km : {c}°C");

        ValueAxis valueAxis = new ValueAxis();
        valueAxis.axisLabel().formatter("{value} °C");
        option.xAxis(valueAxis);

        CategoryAxis categoryAxis = new CategoryAxis();
        categoryAxis.axisLine().onZero(false);
        categoryAxis.axisLabel().formatter("{value} km");
        categoryAxis.boundaryGap(false);
        //List<String> a = new ArrayList<String>();
        categoryAxis.data(0, 10, 20, 30, 40, 50, 60, 70, 80);
        //categoryAxis.data().add(e)
        option.yAxis(categoryAxis);

        Line line = new Line();
        line.smooth(true).name("高度(km)与气温(°C)变化关系").data(15, -50, -56.5, -46.5, -22.1, -2.5, -27.7, -55.7, -76.5).itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0.4)");
        option.series(line);
        //option.exportToHtml("line5.html");
        return option.toPrettyString();
    }
}
