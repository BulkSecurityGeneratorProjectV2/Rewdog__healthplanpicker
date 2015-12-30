'use strict';

describe('Simulation Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockSimulation, MockUser, MockHealthPlan, MockScenario;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockSimulation = jasmine.createSpy('MockSimulation');
        MockUser = jasmine.createSpy('MockUser');
        MockHealthPlan = jasmine.createSpy('MockHealthPlan');
        MockScenario = jasmine.createSpy('MockScenario');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Simulation': MockSimulation,
            'User': MockUser,
            'HealthPlan': MockHealthPlan,
            'Scenario': MockScenario
        };
        createController = function() {
            $injector.get('$controller')("SimulationDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'healthPlanPickerApp:simulationUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
