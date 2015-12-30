'use strict';

angular.module('healthPlanPickerApp')
    .controller('HealthPlanDetailController', function ($scope, $rootScope, $stateParams, entity, HealthPlan, User) {
        $scope.healthPlan = entity;
        $scope.annualCost = function(frequency, premium) {
        	switch (frequency) {
            case "weekly":                
                return premium * 52;
                break;
            case "biweekly":
                return premium * 26;
                break;
            case "monthly":
                return premium * 12;
                break;
            case "annually":
                return premium * 1;
                break;            
        }
        	
        return value * -1; };
        
        
        
        $scope.load = function (id) {
            HealthPlan.get({id: id}, function(result) {
                $scope.healthPlan = result;
            });
        };
        var unsubscribe = $rootScope.$on('healthPlanPickerApp:healthPlanUpdate', function(event, result) {
            $scope.healthPlan = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
