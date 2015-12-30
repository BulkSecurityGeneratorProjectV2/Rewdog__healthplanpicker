'use strict';

angular.module('healthPlanPickerApp').controller('HealthPlanDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'HealthPlan', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, HealthPlan, User) {

        $scope.healthPlan = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            HealthPlan.get({id : id}, function(result) {
                $scope.healthPlan = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('healthPlanPickerApp:healthPlanUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.healthPlan.id != null) {
                HealthPlan.update($scope.healthPlan, onSaveSuccess, onSaveError);
            } else {
                HealthPlan.save($scope.healthPlan, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
