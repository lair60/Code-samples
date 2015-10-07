angular.module('app.services', [])
.factory('apiService', ['$http', function($http) {
    
    var apiEndpoint = ' https://api.github.com/search';
	
	var search = function(name, page) {
        var config = {};
		(page!=""&& page!=undefined) ? config.params = { 'q' : name , 'page': page } : config.params = { 'q' : name}
        return $http.get(apiEndpoint + '/repositories', config);
    };
	
	var issues = function(name,page) {
        var config = {};
		(page!=""&& page!=undefined) ? config.params = { 'q' : "repo:"+name , 'page': page } : config.params = { 'q' : "repo:"+name}
        return $http.get(apiEndpoint + '/issues', config);
    };
    
    // public API
    return {
        search : search,
        issues : issues
    };
} ]);