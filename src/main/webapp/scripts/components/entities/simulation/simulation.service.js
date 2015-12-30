'use strict';

angular.module('healthPlanPickerApp')
    .factory('Simulation', function ($resource, DateUtils) {
        return $resource('api/simulations/:id', {}, {
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
