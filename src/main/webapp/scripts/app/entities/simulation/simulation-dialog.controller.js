'use strict';

angular.module('healthPlanPickerApp').controller('SimulationDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Simulation', 'User', 'HealthPlan', 'Scenario',
        function($scope, $stateParams, $uibModalInstance, entity, Simulation, User, HealthPlan, Scenario) {

        $scope.simulation = entity;
        $scope.users = User.query();
        $scope.healthplans = HealthPlan.query();
        $scope.scenarios = Scenario.query();
        $scope.load = function(id) {
            Simulation.get({id : id}, function(result) {
                $scope.simulation = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('healthPlanPickerApp:simulationUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.simulation.id != null) {
                Simulation.update($scope.simulation, onSaveSuccess, onSaveError);
            } else {
                Simulation.save($scope.simulation, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
