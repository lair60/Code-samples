var app = angular.module('myApp', ['app.services']);

var pag = 1;
var max_pag = 0;

var issues_pag = 1;
var max_issues = 0;

var last_word;
var arrayValues = [['x'], ['Score']];

var drawChart =function (){
	 c3.generate({
		bindto: '#chart_div',
		data: {
			x : 'x',
			columns: arrayValues,
			groups: [
				['Score']
			],
			type: 'bar'
		},
		axis: {
			x: {
				type: 'category'
			}
		}
	 });
}
var updateChart = function (items){
	arrayValues = [['x'], ['Score']];
	for(var i=0;i<items.length;i++){
		arrayValues[0].push(items[i].full_name);
		arrayValues[1].push(items[i].score);
	}
}

$(".chart_popup").click(function(event){
	event.stopPropagation();
	if(event.target.id=="chart_popup"){
		$(".chart_container").hide();
	}
})

					 
app.controller('customersCtrl',['$scope','apiService', function($scope, apiService) {
	
	$scope.search= function(U_Name){
		if((U_Name != "") && (U_Name != undefined)){
			$('#search_button').html('Searching..');
			$('#search_button').attr('disabled',true);
			$scope.clean();
			apiService.search(U_Name).success(function (response) {
					$('#search_button').html('Search');
					$('#search_button').attr('disabled',false);
					last_word = U_Name;
					$("#table_results").show();
					$(".repository_pager").show();
					$(".repository_pager .previous").addClass("disabled");  			
					(response.total_count / 30 >= 1000) ? max_pag = 34 : max_pag = Math.ceil(response.total_count / 30);
					if (max_pag <= 1){
						$(".repository_pager .next").addClass("disabled");  
					}
					else{
						$(".repository_pager .next").removeClass("disabled");
					}
					$scope.results = response.items;	
					updateChart(response.items);
			});
		}
	};
	
    $scope.previous= function(){
		if(!$(".repository_pager .previous").hasClass("disabled")){
			apiService.search(last_word,--pag).success(function (response) {
				$("#blockIssues").hide();
				$(".issues_pager").hide();
				if (pag <= 1){
					$(".repository_pager .previous").addClass("disabled");  
				}
				$scope.results = response.items;
				updateChart(response.items);
			}).
			  error(function(data, status, headers, config) {
				  pag++;
			  });
		}
	};
	$scope.next= function(){
		if(!$(".repository_pager .next").hasClass("disabled")){
			apiService.search(last_word,++pag).success(function (response) {
				$("#blockIssues").hide();
				$(".issues_pager").hide();
				$(".repository_pager .previous").removeClass("disabled");
				if (pag==max_pag){
					$(".repository_pager .next").addClass("disabled");  
				}
				$scope.results = response.items;
				updateChart(response.items);
			}).
			  error(function(data, status, headers, config) {
				  pag--;
			  });
		}
	};
	
	$scope.previousIssues= function(){
		if(!$(".issues_pager .previous").hasClass("disabled")){
			apiService.issues($scope.issues_from,--issues_pag).success(function (response) {
				if (issues_pag <= 1){
					$(".issues_pager .previous").addClass("disabled");  
				}
				$scope.issuesResults = response.items;
			}).
			  error(function(data, status, headers, config) {
				  issues_pag++;
			  });
		}
	};
	$scope.nextIssues= function(){
		if(!$(".issues_pager .next").hasClass("disabled")){
			apiService.issues($scope.issues_from,++issues_pag).success(function (response) {
				$(".issues_pager .previous").removeClass("disabled");
				issues_pag++;
				if (issues_pag==max_issues){
					$(".issues_pager .next").addClass("disabled");  
				}
				$scope.issuesResults = response.items;
			}).
			  error(function(data, status, headers, config) {
				  issues_pag--;
			  });
		}
	};
	
	$scope.openIssues= function(name){	
		apiService.issues(name).success(function (response) {
			$scope.issues_from = name;
			$("#blockIssues").show();
			$(".issues_pager").show();
			$(".issues_pager .previous").addClass("disabled");  
			(response.total_count / 30 >= 1000) ? max_issues = 34 : max_issues = Math.ceil(response.total_count / 30);
			if (max_issues <= 1){
				$(".issues_pager .next").addClass("disabled");  
			}
			else{
				$(".issues_pager .next").removeClass("disabled");
			}
			$scope.issuesResults = response.items;
		});
	};
	
	$scope.showChart = function(){	
		$(".chart_container").show();
		drawChart();
	}
	$scope.clean = function(){	
		$("#table_results").hide();
		$(".repository_pager").hide();
		$scope.U_Name = ""
		$("#blockIssues").hide();
		$(".issues_pager").hide();
	};
}]);
