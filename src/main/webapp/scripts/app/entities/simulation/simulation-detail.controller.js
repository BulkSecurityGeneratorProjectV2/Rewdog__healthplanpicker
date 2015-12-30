'use strict';

angular.module('healthPlanPickerApp')
    .controller('SimulationDetailController', function ($scope, $rootScope, $stateParams, entity, Simulation, User, HealthPlan, Scenario) {
        $scope.simulation = entity;
        $scope.load = function (id) {
            Simulation.get({id: id}, function(result) {
                $scope.simulation = result;
            });
        };
        
        $scope.newAnnualCost = function(){
            var total = 0;
            for(var i = 0; i < $scope.simulation.scenariosimulations.length; i++)
            {
                var scenario = $scope.simulation.scenariosimulations[i];
                total += scenario.annualCost;
            }            
        	
        	return $scope.simulation.healthplansimulations[0].annualCost + total;
        };
        
        var unsubscribe = $rootScope.$on('healthPlanPickerApp:simulationUpdate', function(event, result) {
            $scope.simulation = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
