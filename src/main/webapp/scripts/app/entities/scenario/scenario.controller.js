'use strict';

angular.module('healthPlanPickerApp')
    .controller('ScenarioController', function ($scope, $state, Scenario) {

        $scope.scenarios = [];
        $scope.loadAll = function() {
            Scenario.query(function(result) {
               $scope.scenarios = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.scenario = {
                name: null,
                type: null,
                cost: null,
                frequency: null,
                id: null
            };
        };
    });
