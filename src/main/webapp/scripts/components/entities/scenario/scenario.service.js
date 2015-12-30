'use strict';

angular.module('healthPlanPickerApp')
    .factory('Scenario', function ($resource, DateUtils) {
        return $resource('api/scenarios/:id', {}, {
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
