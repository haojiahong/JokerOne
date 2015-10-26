<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<html>
<head>
<title>用户统计</title>
<%@ include file="/jsp/public/commons.jspf"%>
</head>

<script src="<%=path%>/js/jquery-ui.min.js"></script>
<script src="<%=path%>/js/echarts/echarts-all.js"></script>
<script type="text/javascript">
	$(function() {
		getChartInfo();
	})
</script>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div id="myChart" style="height: 100%; width: 100%"></div>
	</div>
<script type="text/javascript">
	var myChart = echarts.init(document.getElementById('myChart'),"macarons");
	option = {
			 title : {
			        text: '用户统计',
			        x:'center'
			 },
			 tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			 },
			 toolbox: {
        		show : true,
        		feature : {
           			mark : {show: true},
            		dataView : {show: true, readOnly: false},
            		restore : {show: true},
            		saveAsImage : {show: true}
        		}
        	},
		    calculable : true,
		    legend : {
		    	  orient : 'vertical',
		          x : 'left', 	
		          data:['男用户数量','女用户数量']
		    },
		    series : [
		        {
		            name:'个数',
		            type:'pie',
	                radius : '55%',
	                center: ['50%', '60%']
		        }
		    ]
		};
		                    

	function buildChart(chart,dataArr) {
		var optionX = jQuery.extend({}, option);
		optionX.series[0]['data'] = dataArr;
		chart.setOption(optionX);
	}

	function getChartInfo() {
		$.ajax({
			url : "userEchartAction!retrieveUserNums.do",
			type : "post",
			dataType : "json",
			success : function(result) {
				var maleNum = result.resultData.userMaleNum;
				var femaleNum = result.resultData.userFemaleNum;
				var dataArr = [];
				dataArr.push({value:maleNum,name:'男用户数量'});
				dataArr.push({value:femaleNum,name:'女用户数量'});
				buildChart(myChart,dataArr);
			},
			error:function(result){
			}
		});
	}
	
	
</script>
</body>
</html>
