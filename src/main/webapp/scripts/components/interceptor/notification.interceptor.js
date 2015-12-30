 'use strict';

angular.module('healthPlanPickerApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-healthPlanPickerApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-healthPlanPickerApp-params')});
                }
                return response;
            }
        };
    });
