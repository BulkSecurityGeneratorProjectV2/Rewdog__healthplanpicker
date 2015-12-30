'use strict';

angular.module('healthPlanPickerApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('simulation', {
                parent: 'entity',
                url: '/simulations',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Simulations'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/simulation/simulations.html',
                        controller: 'SimulationController'
                    }
                },
                resolve: {
                }
            })
            .state('simulation.detail', {
                parent: 'entity',
                url: '/simulation/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Simulation'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/simulation/simulation-detail.html',
                        controller: 'SimulationDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Simulation', function($stateParams, Simulation) {
                        return Simulation.get({id : $stateParams.id});
                    }]
                }
            })
            .state('simulation.new', {
                parent: 'simulation',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/simulation/simulation-dialog.html',
                        controller: 'SimulationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('simulation', null, { reload: true });
                    }, function() {
                        $state.go('simulation');
                    })
                }]
            })
            .state('simulation.edit', {
                parent: 'simulation',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/simulation/simulation-dialog.html',
                        controller: 'SimulationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Simulation', function(Simulation) {
                                return Simulation.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('simulation', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('simulation.delete', {
                parent: 'simulation',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/simulation/simulation-delete-dialog.html',
                        controller: 'SimulationDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Simulation', function(Simulation) {
                                return Simulation.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('simulation', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
