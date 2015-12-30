'use strict';

angular.module('healthPlanPickerApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('scenario', {
                parent: 'entity',
                url: '/scenarios',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Scenarios'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/scenario/scenarios.html',
                        controller: 'ScenarioController'
                    }
                },
                resolve: {
                }
            })
            .state('scenario.detail', {
                parent: 'entity',
                url: '/scenario/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Scenario'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/scenario/scenario-detail.html',
                        controller: 'ScenarioDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Scenario', function($stateParams, Scenario) {
                        return Scenario.get({id : $stateParams.id});
                    }]
                }
            })
            .state('scenario.new', {
                parent: 'scenario',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/scenario/scenario-dialog.html',
                        controller: 'ScenarioDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    type: null,
                                    cost: null,
                                    frequency: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('scenario', null, { reload: true });
                    }, function() {
                        $state.go('scenario');
                    })
                }]
            })
            .state('scenario.edit', {
                parent: 'scenario',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/scenario/scenario-dialog.html',
                        controller: 'ScenarioDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Scenario', function(Scenario) {
                                return Scenario.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('scenario', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('scenario.delete', {
                parent: 'scenario',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/scenario/scenario-delete-dialog.html',
                        controller: 'ScenarioDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Scenario', function(Scenario) {
                                return Scenario.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('scenario', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
