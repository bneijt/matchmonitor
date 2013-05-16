function MonitorCtrl($scope, $http, $timeout) {
    $scope.states = [];
    $scope.updates = 0;


    $scope.scheduleUpdate = function () {
        updateTimer = $timeout($scope.onUpdate, 5000);
        $scope.updates += 1;
    }

    function storeState(response) {

        $scope.states = response.states.map(function (state) {
            if (state.alive) {
                state.class = "alive";
            } else {
                state.class = "notAlive";
            }
            return state;
        });

        $scope.lastUpdate = response.lastUpdate;
    }

    $scope.onUpdate = function () {
        $http.get("api/overview")
            .success(function (response) {
                $scope.connectionState = "active";
                storeState(response);
                $scope.scheduleUpdate()
            })
            .error(function (response) {
                $scope.connectionState = "inactive";
                $scope.scheduleUpdate()
            });
    }

    var updateTimer = $timeout($scope.onUpdate, 5000);

    $scope.stop = function () {
        $timeout.cancel(updateTimer);
    }

}