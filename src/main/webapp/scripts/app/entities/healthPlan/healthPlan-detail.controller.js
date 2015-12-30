'use strict';

angular.module('healthPlanPickerApp').controller(
		'HealthPlanDetailController',
		function($scope, $rootScope, $stateParams, entity, HealthPlan, User) {
			$scope.healthPlan = entity;

			$scope.load = function(id) {
				HealthPlan.get({
					id : id
				}, function(result) {
					$scope.healthPlan = result;
				});
			};
			var unsubscribe = $rootScope.$on(
					'healthPlanPickerApp:healthPlanUpdate', function(event,
							result) {
						$scope.healthPlan = result;
					});
			$scope.$on('$destroy', unsubscribe);
			
			$scope.annualCost = function(){
				switch ($scope.healthPlan.premiumFrequency) {
				case "weekly":
					return $scope.healthPlan.premium * 52;
					break;
				case "biweekly":
					return $scope.healthPlan.premium * 26;
					break;
				case "monthly":
					return $scope.healthPlan.premium * 12;
					break;
				case "annually":
					return $scope.healthPlan.premium * 1;
					break;
				}
				return $scope.healthPlan.premium;
			};

		});
