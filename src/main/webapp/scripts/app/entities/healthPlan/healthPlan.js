'use strict';

angular.module('healthPlanPickerApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('healthPlan', {
                parent: 'entity',
                url: '/healthPlans',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'HealthPlans'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/healthPlan/healthPlans.html',
                        controller: 'HealthPlanController'
                    }
                },
                resolve: {
                }
            })
            .state('healthPlan.detail', {
                parent: 'entity',
                url: '/healthPlan/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'HealthPlan'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/healthPlan/healthPlan-detail.html',
                        controller: 'HealthPlanDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'HealthPlan', function($stateParams, HealthPlan) {
                        return HealthPlan.get({id : $stateParams.id});
                    }]
                }
            })
            .state('healthPlan.new', {
                parent: 'healthPlan',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/healthPlan/healthPlan-dialog.html',
                        controller: 'HealthPlanDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
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
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('healthPlan', null, { reload: true });
                    }, function() {
                        $state.go('healthPlan');
                    })
                }]
            })
            .state('healthPlan.edit', {
                parent: 'healthPlan',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/healthPlan/healthPlan-dialog.html',
                        controller: 'HealthPlanDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['HealthPlan', function(HealthPlan) {
                                return HealthPlan.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('healthPlan', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('healthPlan.delete', {
                parent: 'healthPlan',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/healthPlan/healthPlan-delete-dialog.html',
                        controller: 'HealthPlanDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HealthPlan', function(HealthPlan) {
                                return HealthPlan.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('healthPlan', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
