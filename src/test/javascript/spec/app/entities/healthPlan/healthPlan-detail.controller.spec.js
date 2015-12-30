'use strict';

describe('HealthPlan Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockHealthPlan, MockUser;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockHealthPlan = jasmine.createSpy('MockHealthPlan');
        MockUser = jasmine.createSpy('MockUser');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'HealthPlan': MockHealthPlan,
            'User': MockUser
        };
        createController = function() {
            $injector.get('$controller')("HealthPlanDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'healthPlanPickerApp:healthPlanUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
