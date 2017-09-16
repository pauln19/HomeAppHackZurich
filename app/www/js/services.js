angular.module('app.services', [])

.service('Activate', function($http) {
  this.doSomething = function(id) {
    return $http({
      method: 'GET',
      url: 'https://www.google.ch/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png',
    }).success(function(data){
      console.log('http request done');
      return data;
    }).error(function(){
      alert("Error");
      return null ;
    });
  }
})
