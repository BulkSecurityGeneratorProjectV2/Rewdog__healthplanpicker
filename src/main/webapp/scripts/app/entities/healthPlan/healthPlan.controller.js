'use strict';

angular.module('healthPlanPickerApp')
    .controller('HealthPlanController', function ($scope, $state, HealthPlan) {

        $scope.healthPlans = [];
        $scope.loadAll = function() {
            HealthPlan.query(function(result) {
               $scope.healthPlans = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.healthPlan = {
                name: null,
                premium: null,
                premiumFrequency: null,
                type: null,
                deductible: null,
                oopMax: null,
                coinsurance: null,
                employerHSAContribution: null,
                copayPCP: null,
                copayER: null,
                copayPharmacy: null,
                id: null,                
            };
        };
    });
