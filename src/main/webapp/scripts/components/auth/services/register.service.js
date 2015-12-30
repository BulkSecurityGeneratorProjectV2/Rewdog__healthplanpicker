'use strict';

angular.module('healthPlanPickerApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


