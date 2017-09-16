var list = []

angular.module('starter.services', [])


  .factory('Objects', function () {

    var objects = [
    ];

    var object;

    return {
      all: function() {
        return objects;
      },
      add: function(object, subobject) {
        var newObject = {
          title: object, subtitle: subobject, id: objects.length+1, power: false, childProtection: false, elderProtection: false
        };
        console.log(newObject);
        objects.push(newObject);
      },
      remove: function(object) {
        objects.splice(chats.indexOf(object), 1);
      },
      get: function(id) {
        for (var i = 0; i < objects.length; i++) {
          if (objects[i].id === parseInt(id)) {
            this.object = objects[i];
            return objects[i];
          }
        }
        return null;
      },
      getControls: function(id) {
        for (var i = 0; i < objects.length; i++) {
          if (objects[i].id === parseInt(id)) {
            this.object = objects[i];
            return [objects[i].power, objects[i].childProtection, objects[i].elderProtection];
          }
        }
        return null;
      },
      editControls: function(id, id2) {
        for (var i = 0; i < objects.length; i++) {
          if (objects[i].id === parseInt(id)) {
            this.object = objects[i];
            switch (id2) {
              case 0:
                objects[i].power = !objects[i].power;
                break;
              case 1:
                objects[i].childProtection = !objects[i].childProtection;
                break;
              case 2:
                objects[i].elderProtection = !objects[i].elderProtection;
                break;
            }
            return null;
          }
        }
      }

    };

  });
