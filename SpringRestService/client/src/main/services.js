angular.module('app.services', [])
.factory('apiService', ['$http', function($http) {
    
    var apiEndpoint = '/api';
    
    var greeting = function(name) {
        var config = {};
        if (name) { config.params = { 'name' : name }; }
        return $http.get(apiEndpoint + '/greeting', config);
    };
    
    var convertion = function(arabicNumber) {
        var config = {};
        if (arabicNumber) { config.params = { 'arabicNumber' : arabicNumber }; }
        return $http.get(apiEndpoint + '/convert', config);
    };
    
    // public API
    return {
        greeting : greeting,
        convertion : convertion
    };
} ]);
