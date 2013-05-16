function MonitorCtrl($scope, $http, $timeout) {
    $scope.states = [];
    var updates = 0;
    $scope.version = "";
    $scope.connectionState = "...";
    $scope.connectionStyle = "inactive";

    $scope.scheduleUpdate = function () {
        updateTimer = $timeout($scope.onUpdate, 5000);
        updates += 1;
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
                storeState(response);
                $scope.connectionStyle = "inactive";
                $scope.connectionState = updates + " updates";
                $scope.scheduleUpdate()
            })
            .error(function () {
                $scope.connectionStyle = "active";
                $scope.connectionState = "disconnected";
                $scope.scheduleUpdate()
            });
    }

    var updateTimer = $timeout($scope.onUpdate, 5000);

    $scope.stop = function () {
        $timeout.cancel(updateTimer);
    }

    $http.get("api/manifest")
        .success(function (response) {
            $scope.version = "v" + response["Implementation-Version"];
        });

}