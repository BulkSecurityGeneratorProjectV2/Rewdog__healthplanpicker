'use strict';

angular.module('healthPlanPickerApp')
	.controller('HealthPlanDeleteController', function($scope, $uibModalInstance, entity, HealthPlan) {

        $scope.healthPlan = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HealthPlan.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
