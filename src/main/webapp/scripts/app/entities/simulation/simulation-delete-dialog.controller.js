'use strict';

angular.module('healthPlanPickerApp')
	.controller('SimulationDeleteController', function($scope, $uibModalInstance, entity, Simulation) {

        $scope.simulation = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Simulation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
