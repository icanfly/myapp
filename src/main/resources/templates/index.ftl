<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no"/>
    <link rel="stylesheet" href="/static/css/index.css" type="text/css">
    <link rel="stylesheet" href="/static/css/loading.css" type="text/css"/>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=oqX2VPKvW3G9mEGENhoRclajvk8El4Bv"></script>
    <script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.1/jquery.min.js"></script>
    <script type="text/javascript" src="/static/js/index.js"></script>
    <script type="text/javascript" src="/static/js/layer/layer.js"></script>
    <script type="text/javascript" src="/static/js/loading.js"></script>
    <title>Foot Truck Demo</title>
</head>
<body>
<h1 id="title">
    Foot Truck Demo
</h1>
<div id="container">
    <div id="leftSide">
        <div>
            <h2>使用说明</h2>
            <span>在右图中拖动地图，并使用鼠标右键菜单<b style="color:green">"搜索附近餐车"</b>对附近的FootTruck进行搜索，搜索范围大小受参数<b
                    style="color:green">'搜索范围'</b>控制，搜索结果集返回量受参数<b style="color:green">'返回数量'</b>控制</span>
        </div>
        <div>
            <h2>搜索结果说明</h2>
            <li>如果没有满足条件的搜索结果，则在页面上会弹出提示窗提示未找到数据。</li>
            <li>如果查询有数据，则会在地图上以<b style="color:red">红色标记</b>的形式标出，用鼠标双击红色标记会弹出信息窗口显示该餐车的一些信息，如<b
                    style="color:green">餐车名称，餐车地址，餐车供应食物类别等。</b></li>
        </div>
        <div>
            <h2>参数调节</h2>
            <div>
                <div>数据来源：<input id="onlineRadio" type="radio" name="source" value="online">实时
                    <input id="offlineRadio" type="radio" name="source" value="offline" checked>离线（至2017-09-14有效数据）
                </div>
                <div>搜索范围: <input id="meters" type="text" value="1000">（默认1000，最大2000，单位:米）</div>
                <div>返回数量: <input id="querySize" type="text" value="10">（默认10，最大20个）</div>
            </div>
        </div>
    </div>
    <div id="mapDiv"></div>
</div>
</body>
</html>
