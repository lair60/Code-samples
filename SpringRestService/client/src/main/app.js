angular.module('app', ['app.services', 'templates.app']);

angular.module('app')
.controller('AppCtrl', ['$scope', 'apiService', function($scope, apiService) {

    var setGreeting = function () {
        apiService.greeting($scope.name).success(function(data) {
            $scope.greeting = data.content;
        });
    };
    
    var setRomanNumber = function (){
    	if(($scope.arabicNum > 0)&&($scope.arabicNum < 2147483648)){// MAX int
	    	apiService.convertion($scope.arabicNum).success(function(data) {
	    		document.getElementById("errorBlock").style.display = "none";
	    		document.getElementById("resultBlock1").style.display = "block";
	    		var cad =''
	    		if (data.millionRoman!=""){
	    			if (data.millionRomanUnderline){
	    				cad = "<u>"+data.millionRoman+"</u>";
	    			}
	    			else{//else it should use overline
	    				cad = "<span style='text-Decoration:overline'>"+data.millionRoman+"</span>";
	    			}
	    		}
	    		if (data.thousandRoman!=""){
	    			if (data.thousandRomanOverline){
	    				cad = cad + "<span style='text-Decoration:overline'>"+data.thousandRoman+"</span>";
	    			}
	    			else{
	    				cad = cad + data.thousandRoman;
	    			}
	    		}
	    		cad = cad + data.hundredRoman + data.tenRoman + data.unitRoman;
	    		document.getElementById("resultNumber1").innerHTML= cad;
	        });
    	}
    	else{
    		document.getElementById("resultBlock1").style.display = "none";
    		document.getElementById("errorBlock").style.display = "block";
    	}
    }

    setGreeting();

    $scope.submitClicked = function () {
        setGreeting();
    };
    
    $scope.submitClicked2 = function () {
        setRomanNumber();
    };

} ]);
