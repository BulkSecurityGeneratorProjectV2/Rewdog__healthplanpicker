'use strict';

angular.module('healthPlanPickerApp').controller('ScenarioDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Scenario',
        function($scope, $stateParams, $uibModalInstance, entity, Scenario) {

        $scope.scenario = entity;
        $scope.load = function(id) {
            Scenario.get({id : id}, function(result) {
                $scope.scenario = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('healthPlanPickerApp:scenarioUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.scenario.id != null) {
                Scenario.update($scope.scenario, onSaveSuccess, onSaveError);
            } else {
                Scenario.save($scope.scenario, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
