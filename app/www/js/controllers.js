angular.module('starter.controllers', [])

.controller('AppCtrl', function($scope, $ionicModal, $timeout, Objects) {

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

.controller('ObjectsCtrl', function($scope, Objects) {
  $scope.objects = Objects.all();

  $scope.object = Objects.object;

  $scope.getControls = function(id){
    $scope.objectId = id;
    $scope.controls = Objects.getControls(id);
    console.log($scope.controls);
    return null;
  }
})

.controller('ObjectCtrl', function($scope, $stateParams, $http, Objects) {

  $scope.activate = function(id2,status){
    Objects.editControls(Objects.object.id,id2);
    if (id2 === 0 && Objects.object.power === true){
      return $http({
        method: 'GET',
        url: 'http://localhost:8080/camera',
      }).success(function(data){
        console.log(data.data);
        $scope.image = data.data;
        return data.data;
      }).error(function(){
        alert("Error");
        return null ;
      });
    }
  }
})

.controller('AddCtrl', function($scope, $stateParams, Objects) {
  $scope.add = function(selection, subselection) {
    Objects.add(selection, subselection);
  }
})

.controller('RemoveCtrl', function($scope, $stateParams) {

  $scope.remove = function(item) {
  var index = $scope.objects.indexOf(item);
  $scope.bdays.splice(index, 1);
}

});
