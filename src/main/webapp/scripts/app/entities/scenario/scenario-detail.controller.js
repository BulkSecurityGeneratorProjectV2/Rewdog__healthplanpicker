'use strict';

angular.module('healthPlanPickerApp')
    .controller('ScenarioDetailController', function ($scope, $rootScope, $stateParams, entity, Scenario) {
        $scope.scenario = entity;
        $scope.load = function (id) {
            Scenario.get({id: id}, function(result) {
                $scope.scenario = result;
            });
        };
        var unsubscribe = $rootScope.$on('healthPlanPickerApp:scenarioUpdate', function(event, result) {
            $scope.scenario = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
