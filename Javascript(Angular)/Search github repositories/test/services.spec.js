describe('apiService', function() {
    var svc, $httpBackend;
    
    beforeEach(function() {
        module('app.services');
        
        inject(function(apiService ,_$httpBackend_) {
            svc = apiService;
            $httpBackend = _$httpBackend_;
        });
		
    });
	
    describe('search', function() {
		 it('Searching of "bootstrap" repository returns more than 1 repo', function() {
			var name = "bootstrap";
            //$httpBackend.expectGET('https://api.github.com/search/repositories?q=bootstrap').respond(returnData);
            
            var returnedPromise = svc.search(name);
            
            var result;
			
            returnedPromise.then(function(response) {
              result = response.items.length;
			  expect(result > 0).toEqual(true);
            });
		
            //$httpBackend.flush();
		
			
        });
		it('Searching of "thereisnorepository" repository returns an empty result', function() {
			var name = "thereisnorepository";
            //$httpBackend.expectGET('https://api.github.com/search/repositories?q=bootstrap').respond(returnData);
            
            var returnedPromise = svc.search(name);
            
            var result;
			
            returnedPromise.then(function(response) {
              result = response.items.length;			  
			  expect(result).toEqual(0);
            });
            //$httpBackend.flush();
        });
		it('Searching of "bootstrap" repository returns more than 1 page(30 rows)', function() {
			var name = "bootstrap";
			var page = 2;
            //$httpBackend.expectGET('https://api.github.com/search/repositories?q=bootstrap').respond(returnData);
            
            var returnedPromise = svc.search(name , page);
            
            var result;
			
            returnedPromise.then(function(response) {
              result = response.items.length;
			  expect(result > 0).toEqual(true);
            });
            //$httpBackend.flush();
        });
	});
	describe('Issues', function() {
		 it('Searching issues from "bootstrap" repo returns more than 1 row', function() {
			var name = "bootstrap";
            //$httpBackend.expectGET('https://api.github.com/search/repositories?q=bootstrap').respond(returnData);
            
            var returnedSearchPromise = svc.search(name);
            var returnedIssuesPromise = svc.issues(result);
            var result;
			
            returnedSearchPromise.then(function(response) {
				  result = response.items[0].full_name;
				  returnedIssuesPromise.then(function(response) {
						result = response.items.length;
						expect(result > 0).toEqual(true);
				  });
            });
            //$httpBackend.flush();
        });
		it('Searching issues from "thereisnorepository" repo returns an empty result', function() {
			var name = "thereisnorepository";
            //$httpBackend.expectGET('https://api.github.com/search/repositories?q=bootstrap').respond(returnData);
            
            var returnedIssuesPromise = svc.issues(name);
            var result;
			returnedIssuesPromise.then(function(response) {
					result = response.message;
					expect(result).toEqual('Validation Failed');
			});
            //$httpBackend.flush();
        });
	});
});