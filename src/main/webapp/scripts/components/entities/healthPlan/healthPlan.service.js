'use strict';

angular.module('healthPlanPickerApp')
    .factory('HealthPlan', function ($resource, DateUtils) {
        return $resource('api/healthPlans/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
