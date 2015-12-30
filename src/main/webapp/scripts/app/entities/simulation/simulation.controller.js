'use strict';

angular.module('healthPlanPickerApp')
    .controller('SimulationController', function ($scope, $state, Simulation) {

        $scope.simulations = [];
        $scope.loadAll = function() {
            Simulation.query(function(result) {
               $scope.simulations = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.simulation = {
                name: null,
                id: null
            };
        };
    });
