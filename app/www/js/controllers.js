angular.module('starter.controllers', [])

.controller('AppCtrl', function($scope, $ionicModal, $timeout) {

  // With the new view caching in Ionic, Controllers are only called
  // when they are recreated or on app start, instead of every page change.
  // To listen for when this page is active (for example, to refresh data),
  // listen for the $ionicView.enter event:
  //$scope.$on('$ionicView.enter', function(e) {
  //});

  // Form data for the login modal
  $scope.loginData = {};

  // Create the login modal that we will use later
  $ionicModal.fromTemplateUrl('templates/login.html', {
    scope: $scope
  }).then(function(modal) {
    $scope.modal = modal;
  });

  // Triggered in the login modal to close it
  $scope.closeLogin = function() {
    $scope.modal.hide();
  };

  // Open the login modal
  $scope.login = function() {
    $scope.modal.show();
  };

  // Perform the login action when the user submits the login form
  $scope.doLogin = function() {
    console.log('Doing login', $scope.loginData);

    // Simulate a login delay. Remove this and replace with your login
    // code if using a login system
    $timeout(function() {
      $scope.closeLogin();
    }, 1000);
  };
})

.controller('ObjectsCtrl', function($scope) {
  $scope.objects = [
    { title: 'Main Door', id: 1 },
    { title: 'Secondary Door', id: 2},
    { title: 'Stove', id: 3 },
    { title: 'Drawer', id: 4 }
  ];
})

.controller('ObjectCtrl', function($scope, $stateParams, $http) {
  $scope.controls = [
    { title: 'Power', id: 1 },
    { title: 'Child protection', id: 2},
    { title: 'Elder protection', id: 3 }
  ];

  $scope.activate = function(id,status){
    if (id == 1 && status == true){
      return $http({
        method: 'GET',
        url: 'http://localhost:8080',
      }).success(function(data){
        console.log(data);
        return data;
      }).error(function(){
        alert("Error");
        return null ;
      });
    }
  }
});
