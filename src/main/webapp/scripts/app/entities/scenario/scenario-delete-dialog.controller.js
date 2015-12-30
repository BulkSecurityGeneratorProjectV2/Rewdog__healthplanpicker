'use strict';

angular.module('healthPlanPickerApp')
	.controller('ScenarioDeleteController', function($scope, $uibModalInstance, entity, Scenario) {

        $scope.scenario = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Scenario.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
