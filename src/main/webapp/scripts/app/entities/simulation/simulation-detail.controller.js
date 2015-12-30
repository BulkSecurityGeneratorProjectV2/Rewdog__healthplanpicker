'use strict';

angular.module('healthPlanPickerApp')
    .controller('SimulationDetailController', function ($scope, $rootScope, $stateParams, entity, Simulation, User, HealthPlan, Scenario) {
        $scope.simulation = entity;
        $scope.load = function (id) {
            Simulation.get({id: id}, function(result) {
                $scope.simulation = result;
            });
        };
        var unsubscribe = $rootScope.$on('healthPlanPickerApp:simulationUpdate', function(event, result) {
            $scope.simulation = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
