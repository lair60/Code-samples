describe('apiService', function() {
    var svc, $httpBackend;
    
    beforeEach(function() {
        module('app.services');
        
        inject(function(apiService, _$httpBackend_) {
            svc = apiService;
            $httpBackend = _$httpBackend_;
        });
    });
    
    afterEach(function() {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });
    
    describe('greeting', function() {
        it('should return the default message', function() {
            var returnData = { content : 'Hello' };
            $httpBackend.expectGET('/api/greeting').respond(returnData);
            
            var returnedPromise = svc.greeting();
            
            var result;
            returnedPromise.then(function(response) {
              result = response.data;
            });
            $httpBackend.flush();
            expect(result).toEqual(returnData);
        });
        
        it('should return a custom message for a specified name', function() {
            var returnData = { content : 'Hello Foo' };
            $httpBackend.expectGET('/api/greeting?name=Foo').respond(returnData);
            
            var returnedPromise = svc.greeting('Foo');
            
            var result;
            returnedPromise.then(function(response) {
              result = response.data;
            });
            $httpBackend.flush();
            expect(result).toEqual(returnData);
        });
    });
    
    describe('convertion', function() {
        it('should convert an arabic number', function() {
            var returnData = { romanNumber : 'CMXCIX' };
            $httpBackend.expectGET('/api/convert?arabicNumber=999').respond(returnData);
            
            var returnedPromise = svc.convertion('999');
            
            var result;
            returnedPromise.then(function(response) {
              result = response.data.romanNumber;
            });
            $httpBackend.flush();
            expect(result).toEqual(returnData.romanNumber);
        });
        it('should show an error message', function() {
            var returnData = { errorMessage : 'Invalid Arabic number. It should be between 1..2147483647' };
            $httpBackend.expectGET('/api/convert?arabicNumber=-1').respond(returnData);
            
            var returnedPromise = svc.convertion('-1');
            
            var result;
            returnedPromise.then(function(response) {
              result = response.data.errorMessage;
            });
            $httpBackend.flush();
            expect(result).toEqual(returnData.errorMessage);
        });
    });

});
