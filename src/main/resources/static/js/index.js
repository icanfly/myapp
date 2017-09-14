var defaultLatitude = 37.7577;
var defaultLongitude = -122.4376;
var baiduMap;
var searchMeters = 1000;
var querySize = 10;
var source = "offline";
var defaultSearchMeters = 1000;
var defaultQuerySize = 10;
var currentLatitude = defaultLatitude;
var currentLongitude = defaultLatitude;

$(document).ready(function () {
    initControl();
    initBaiduMap();
    
});

function initControl() {
    source = $('input:radio[name="source"]:checked').val();
    $('input:radio[name="source"]').click(function(){
        source = $(this).val();
    });

    searchMeters = $("#meters").val();
    if(!searchMeters){
        searchMeters = defaultSearchMeters;
        $("#meters").val(searchMeters);
    }
    $("#meters").change(function () {
        searchMeters = $(this).val();
        if(!searchMeters){
            searchMeters = defaultSearchMeters;
        }
        $(this).attr('placeholder','当前值:'+searchMeters);
    });

    querySize = $("#querySize").val();
    if(!querySize){
        querySize = defaultQuerySize;
        $("#querySize").val(querySize);
    }
    $("#querySize").change(function () {
        querySize = $(this).val();
        if(!querySize){
            querySize = defaultQuerySize;
        }
        $(this).attr('placeholder','当前值:'+querySize);
    });
}

function initBaiduMap() {
    // 百度地图API功能
    baiduMap = new BMap.Map("mapDiv");
    var point = new BMap.Point(defaultLongitude, defaultLatitude);
    baiduMap.centerAndZoom(point, 15);
    //baiduMap.addEventListener("click", loadFootTruck);
    baiduMap.addEventListener("rightclick", initLocation);

    addMenu();

    loadNearFootTruck(defaultLatitude, defaultLongitude, searchMeters, querySize);
}

function initLocation(e) {
    currentLatitude = e.point.lat;
    currentLongitude = e.point.lng;
}

function addMenu() {
    var menu = new BMap.ContextMenu();
    var txtMenuItem = [
        {
            text: '搜索附近餐车',
            callback: function () {
                var point = new BMap.Point(currentLongitude, currentLatitude);
                baiduMap.centerAndZoom(point, 15);
                loadNearFootTruck(currentLatitude, currentLongitude, searchMeters, querySize);
            }
        }
    ];
    for (var i = 0; i < txtMenuItem.length; i++) {
        menu.addItem(new BMap.MenuItem(txtMenuItem[i].text, txtMenuItem[i].callback, 100));
    }
    baiduMap.addContextMenu(menu);
}

// function loadFootTruck(e) {
//     loadNearFootTruck(e.point.lat, e.point.lng, searchMeters, querySize);
// }

function loadNearFootTruck(latitude, longitude, meters, querySize) {

    //加载层
    var loading = $("#mapDiv");
    loading.showLoading();

    //异步发送ajax请求后端服务器 获取附近周围的餐车
    $.ajax({
        type: "post",
        url: "/foot-truck/search",
        timeout: 20000, //超时时间设置，单位毫秒
        data: {
            "latitude": latitude,
            "longitude": longitude,
            "meters": meters,
            "querySize": querySize,
            "source": source
        },
        success: function (data) {
            loading.hideLoading();
            if (data.resultCode != 'success') {
                alert("ERROR:" + data.message);
                return;
            } else if (data.data == null || data.data.length == 0) {
                layer.msg('在该地点附近没有餐车', {icon: 5, offset: 't'});
            }
            drawOverlays(data);

        },
        error: function (err) {
            loading.hideLoading();
            layer.msg('加载失败，请重试', {icon: 5, offset: 't'});
        }
    });
}

function drawOverlays(data) {
    baiduMap.clearOverlays();
    for (idx in data.data) {
        drawOverlay(data.data[idx]);
    }
}

function drawOverlay(data) {

    var point = new BMap.Point(data.longitude, data.latitude);
    var marker = new BMap.Marker(point); // 创建点
    marker.addEventListener("click", attribute);
    baiduMap.addOverlay(marker);

    function attribute() {
        var opts = {
            width: 300,     // 信息窗口宽度
            height: 0,     // 信息窗口高度
            title: "<h3 style='color:green'>" + data.applicant + "</h3>"// 信息窗口标题
        }

        var info = "<div style='overflow-y:auto'>" +
            "<div><span style='color:blue'>Business Hours: </span>" + data.dayshours + "</div>" +
            "<div><span style='color:blue'>Address: </span>" + data.address + "</div>" +
            "<div><span style='color:blue'>Foot Items: </span>" +
            "" + data.fooditems.replace(/:/g, ';') + "</div>" +
            "</div>";

        var infoWindow = new BMap.InfoWindow(info, opts);  // 创建信息窗口对象
        marker.addEventListener("click", function () {
            baiduMap.openInfoWindow(infoWindow, point); //开启信息窗口
        });
    }
}

